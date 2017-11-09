package cs2340.gatech.edu.m4.activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.List;

import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;

/**
 * Created by bravado on 11/9/17.
 */

public class HeatMapActivity extends BaseMapActivity {

    private HeatmapTileProvider mProvider;

    private TileOverlay mOverlay;

    @Override
    protected void startMap() {
        getMap().setMinZoomPreference(10.0f);

        LatLng cameraPoint = new LatLng(40.713, -74.004);
        getMap().moveCamera(CameraUpdateFactory.newLatLng(cameraPoint));

        List<LatLng> list = new ArrayList<>();
        List<DataItem> filteredList = SimpleModel.INSTANCE.getFilteredList();
        for (DataItem item : filteredList){
            list.add(new LatLng(item.getLatitude(), item.getLongitude()));
        }
        mProvider = new HeatmapTileProvider.Builder().data(list).build();
        mProvider.setRadius(25);
        mOverlay = getMap().addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }
}
