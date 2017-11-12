package cs2340.gatech.edu.m4.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataDatabaseHelper;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;

public class DataDetailActivity extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ACTIVITY = "activity";

    private DataDatabaseHelper dataDatabaseHelper;
    private SQLiteDatabase db;

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

        if (getIntent().getStringExtra(ACTIVITY).equals("ClusterMapActivity")){
            dataDatabaseHelper = new DataDatabaseHelper(this, "Data.db", null, 1);
            db = dataDatabaseHelper.getWritableDatabase();
            mItem = DataDatabaseHelper.QueryData(db, item_id);
        }else {
            mItem = SimpleModel.INSTANCE.findItemById(item_id);
        }

        dkey = findViewById(R.id.detail_key);
        ddate = findViewById(R.id.detail_date);
        dloc = findViewById(R.id.detail_loc);
        dzip = findViewById(R.id.detail_zip);
        daddr = findViewById(R.id.detail_addr);
        dcity = findViewById(R.id.detail_city);
        dbo = findViewById(R.id.detail_bo);
        dla = findViewById(R.id.detail_la);
        dlon = findViewById(R.id.detail_lon);

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
