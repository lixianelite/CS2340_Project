package cs2340.gatech.edu.m4.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cs2340.gatech.edu.m4.R;
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
    private DataItem data;


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


        Button report_cancelButton = (Button) findViewById(R.id.report_cancel_button);
        report_cancelButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent report_cancelIntent = new Intent(ReportActivity.this, MainActivity.class);
                ReportActivity.this.startActivity(report_cancelIntent);
                finish();
            }
        });

        Button report_writeButton = (Button) findViewById(R.id.report_write_button);
        report_writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteData();
                Intent report_writeIntent = new Intent(ReportActivity.this, MainActivity.class);
                ReportActivity.this.startActivity(report_writeIntent);
                finish();
            }
        });
    }
    private void WriteData(){
        DataItem data = new DataItem(DataItem.getReportId(),dateText.getText().toString(), locationText.getText().toString(), zipText.getText().toString(), addressText.getText().toString(), cityText.getText().toString(), boroughText.getText().toString(), Float.valueOf(latitudeText.getText().toString()), Float.valueOf(longitudeText.getText().toString()));
        List<DataItem> list = SimpleModel.INSTANCE.getItems();
        list.add(0,data);

    }
    
}
