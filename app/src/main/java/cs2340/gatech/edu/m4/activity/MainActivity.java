package cs2340.gatech.edu.m4.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataDatabaseHelper;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.DataItemAdapter;
import cs2340.gatech.edu.m4.model.SimpleModel;


// The data is from department of Health and Mental Hygiene

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DataDatabaseHelper dataDatabaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        DataItemAdapter adapter = new DataItemAdapter(SimpleModel.INSTANCE.getItems());
        recyclerView.setAdapter(adapter);
        dataDatabaseHelper = new DataDatabaseHelper(this, "Data.db", null, 1);
        db = dataDatabaseHelper.getWritableDatabase();

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.report_button:
                Intent ReportIntent = new Intent(MainActivity.this, ReportActivity.class);
                ReportIntent.putExtra("className", "MainActivity");
                MainActivity.this.startActivity(ReportIntent);
                break;
            case R.id.menu_chart:
                AlertDialog.Builder dialog1 = getAlertDialog("chart_display");
                dialog1.show();
                break;
            case R.id.heat_map:
                AlertDialog.Builder dialog2 = getAlertDialog("heatmap_display");
                dialog2.show();
                break;
            case R.id.cluster_map:
                AlertDialog.Builder dialog3 = getAlertDialog("cluster_display");
                dialog3.show();
                break;
            case R.id.logout_button:
                Intent logoutIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                SimpleModel.INSTANCE.getItems().clear();
                SimpleModel.INSTANCE.getIdContainer().clear();
                MainActivity.this.startActivity(logoutIntent);
                finish();
                break;
            default:
        }
        return true;
    }



    private void filterProcess(String startDate, String endDate){
        List<DataItem> list = SimpleModel.INSTANCE.getItems();
        Map<Integer, String> formatMap = SimpleModel.INSTANCE.getFormatMap();
        String[] startArray = startDate.split("-");
        String[] endArray = endDate.split("-");

        int startMon = Integer.parseInt(startArray[1]);
        int startYear = Integer.parseInt(startArray[0]);
        int endMon = Integer.parseInt(endArray[1]);
        int endYear = Integer.parseInt(endArray[0]);
        int years = Math.max(0, endYear - startYear - 1);

        int times = startYear != endYear ? 13 - startMon + endMon + years * 12 : endMon - startMon + 1;


        HashMap<String, Integer> map = new HashMap<>();

        for (int i = 0; i < times; i++){
            int dateYear = startYear + (startMon + i) / 13;
            int dateMon = (startMon - 1 + i) % 12 + 1;


            float standardDate = dateYear + (float)dateMon / 100;
            map.put(String.valueOf(standardDate), 0);
            String formatDate = dateYear + "." + dateMon;
            formatMap.put(i, formatDate);
        }

        for (DataItem item : list){
            String createdDate = Transform(item.getCreatedDate());
            if (map.containsKey(createdDate)){
                map.put(createdDate, map.get(createdDate) + 1);
            }
        }

        for (int i = 0; i < times; i++){
            int dateYear = startYear + (startMon + i) / 13;
            int dateMon = (startMon - 1 + i) % 12 + 1;
            float standardDate = dateYear + (float)dateMon / 100;
            int count = map.get(String.valueOf(standardDate));

            SimpleModel.INSTANCE.addEntry(new Entry(i, count));
        }
    }

    private String Transform(String rawDate){
        String[] date = rawDate.split("-");

        int Year = Integer.parseInt(date[0]);
        int Mon = Integer.parseInt(date[1]);


        float standardDate = Year + (float)Mon / 100;
        return String.valueOf(standardDate);
    }

    private AlertDialog.Builder getAlertDialog(final String choice){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Select Date Range");
        Context context = MainActivity.this;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText startDate = new EditText(context);
        startDate.setClickable(true);
        startDate.setFocusable(false);
        startDate.setHint("Enter StartDate: YY-MM-DD");
        layout.addView(startDate);

        final EditText endDate = new EditText(context);
        endDate.setClickable(true);
        endDate.setFocusable(false);
        endDate.setHint("Enter EndDate: YY-MM-DD");
        layout.addView(endDate);

        alertDialog.setView(layout);

        final Calendar myCalendar = Calendar.getInstance();
        final String dateFormat = "y-M-d";

        final DatePickerDialog.OnDateSetListener start_date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                startDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, start_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener end_date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                endDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, end_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        alertDialog.setPositiveButton("Go",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        String sdText = startDate.getText().toString();
                        String edText = endDate.getText().toString();

                        Log.d("alertDialog", sdText);
                        Log.d("alertDialog", edText);

                        if (sdText != null && !sdText.isEmpty() && edText != null && !edText.isEmpty()){
                            if (choice.equals("cluster_display")){
                                Intent ClusterMapDisplay = new Intent(MainActivity.this, ClusterMapActivity.class);
                                MainActivity.this.startActivity(ClusterMapDisplay);
                                DataDatabaseHelper.FilterData(db, sdText, edText);
                            }else if (choice.equals("chart_display")){
                                Intent ChartDisplay = new Intent(MainActivity.this, ChartActivity.class);
                                MainActivity.this.startActivity(ChartDisplay);
                                filterProcess(sdText, edText);
                            }else if (choice.equals("heatmap_display")){
                                Intent HeatMapDisplay = new Intent(MainActivity.this, HeatMapActivity.class);
                                DataDatabaseHelper.FilterData(db, sdText, edText);
                                MainActivity.this.startActivity(HeatMapDisplay);
                            }
                        } else{
                            Toast.makeText(getApplicationContext(),
                                    "Date cannot be empty!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        return alertDialog;
    }




    /*private static Date changeDateFormat(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("y-M-d");
        Date formateddate = format.parse(date);

        return formateddate;
    }*/

    /*private void filtering(String start_date, String end_date){
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = changeDateFormat(start_date);
            endDate = changeDateFormat(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleModel.INSTANCE.getFilteredList().clear();
        List<DataItem> list = SimpleModel.INSTANCE.getItems();
        for (DataItem item : list){
            Date created_date = new Date();
            try{
                created_date = changeDateFormat(item.getCreatedDate());
            } catch (ParseException e){
                e.printStackTrace();
            }
            if (created_date.after(startDate) && created_date.before(endDate)){
                SimpleModel.INSTANCE.getFilteredList().add(item);
            }
        }
    }*/

}