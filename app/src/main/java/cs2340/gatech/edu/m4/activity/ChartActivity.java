package cs2340.gatech.edu.m4.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SamplePoint;
import cs2340.gatech.edu.m4.model.SimpleModel;

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

        List<Entry> entries = SimpleModel.INSTANCE.getEntries(); // = convertDataSetToEntry(data.getDataList());

        LineDataSet dataSet = new LineDataSet(entries, "The history of Sightings");

        LineData data = new LineData(dataSet);

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS); //

        dataSet.setDrawFilled(true);

        lineChart.setData(data);

        lineChart.animateY(5000);

        lineChart.getDescription().setText("What's this");

        final Map<Integer, String> formatMap = SimpleModel.INSTANCE.getFormatMap();

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.d("CharActivity", "value: " + value);
                return formatMap.get((int)value);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SimpleModel.INSTANCE.getEntries().clear();
        SimpleModel.INSTANCE.getFormatMap().clear();
    }
}
