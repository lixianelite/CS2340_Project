package cs2340.gatech.edu.m4.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by bravado on 11/9/17.
 */

public class ClusterData implements ClusterItem {
    private LatLng mPosition;
    private String title;
    private String snippet;

    public ClusterData(double lat, double lng, String title, String snippet){
        mPosition = new LatLng(lat, lng);
        this.title = title;
        this.snippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getTitle(){
        return title;
    }

    public String getSnippet(){
        return snippet;
    }

}
