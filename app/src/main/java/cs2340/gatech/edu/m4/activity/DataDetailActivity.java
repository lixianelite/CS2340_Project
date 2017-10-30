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

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;

public class DataDetailActivity extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "item_id";

    private DataItem mItem;

    private TextView dkey;
    private TextView ddate;
    private TextView dloc;
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

        int item_id = getIntent().getIntExtra(ARG_ITEM_ID, -1);



        mItem = SimpleModel.INSTANCE.findItemById(item_id);

        dkey = (TextView) findViewById(R.id.detail_key);
        ddate = (TextView) findViewById(R.id.detail_date);
        dloc = (TextView) findViewById(R.id.detail_loc);
        dzip = (TextView) findViewById(R.id.detail_zip);
        daddr = (TextView) findViewById(R.id.detail_addr);
        dcity = (TextView) findViewById(R.id.detail_city);
        dbo = (TextView) findViewById(R.id.detail_bo);
        dla = (TextView) findViewById(R.id.detail_la);
        dlon = (TextView) findViewById(R.id.detail_lon);

        dkey.setText("UniqueKey:"+ " " + String.valueOf(mItem.getId()));
        ddate.setText("CreatedDate:" + " " + String.valueOf(mItem.getCreatedDate()));
        dloc.setText("LocationType:" + " " + String.valueOf(mItem.getLocationType()));
        dzip.setText("Zip:" + " " + String.valueOf(mItem.getZip()));
        daddr.setText("Address:" + " " + String.valueOf(mItem.getAddress()));
        dcity.setText("City:" + " " + String.valueOf(mItem.getCity()));
        dbo.setText("Borough:" + " " + String.valueOf(mItem.getBorough()));
        dla.setText("Latitude:" + " " + String.valueOf(mItem.getLatitude()));
        dlon.setText("Longitude:" + " " + String.valueOf(mItem.getLongitude()));



    }

}
