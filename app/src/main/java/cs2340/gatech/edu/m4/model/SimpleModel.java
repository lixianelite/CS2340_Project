package cs2340.gatech.edu.m4.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SimpleModel {
    public static final SimpleModel INSTANCE = new SimpleModel();


    private List<DataItem> items;

    private Set<Integer> idContainer;

    private List<DataItem> filteredList;

    private SimpleModel() {
        items = new ArrayList<>();
        idContainer = new HashSet<>();
        filteredList = new ArrayList<>();
    }

    public void addItem(DataItem item) {
        items.add(item);
    }

    public List<DataItem> getItems() {
        return items;
    }

    public List<DataItem> getFilteredList(){
        return filteredList;
    }

    public Set<Integer> getIdContainer(){
        return idContainer;
    }

    public void addId(Integer id){
        idContainer.add(id);
    }

    public boolean containsId(int id){
        return idContainer.contains(id);
    }

    public DataItem findItemById(int id) {
        for (DataItem d : items) {
            if (d.getId() == id) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }
}
