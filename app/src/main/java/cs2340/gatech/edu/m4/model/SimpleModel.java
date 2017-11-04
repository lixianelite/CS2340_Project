package cs2340.gatech.edu.m4.model;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SimpleModel {
    public static final SimpleModel INSTANCE = new SimpleModel();


    private List<DataItem> items;

    private Set<Integer> idContainer;

    private List<DataItem> filteredList;

    private List<Entry> entries;

    private Map<Integer, String> formatMap;

    private SimpleModel() {
        items = new ArrayList<>();
        idContainer = new HashSet<>();
        filteredList = new ArrayList<>();
        entries = new ArrayList<>();
        formatMap = new HashMap<>();
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

    public List<Entry> getEntries(){
        return entries;
    }

    public Set<Integer> getIdContainer(){
        return idContainer;
    }

    public Map<Integer, String> getFormatMap(){
        return formatMap;
    }

    public void addId(Integer id){
        idContainer.add(id);
    }

    public boolean containsId(int id){
        return idContainer.contains(id);
    }

    public void addEntry(Entry entry){
        entries.add(entry);
    }

    public DataItem findItemById(int id) {
        for (DataItem d : items) {
            if (d.getId() == id) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }
}
