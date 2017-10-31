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

    private GoogleMap mMap;
    private EditText rDateAfterView;
    private EditText rDateBeforeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_activity);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rDateAfterView = (EditText) findViewById(R.id.dateafter_editText);
        rDateBeforeView = (EditText) findViewById(R.id.datebefore_editText);

        Button date_rangeButton = (Button) findViewById(R.id.daterange_button);

        /*date_rangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rdateafter = rDateAfterView.getText().toString();
                String rdatebefore = rDateBeforeView.getText().toString();
                Date dateafter = new Date();
                Date datebefore = new Date();
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
            }
        });*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(10.0f);

        List<DataItem> list = SimpleModel.INSTANCE.getItems();
        for (int i = 0; i < list.size(); i++){
            DataItem item = list.get(i);
            mMap.addMarker(new MarkerOptions().position(new LatLng(item.getLatitude(), item.getLongitude()))
                    .title(String.valueOf(item.getId())).snippet(item.getCreatedDate() + "\n" + item.getAddress()));
        }
        DataItem lastItem = list.get(list.size() - 1);
        mMap.setOnInfoWindowLongClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lastItem.getLatitude(), lastItem.getLongitude())));
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
        finish();
    }

    public Date changeDateFormat(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("M/d/yy");
        Date formateddate = format.parse(date);

        return formateddate;
    }

}
