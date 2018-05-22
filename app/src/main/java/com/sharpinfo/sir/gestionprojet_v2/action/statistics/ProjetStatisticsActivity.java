package com.sharpinfo.sir.gestionprojet_v2.action.statistics;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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


public class ProjetStatisticsActivity extends AppCompatActivity {
    Spinner spinner;
    private String choice = "";

    private void initPieChart() {
        PieChart pieChart = findViewById(R.id.chart);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(13.5f, "Sharpinfo"));
        entries.add(new PieEntry(26.7f, "Societe Générale"));
        entries.add(new PieEntry(24.0f, "UNITECH"));
        entries.add(new PieEntry(39.8f, "Smart Networks"));
        entries.add(new PieEntry(25.8f, "Societe2"));
        entries.add(new PieEntry(9f, "Vegasys"));
        entries.add(new PieEntry(12.8f, "Societe1"));

        PieDataSet set = new PieDataSet(entries, "");

        set.setColors(getResources().getColor(R.color.yello), getResources().getColor(R.color.purple), getResources().getColor(R.color.pink), getResources().getColor(R.color.green), getResources().getColor(R.color.orange), getResources().getColor(R.color.brown), getResources().getColor(R.color.grey));

        PieData data = new PieData(set);
        pieChart.setData(data);
        Description description = new Description();
        description.setText("Time spent on companies (hours)");
        pieChart.setDescription(description);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.black));
        pieChart.setCenterText("Time spent on companies");
        pieChart.invalidate(); // refresh

    }

    private void initBarChart() {
        BarChart barChart = findViewById(R.id.chart2);
        barChart.setVisibility(View.GONE);
        List<BarEntry> entries2 = new ArrayList<>();

        entries2.add(new BarEntry(0f, 30f));
        entries2.add(new BarEntry(1f, 80f));
        entries2.add(new BarEntry(2f, 60f));
        entries2.add(new BarEntry(3f, 50f));
        entries2.add(new BarEntry(4f, 70f));
        entries2.add(new BarEntry(5f, 60f));

        BarDataSet set2 = new BarDataSet(entries2, "BarDataSet");

        set2.setColors(getResources().getColor(R.color.yello), getResources().getColor(R.color.purple), getResources().getColor(R.color.pink), getResources().getColor(R.color.green), getResources().getColor(R.color.orange), getResources().getColor(R.color.brown), getResources().getColor(R.color.grey));

        Description description = new Description();
        description.setText("Expenses of the companies");
        barChart.setDescription(description);
        BarData data2 = new BarData(set2);
        data2.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data2);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh
    }

    private void initChartSpinner() {
        spinner = findViewById(R.id.chart_choice_project);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.charts_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private String getChoiceFromSpinner() {
//        final String[] choice = new String[1];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice = parent.getItemAtPosition(position).toString();

                Log.d("test", "no error");
                Log.d("tag", "***************************" + choice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return choice;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projet_statistics);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
//        initBarChart();
//        initPieChart();

        final PieChart pieChart = findViewById(R.id.chart);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(13.5f, "Sharpinfo"));
        entries.add(new PieEntry(26.7f, "Societe Générale"));
        entries.add(new PieEntry(24.0f, "UNITECH"));
        entries.add(new PieEntry(39.8f, "Smart Networks"));
        entries.add(new PieEntry(25.8f, "Societe2"));
        entries.add(new PieEntry(9f, "Vegasys"));
        entries.add(new PieEntry(12.8f, "Societe1"));

        PieDataSet set = new PieDataSet(entries, "");

        set.setColors(getResources().getColor(R.color.yello), getResources().getColor(R.color.purple), getResources().getColor(R.color.pink), getResources().getColor(R.color.green), getResources().getColor(R.color.orange), getResources().getColor(R.color.brown), getResources().getColor(R.color.grey));

        PieData data = new PieData(set);
        pieChart.setData(data);
        Description description = new Description();
        description.setText("Time spent on companies (hours)");
        pieChart.setDescription(description);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.black));
        pieChart.setCenterText("Time spent on companies");
        pieChart.invalidate(); // refresh


        final BarChart barChart = findViewById(R.id.chart2);
        barChart.setVisibility(View.GONE);
        List<BarEntry> entries2 = new ArrayList<>();

        entries2.add(new BarEntry(0f, 30f));
        entries2.add(new BarEntry(1f, 80f));
        entries2.add(new BarEntry(2f, 60f));
        entries2.add(new BarEntry(3f, 50f));
        entries2.add(new BarEntry(4f, 70f));
        entries2.add(new BarEntry(5f, 60f));

        BarDataSet set2 = new BarDataSet(entries2, "BarDataSet");

        set2.setColors(getResources().getColor(R.color.yello), getResources().getColor(R.color.purple), getResources().getColor(R.color.pink), getResources().getColor(R.color.green), getResources().getColor(R.color.orange), getResources().getColor(R.color.brown), getResources().getColor(R.color.grey));

        Description description2 = new Description();
        description.setText("Expenses of the companies");
        barChart.setDescription(description2);
        BarData data2 = new BarData(set2);
        data2.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data2);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh

        initChartSpinner();
         getChoiceFromSpinner();
        Button go = findViewById(R.id.chart_go_project);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "===== " + choice);
                if (choice.equals("Time")) {
                    pieChart.setVisibility(View.VISIBLE);
                    barChart.setVisibility(View.GONE);
                } else if (choice.equals("Expense")) {
                    pieChart.setVisibility(View.GONE);
                    barChart.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
