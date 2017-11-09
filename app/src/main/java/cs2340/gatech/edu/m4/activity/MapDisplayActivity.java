package cs2340.gatech.edu.m4.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;


/**
 * Created by bravado on 11/8/17.
 */

public class MapDisplayActivity extends BaseMapActivity implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnMapLongClickListener {

    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";

    @Override
    protected void startMap() {

        getMap().setMinZoomPreference(10.0f);

        List<DataItem> list = SimpleModel.INSTANCE.getFilteredList();
        for (int i = 0; i < list.size(); i++){
            DataItem item = list.get(i);
            getMap().addMarker(new MarkerOptions().position(new LatLng(item.getLatitude(), item.getLongitude()))
                    .title(String.valueOf(item.getId())).snippet(item.getCreatedDate() + "\n" + item.getAddress()));
        }

        getMap().setOnInfoWindowLongClickListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setInfoWindowAdapter(new MapDisplayActivity.CustomInfoWindowAdapter());
        LatLng cameraPoint = list.isEmpty() ? new LatLng(40.67, -73.99) : new LatLng(list.get(list.size() - 1). getLatitude(),
                list.get(list.size() - 1). getLongitude());
        getMap().moveCamera(CameraUpdateFactory.newLatLng(cameraPoint));

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

}
