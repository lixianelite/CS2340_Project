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
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataDatabaseHelper;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.DataItemAdapter;
import cs2340.gatech.edu.m4.model.SimpleModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public static String TAG = "MY_APP";
    public String sdText;
    public String edText;

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
                startDate.setClickable(true);
                startDate.setFocusable(false);
                layout.addView(startDate);

                final EditText endDate = new EditText(context);
                endDate.setHint("Enter EndDate: MM/DD/YY");
                startDate.setClickable(true);
                startDate.setFocusable(false);
                layout.addView(endDate);

                alertDialog.setView(layout);

                final Calendar myCalendar = Calendar.getInstance();
                final String dateFormat = "MM/dd/yy";

                final DatePickerDialog.OnDateSetListener start_date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, day);
                        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                        startDate.setText(sdf.format(myCalendar.getTime()));
                        sdText = sdf.format(myCalendar.getTime());
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
                        edText = sdf.format(myCalendar.getTime());
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
                            public void onClick(DialogInterface dialogInterface, int i) {
                               //String sdText = startDate.getText().toString();
                               //String edText = endDate.getText().toString();
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
}