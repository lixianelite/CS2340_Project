package cs2340.gatech.edu.m4.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.ClientInfoStatus;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import cs2340.gatech.edu.m4.*;
import cs2340.gatech.edu.m4.model.DataDatabaseHelper;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;

/**
 * Created by bravado on 10/29/17.
 */

public class MapDisplayActivity extends FragmentActivity implements OnMapReadyCallback,
            GoogleMap.OnInfoWindowLongClickListener,
            GoogleMap.OnMapLongClickListener{

    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    private GoogleMap mMap;
    private EditText rDateAfterView;
    private EditText rDateBeforeView;

    private Button date_rangeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_activity);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        String rev_startDate = getIntent().getStringExtra(START_DATE);
        String rev_endDate = getIntent().getStringExtra(END_DATE);
        Log.d("OnMapReady", rev_startDate);
        Log.d("OnMapReady", rev_endDate);
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = changeDateFormat(rev_startDate);
            endDate = changeDateFormat(rev_endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleModel.INSTANCE.getFilteredList().clear();
        List<DataItem> list = SimpleModel.INSTANCE.getItems();
        for (int i = 0; i < list.size(); i++){
            DataItem item = list.get(i);
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

        mMap = googleMap;

        mMap.setMinZoomPreference(5.0f);

        //List<DataItem> list = SimpleModel.INSTANCE.getItems();
        list = SimpleModel.INSTANCE.getFilteredList();
        for (int i = 0; i < list.size(); i++){
            DataItem item = list.get(i);
            mMap.addMarker(new MarkerOptions().position(new LatLng(item.getLatitude(), item.getLongitude()))
                    .title(String.valueOf(item.getId())).snippet(item.getCreatedDate() + "\n" + item.getAddress()));
        }

        mMap.setOnInfoWindowLongClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        LatLng cameraPoint = list.isEmpty() ? new LatLng(40.67, -73.99) : new LatLng(list.get(list.size() - 1). getLatitude(),
                list.get(list.size() - 1). getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPoint));



    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

        private final View mContentsView;

        CustomInfoWindowAdapter(){
            mContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            TextView tvTitle = ((TextView)mContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView)mContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());
            return mContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        int Item_id = Integer.parseInt(marker.getTitle());
        Intent intent = new Intent(MapDisplayActivity.this, DataDetailActivity.class);
        intent.putExtra(DataDetailActivity.ARG_ITEM_ID, Item_id);
        this.startActivity(intent);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        Intent intent = new Intent(MapDisplayActivity.this, ReportActivity.class);
        intent.putExtra("className", "MapDisplayActivity");
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }

    public Date changeDateFormat(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("M/d/yy");
        Date formateddate = format.parse(date);

        return formateddate;
    }

}







/*date_rangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rdateafter = rDateAfterView.getText().toString();
                String rdatebefore = rDateBeforeView.getText().toString();
                Date dateafter = new Date();
                Date datebefore = new Date();
                mMap.clear();
                try {
                    dateafter = changeDateFormat(rdateafter);
                    datebefore = changeDateFormat(rdatebefore);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleModel.INSTANCE.getFilteredList().clear();
                List<DataItem> list = SimpleModel.INSTANCE.getItems();
                for (int i = 0; i < list.size(); i++){
                    DataItem item = list.get(i);
                    Date created_date = new Date();
                    try{
                        created_date = changeDateFormat(item.getCreatedDate());
                    } catch (ParseException e){
                        e.printStackTrace();
                    }
                    if (created_date.after(dateafter) && created_date.before(datebefore)){
                        SimpleModel.INSTANCE.getFilteredList().add(item);
                    }
                }

                list = SimpleModel.INSTANCE.getFilteredList();
                Log.d("MapDisplayActivity", list.size() + "");
                for (int i = 0; i < list.size(); i++){
                    DataItem item = list.get(i);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(item.getLatitude(), item.getLongitude()))
                            .title(String.valueOf(item.getId())).snippet(item.getCreatedDate() + "\n" + item.getAddress()));
                }
            }
        });*/

