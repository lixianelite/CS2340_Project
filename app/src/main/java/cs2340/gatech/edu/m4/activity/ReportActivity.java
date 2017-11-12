package cs2340.gatech.edu.m4.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataDatabaseHelper;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;

public class ReportActivity extends AppCompatActivity {

    private EditText dateText;
    private EditText locationText;
    private EditText zipText;
    private EditText addressText;
    private EditText cityText;
    private EditText boroughText;
    private EditText latitudeText;
    private EditText longitudeText;
    private Random random;
    private DataDatabaseHelper dataDatabaseHelper;
    private SQLiteDatabase db;
    private String receivedClassName;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        dateText = findViewById(R.id.date);
        locationText = findViewById(R.id.location);
        zipText = findViewById(R.id.zip);
        addressText = findViewById(R.id.address);
        cityText = findViewById(R.id.city);
        boroughText = findViewById(R.id.borough);
        latitudeText = findViewById(R.id.latitude);
        longitudeText = findViewById(R.id.longitude);
        random = new Random();
        dataDatabaseHelper = new DataDatabaseHelper(this, "Data.db", null, 1);
        db = dataDatabaseHelper.getWritableDatabase();
        Button report_writeButton = findViewById(R.id.report_write_button);
        Button report_cancelButton = findViewById(R.id.report_cancel_button);

        myCalendar = Calendar.getInstance();
        final String dateFormat = "y-M-d";

        final DatePickerDialog.OnDateSetListener create_date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                dateText.setText(sdf.format(myCalendar.getTime()));
            }
        };

        Intent receivedIntent = getIntent();
        receivedClassName = receivedIntent.getStringExtra("className");
        if (receivedClassName.equals("ClusterMapDisplayActivity")) MapReportConfiguration(receivedIntent);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReportActivity.this, create_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        report_writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateText.getText().toString().equals("") || latitudeText.getText().toString().equals("") || longitudeText.getText().toString().equals("") || zipText.getText().toString().equals("")){
                    if (dateText.getText().toString().equals("")) Toast.makeText(ReportActivity.this, "Report error! created date can't be null", Toast.LENGTH_SHORT).show();
                    else if(zipText.getText().toString().equals("")) Toast.makeText(ReportActivity.this, "Report error! Zip Code can't be null", Toast.LENGTH_SHORT).show();
                    else if(latitudeText.getText().toString().equals("")) Toast.makeText(ReportActivity.this, "Report error! Latitude can't be null", Toast.LENGTH_SHORT).show();
                    else if (longitudeText.getText().toString().equals("")) Toast.makeText(ReportActivity.this, "Report error! Longitude can't be null", Toast.LENGTH_SHORT).show();
                }else{
                    WriteData();
                    Toast.makeText(ReportActivity.this, "Reported the rat sightseeing! Good Job!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        report_cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void MapReportConfiguration(Intent receivedIntent){
        double latitude = receivedIntent.getDoubleExtra("latitude", -1);
        double longitude = receivedIntent.getDoubleExtra("longitude", -1);
        latitudeText.setText(latitude + "");
        longitudeText.setText(longitude + "");
        longitudeText.setEnabled(false);
        latitudeText.setEnabled(false);
    }

    private void WriteData(){
        SimpleModel model = SimpleModel.INSTANCE;

        int genId = 50000000 + random.nextInt(10000000);
        while (model.containsId(genId)){
            genId = 50000000 + random.nextInt(10000000);
        }

        String rawdate = DataDatabaseHelper.dateTransform(dateText.getText().toString());

        DataItem data = new DataItem(genId, rawdate, locationText.getText().toString(), Integer.valueOf(zipText.getText().toString()), addressText.getText().toString(), cityText.getText().toString(), boroughText.getText().toString(), Float.valueOf(latitudeText.getText().toString()), Float.valueOf(longitudeText.getText().toString()));
        model.addItem(data);
        model.addId(genId);
        DataDatabaseHelper.writeIntoDatabase(db, data);
    }

}
