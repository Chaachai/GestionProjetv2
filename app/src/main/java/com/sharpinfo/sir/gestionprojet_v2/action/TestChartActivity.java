package com.sharpinfo.sir.gestionprojet_v2.action;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
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

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(13.5f, "Yellow"));
        entries.add(new PieEntry(26.7f, "Purple"));
        entries.add(new PieEntry(24.0f, "Pink"));
        entries.add(new PieEntry(39.8f, "Green"));

        PieDataSet set = new PieDataSet(entries, "Election Results");
        set.setColors(new int[]{getResources().getColor(R.color.yello), getResources().getColor(R.color.purple), getResources().getColor(R.color.pink), getResources().getColor(R.color.green)});
//        set.setColors(getResources().getColor(R.color.green));
        PieData data = new PieData(set);
        pieChart.setData(data);
        Description description = new Description();
        description.setText("Ktab chi Description hna");
        pieChart.setDescription(description);
        pieChart.setCenterText("Ktab chi text hna ");
        pieChart.invalidate(); // refresh

    }
}
