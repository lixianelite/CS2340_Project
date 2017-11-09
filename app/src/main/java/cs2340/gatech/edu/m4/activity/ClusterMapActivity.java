package cs2340.gatech.edu.m4.activity;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import cs2340.gatech.edu.m4.model.ClusterData;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;

/**
 * Created by bravado on 11/9/17.
 */

public class ClusterMapActivity extends BaseMapActivity {

    private ClusterManager<ClusterData> mClusterManager;

    @Override
    protected void startMap() {

        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.713, -74.004), 10));
        mClusterManager = new ClusterManager<ClusterData>(this, getMap());

        getMap().setOnCameraIdleListener(mClusterManager);

        List<DataItem> filteredList = SimpleModel.INSTANCE.getFilteredList();
        List<ClusterData> list = new ArrayList<>();
        for (DataItem item : filteredList){
            list.add(new ClusterData(item.getLatitude(), item.getLongitude()));
        }
        mClusterManager.addItems(list);

    }
}
