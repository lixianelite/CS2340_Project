package cs2340.gatech.edu.m4.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SamplePoint;

/**
 * Created by bravado on 10/31/17.
 */

public class ChartActivity extends AppCompatActivity{

    private dataSetExample data = new dataSetExample();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        LineChart lineChart = (LineChart)findViewById(R.id.chart);

        List<Entry> entries = convertDataSetToEntry(data.getDataList());

        LineDataSet dataSet = new LineDataSet(entries, "The history of Sightings");

        LineData data = new LineData(dataSet);

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS); //

        dataSet.setDrawFilled(true);

        lineChart.setData(data);

        lineChart.animateY(5000);

        lineChart.getDescription().setText("What's this");


    }


    private List<Entry> convertDataSetToEntry(List<SamplePoint> samplelist){
        List<Entry> entries = new ArrayList<>();

        for (SamplePoint sample : samplelist){
            entries.add(new Entry((float)sample.x, sample.y));
        }

        return entries;
    }
}
