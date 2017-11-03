package cs2340.gatech.edu.m4.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataDatabaseHelper;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.DataItemAdapter;
import cs2340.gatech.edu.m4.model.SimpleModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public static String TAG = "MY_APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        DataItemAdapter adapter = new DataItemAdapter(SimpleModel.INSTANCE.getItems());
        recyclerView.setAdapter(adapter);

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
            case R.id.map_display:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Select Date Range");
                Context context = MainActivity.this;
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText startDate = new EditText(context);
                startDate.setHint("Enter StartDate: MM/DD/YY");
                layout.addView(startDate);

                final EditText endDate = new EditText(context);
                endDate.setHint("Enter EndDate: MM/DD/YY");
                layout.addView(endDate);

                alertDialog.setView(layout);

                alertDialog.setPositiveButton("Go",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick (DialogInterface dialogInterface, int i) {
                                String sdText = startDate.getText().toString();
                                String edText = endDate.getText().toString();
                                if (sdText != null && !sdText.isEmpty() && edText != null && !edText.isEmpty()){
                                    Intent MapDisplay = new Intent(MainActivity.this, MapDisplayActivity.class);
                                    MapDisplay.putExtra(MapDisplayActivity.START_DATE, sdText);
                                    MapDisplay.putExtra(MapDisplayActivity.END_DATE, edText);
                                    MainActivity.this.startActivity(MapDisplay);
                                }
                                else{
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
                alertDialog.show();
                break;
            case R.id.menu_chart:
                Intent ChartDisplay = new Intent(MainActivity.this, ChartActivity.class);
                MainActivity.this.startActivity(ChartDisplay);
                filterProcess("09/03/15", "09/10/15");
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
        String[] startArray = startDate.split("/");
        String[] endArray = endDate.split("/");
        int startMon = Integer.parseInt(startArray[0]);


        int startDay = Integer.parseInt(startArray[1]);

        int endMon = Integer.parseInt(endArray[0]);

        int endDay = Integer.parseInt(endArray[1]);

        int months = Math.max(0, endMon - startMon - 1);


        int times = startMon != endMon ? 32 - startDay + endDay + months * 31 : endDay - startDay + 1;


        HashMap<String, Integer> map = new HashMap<>();

        for (int i = 0; i < times; i++){
            int dateMon = startMon + (startDay + i) / 32;
            int dateDay = (startDay - 1 + i) % 31 + 1;
            float standardDate = dateMon + (float)dateDay / 100;
            map.put(String.valueOf(standardDate), 0);
            String formatDate = dateMon + "." + dateDay;
            formatMap.put(i, formatDate);
        }

        for (DataItem item : list){
            String createdDate = Transform(item.getCreatedDate());
            if (map.containsKey(createdDate)){
                map.put(createdDate, map.get(createdDate) + 1);
            }
        }

        for (int i = 0; i < times; i++){
            int dateMon = startMon + (startDay + i) / 32;
            int dateDay = (startDay - 1 + i) % 31 + 1;
            float standardDate = dateMon + (float)dateDay / 100;
            int count = map.get(String.valueOf(standardDate));

            Log.d("filterProcess", "standardDate " + standardDate);


            SimpleModel.INSTANCE.addEntry(new Entry(i, count));
        }

    }

    private String Transform(String rawDate){
        String[] date = rawDate.split("/");
        String[] subDate = date[2].split(" ");

        int Mon = Integer.parseInt(date[0]);
        int Day = Integer.parseInt(date[1]);


        float standardDate = Mon + (float)Day / 100;
        return String.valueOf(standardDate);
    }


}