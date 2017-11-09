package cs2340.gatech.edu.m4.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import cs2340.gatech.edu.m4.R;

/**
 * Created by bravado on 11/8/17.
 */

public abstract class BaseMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_activity);
        setUpMap();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }


    //This needs more consideration!!!

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mMap != null) return;
        mMap = googleMap;
        startMap();
    }

    protected abstract void startMap();

    private void setUpMap(){
        ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMapAsync(this);
    }

    protected GoogleMap getMap(){
        return mMap;
    }

}
