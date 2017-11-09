package cs2340.gatech.edu.m4.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        dateText = (EditText) findViewById(R.id.date);
        locationText = (EditText) findViewById(R.id.location);
        zipText = (EditText) findViewById(R.id.zip);
        addressText = (EditText) findViewById(R.id.address);
        cityText = (EditText) findViewById(R.id.city);
        boroughText = (EditText) findViewById(R.id.borough);
        latitudeText = (EditText) findViewById(R.id.latitude);
        longitudeText = (EditText) findViewById(R.id.longitude);
        random = new Random();
        dataDatabaseHelper = new DataDatabaseHelper(this, "Data.db", null, 1);
        db = dataDatabaseHelper.getWritableDatabase();

        Intent receivedIntent = getIntent();
        receivedClassName = receivedIntent.getStringExtra("className");
        if (receivedClassName.equals("MapDisplayActivity")) MapReportConfiguration(receivedIntent);

        Button report_cancelButton = (Button) findViewById(R.id.report_cancel_button);


        report_cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Button report_writeButton = (Button) findViewById(R.id.report_write_button);
        report_writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteData();
                Toast.makeText(ReportActivity.this, "Reported the rat sightseeing! Good Job!", Toast.LENGTH_SHORT).show();
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

        int genId = 10000000 + random.nextInt(90000000);
        while (model.containsId(genId)){
            genId = 10000000 + random.nextInt(90000000);
        }

        String date = dateText.getText().toString();

        Log.d("ReportActivity", date);
        date = TransformDate(date);
        Log.d("ReportActivity", date);

        DataItem data = new DataItem(genId, dateText.getText().toString(), locationText.getText().toString(), Integer.valueOf(zipText.getText().toString()), addressText.getText().toString(), cityText.getText().toString(), boroughText.getText().toString(), Float.valueOf(latitudeText.getText().toString()), Float.valueOf(longitudeText.getText().toString()));
        model.addItem(data);
        model.addId(genId);
        DataDatabaseHelper.writeIntoDatabase(db, data);
    }

    private String TransformDate(String rawDate){
        String[] date = rawDate.split("/");
        int year = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[0]);
        int day = Integer.parseInt(date[1]);
        return month + "/" + day + "/" + year + " 00:00";
    }

}
