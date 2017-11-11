package cs2340.gatech.edu.m4.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;
import java.util.List;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.ClusterData;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;

/**
 * Created by bravado on 11/9/17.
 */

public class ClusterMapActivity extends BaseMapActivity implements GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnMapLongClickListener {

    private ClusterManager<ClusterData> mClusterManager;

    @Override
    protected void startMap() {

        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.713, -74.004), 10));
        mClusterManager = new ClusterManager<ClusterData>(this, getMap());

        CustomClusterRenderer renderer = new CustomClusterRenderer(this, getMap(), mClusterManager);

        mClusterManager.setRenderer(renderer);
        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new CustomInfoViewAdapter());

        getMap().setOnCameraIdleListener(mClusterManager);
        getMap().setInfoWindowAdapter(mClusterManager.getMarkerManager());
        getMap().setOnInfoWindowLongClickListener(this);
        getMap().setOnMapLongClickListener(this);

        List<DataItem> filteredList = SimpleModel.INSTANCE.getFilteredList();
        List<ClusterData> list = new ArrayList<>();
        for (DataItem item : filteredList){
            list.add(new ClusterData(item.getLatitude(), item.getLongitude(), String.valueOf(item.getId()), item.getCreatedDate() + "\n" + item.getAddress()));
        }
        mClusterManager.addItems(list);
        mClusterManager.cluster();
    }

    class CustomInfoViewAdapter implements GoogleMap.InfoWindowAdapter{

        private final View mContentsView;

        CustomInfoViewAdapter(){
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


    class CustomClusterRenderer extends DefaultClusterRenderer<ClusterData>{

        public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<ClusterData> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(ClusterData item, MarkerOptions markerOptions) {
            markerOptions.title(item.getTitle()).snippet(item.getSnippet());
        }
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        int Item_id = Integer.parseInt(marker.getTitle());
        Intent intent = new Intent(ClusterMapActivity.this, DataDetailActivity.class);
        intent.putExtra(DataDetailActivity.ARG_ITEM_ID, Item_id);
        this.startActivity(intent);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        Intent intent = new Intent(ClusterMapActivity.this, ReportActivity.class);
        intent.putExtra("className", "ClusterMapDisplayActivity");
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }

}
