package com.sharpinfo.sir.gestionprojet_v2.action.statistics;

import android.content.Context;
import android.graphics.Color;
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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sharpinfo.sir.gestionprojet_v2.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import bean.Projet;
import bean.Societe;
import service.DepenseService;
import service.ProjetService;
import service.SocieteService;


public class ProjetStatisticsActivity extends AppCompatActivity {
    Context context = this;
    Spinner spinner;
    PieChart pieChart;
    BarChart barChart;
    private String choice = "";
    DepenseService depenseService = new DepenseService(context);

    private void initPieChart() {
        pieChart = findViewById(R.id.chart);

        ProjetService projetService = new ProjetService(context);

        List<Projet> projets = projetService.findAll();

        List<PieEntry> entries = new ArrayList<PieEntry>();

        BigDecimal total = depenseService.totalDepenseProjet();
        Log.d("chart", total + "");
        for (Projet projet : projets) {
            BigDecimal depenseProjet = depenseService.depenseByProjet(projet);
            BigDecimal pourcentage = depenseProjet.divide(total, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            Log.d("chart", pourcentage + "");
            entries.add(new PieEntry(pourcentage.floatValue(), projet.getNom()));
        }


        PieDataSet dataSet = new PieDataSet(entries, "Depense Par Projet");


        // add a lot of colors
        dataSet.setColors(getResources().getColor(R.color.yello),
                getResources().getColor(R.color.purple),
                getResources().getColor(R.color.pink),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.brown),
                getResources().getColor(R.color.grey));

        Description description = new Description();
        description.setText("Depenses Par Societe");
        description.setTextSize(20f);
        description.setXOffset(10f);
        description.setYOffset(10f);
        pieChart.setDescription(description);
        dataSet.setValueTextSize(15f);
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setValueTextColor(Color.BLACK);

        //legend
        Legend legend = pieChart.getLegend();
        legend.setFormSize(15f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(15f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        //


        dataSet.setSliceSpace(1f);//space between parts

        pieChart.setDrawHoleEnabled(true);//for the hole inside

        PieData data = new PieData(dataSet);
        pieChart.setUsePercentValues(true);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh


    }

    private void initBarChart() {
        barChart = findViewById(R.id.chart2);
        barChart.setVisibility(View.GONE);
        List<BarEntry> entries2 = new ArrayList<>();

        entries2.add(new BarEntry(0f, 30f));
        entries2.add(new BarEntry(1f, 80f));
        entries2.add(new BarEntry(2f, 60f));
        entries2.add(new BarEntry(3f, 50f));
        entries2.add(new BarEntry(4f, 70f));
        entries2.add(new BarEntry(5f, 60f));

        BarDataSet set2 = new BarDataSet(entries2, "BarDataSet");

        set2.setColors(getResources().getColor(R.color.yello),
                getResources().getColor(R.color.purple),
                getResources().getColor(R.color.pink),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.brown),
                getResources().getColor(R.color.grey));

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.charts_array, android.R.layout.simple_spinner_item);
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
        initBarChart();
        initPieChart();

        pieChart = findViewById(R.id.chart);
        barChart = findViewById(R.id.chart2);

        initChartSpinner();
        getChoiceFromSpinner();
        Button go = findViewById(R.id.chart_go_project);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "===== " + choice);
                if (choice.equals("Expense")) {
                    pieChart.setVisibility(View.VISIBLE);
                    barChart.setVisibility(View.GONE);
                } else if (choice.equals("Time")) {
                    pieChart.setVisibility(View.GONE);
                    barChart.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
