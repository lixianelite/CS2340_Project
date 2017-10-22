package cs2340.gatech.edu.m4.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianyunan on 10/8/17.
 */

public class SimpleModel {
    public static final SimpleModel INSTANCE = new SimpleModel();


    private List<DataItem> items;

    private SimpleModel() {
        items = new ArrayList<>();
    }

    public void addItem(DataItem item) {
        items.add(item);
    }

    public List<DataItem> getItems() {
        return items;
    }



    public DataItem findItemById(String id) {
        for (DataItem d : items) {
            if (d.getId().equals(id)) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }
}
