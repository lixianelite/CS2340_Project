package cs2340.gatech.edu.m4.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by bravado on 11/9/17.
 */

public class ClusterData implements ClusterItem {
    private LatLng mPosition;

    public ClusterData(double lat, double lng){
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

}
