package cs2340.gatech.edu.m4.activity;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;

public class DataDetailActivity extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "item_id";

    private DataItem mItem;

    private TextView dkey;
    private TextView ddate;
    private TextView dlo;
    private TextView dzip;
    private TextView daddr;
    private TextView dcity;
    private TextView dbo;
    private TextView dla;
    private TextView dlon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_detail);



        int id = getIntent().getIntExtra(ARG_ITEM_ID, -1);
        Log.d("DataDetailActivity", id + "");


        mItem = SimpleModel.INSTANCE.findItemById(id);
        Log.d("DataDetailActivity", "find success!");
        Log.d("DataDetailActivity", "id: " + mItem.getId() + "");


        dkey = (TextView) findViewById(R.id.detail_key);
        ddate = (TextView) findViewById(R.id.detail_date);
        dlo = (TextView) findViewById(R.id.detail_lo);
        dzip = (TextView) findViewById(R.id.detail_zip);
        daddr = (TextView) findViewById(R.id.detail_addr);
        dcity = (TextView) findViewById(R.id.detail_city);
        dbo = (TextView) findViewById(R.id.detail_bo);
        dla = (TextView) findViewById(R.id.detail_la);
        dlon = (TextView) findViewById(R.id.detail_lo);

        dkey.setText(String.valueOf(mItem.getId()));

        ddate.setText(String.valueOf(mItem.getCreatedDate()));

        dlo.setText(String.valueOf(mItem.getLocationType()));
        dzip.setText(String.valueOf(mItem.getZip()));
        daddr.setText(String.valueOf(mItem.getAddress()));
        dbo.setText(String.valueOf(mItem.getBorough()));
        dla.setText(String.valueOf(mItem.getLatitude()));
        dlon.setText(String.valueOf(mItem.getLongitude()));



    }

}
