package cs2340.gatech.edu.m4.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cs2340.gatech.edu.m4.model.SamplePoint;

/**
 * Created by bravado on 10/31/17.
 */


/*

This class is just a hard code for test;

 */

public class dataSetExample {


    private List<SamplePoint> dataList = new ArrayList<>();

    public dataSetExample() {
        generateDummyData();
    }

    private void generateDummyData() {

        for (int i = 1; i < 10; ++i) {
            double x = i * 0.5;
            dataList.add(new SamplePoint(x, i));
        }

    }

    public List<SamplePoint> getDataList() { return dataList; }

}
