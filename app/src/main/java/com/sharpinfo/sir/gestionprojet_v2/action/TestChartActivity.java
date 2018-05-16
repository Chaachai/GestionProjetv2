package com.sharpinfo.sir.gestionprojet_v2.action;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sharpinfo.sir.gestionprojet_v2.R;

import java.util.ArrayList;
import java.util.List;


public class TestChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_chart);
        PieChart pieChart = (PieChart) findViewById(R.id.chart);
        BarChart barChart = (BarChart) findViewById(R.id.chart2);

        List<PieEntry> entries = new ArrayList<>();
        List<BarEntry> entries2 = new ArrayList<>();

        entries.add(new PieEntry(13.5f, "Yellow"));
        entries.add(new PieEntry(26.7f, "Purple"));
        entries.add(new PieEntry(24.0f, "Pink"));
        entries.add(new PieEntry(39.8f, "Green"));


        entries2.add(new BarEntry(0f,30f ));
        entries2.add(new BarEntry(1f, 80f));
        entries2.add(new BarEntry(2f, 60f));
        entries2.add(new BarEntry(3f, 50f));
        entries2.add(new BarEntry(4f, 70f));
        entries2.add(new BarEntry(5f, 60f));

        PieDataSet set = new PieDataSet(entries, "Election Results");

        BarDataSet set2 = new BarDataSet(entries2, "BarDataSet");

        set.setColors(new int[]{getResources().getColor(R.color.yello), getResources().getColor(R.color.purple), getResources().getColor(R.color.pink), getResources().getColor(R.color.green)});

        set2.setColors(new int[]{getResources().getColor(R.color.yello), getResources().getColor(R.color.purple), getResources().getColor(R.color.pink), getResources().getColor(R.color.green)});


        PieData data = new PieData(set);
        pieChart.setData(data);
        Description description = new Description();
        description.setText("Ktab chi Description hna");
        pieChart.setDescription(description);
        pieChart.setCenterText("Ktab chi text hna ");
        pieChart.invalidate(); // refresh


        BarData data2 = new BarData(set2);
        data2.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data2);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh

    }
}
