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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
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
import service.TacheService;


public class ProjetStatisticsActivity extends AppCompatActivity {
    Context context = this;
    Spinner spinner;
    PieChart pieChartExpenses;
    PieChart pieChartTime;
    private String choice = "";
    DepenseService depenseService = new DepenseService(context);

    private void initPieChartExpenses() {
        pieChartExpenses = findViewById(R.id.chart);

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


        PieDataSet dataSet = new PieDataSet(entries, "");


        // add a lot of colors
        dataSet.setColors(
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.brown),
                getResources().getColor(R.color.purple),
                getResources().getColor(R.color.pink),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.yello),
                getResources().getColor(R.color.grey));

        Description description = pieChartExpenses.getDescription();
        description.setEnabled(false);
        pieChartExpenses.setCenterText("Depense Par Projet");

        dataSet.setValueTextSize(15f);
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setValueTextColor(Color.BLACK);

        //legend
        Legend legend = pieChartExpenses.getLegend();
        legend.setFormSize(13f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(13f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setYOffset(0f);
        //

        //position
        pieChartExpenses.setExtraBottomOffset(-20f);
        //

        dataSet.setSliceSpace(1f);//space between parts

        pieChartExpenses.setDrawHoleEnabled(true);//for the hole inside

        PieData data = new PieData(dataSet);
        pieChartExpenses.setUsePercentValues(true);
        pieChartExpenses.setEntryLabelColor(getResources().getColor(R.color.black));
        pieChartExpenses.setData(data);
        pieChartExpenses.invalidate(); // refresh


    }

    private void initBarChart() {
        pieChartTime = findViewById(R.id.chart2);
        pieChartTime.setVisibility(View.GONE);
        TacheService tacheService = new TacheService(context);
        ProjetService projetService = new ProjetService(context);

        List<Projet> projets = projetService.findAll();
        Integer total = tacheService.totalTacheProjet();

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        if (total != 0) {
            for (Projet projet : projets) {
                Integer heure = tacheService.tacheByProjet(projet);

                BigDecimal totalBig = new BigDecimal(total);
                BigDecimal heureBig = new BigDecimal(heure);
                BigDecimal pourcentage = heureBig.divide(totalBig, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                Log.d("barcharttest", pourcentage.floatValue() + "");

                pieEntries.add(new PieEntry(pourcentage.floatValue(), projet.getNom()));
            }

        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        pieDataSet.setColors(getResources().getColor(R.color.yello),
                getResources().getColor(R.color.purple),
                getResources().getColor(R.color.pink),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.brown),
                getResources().getColor(R.color.grey));


        Description description = pieChartTime.getDescription();
        description.setEnabled(false);
        pieChartTime.setCenterText("Time Par Societe");


        //animation
        pieChartTime.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        //
        pieDataSet.setValueTextSize(15f);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setValueTextColor(Color.BLACK);

        //legend
        Legend legend = pieChartTime.getLegend();
        legend.setFormSize(15f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(15f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setYOffset(0f);
        //

        //position
        pieChartTime.setExtraBottomOffset(-20f);
        //

        pieDataSet.setSliceSpace(1f);//space between parts

        pieChartTime.setDrawHoleEnabled(true);//for the hole inside

        PieData data = new PieData(pieDataSet);
        pieChartTime.setUsePercentValues(true);
        pieChartTime.setData(data);
        pieChartTime.invalidate(); // refresh
    }

    private void initChartSpinner() {
        spinner = findViewById(R.id.chart_choice_project);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.charts_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private String getChoiceFromSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice = parent.getItemAtPosition(position).toString();
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
        initPieChartExpenses();

        pieChartExpenses = findViewById(R.id.chart);
        pieChartTime = findViewById(R.id.chart2);

        initChartSpinner();
        getChoiceFromSpinner();
        Button go = findViewById(R.id.chart_go_project);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choice.equals("Expense")) {
                    pieChartExpenses.setVisibility(View.VISIBLE);
                    pieChartTime.setVisibility(View.GONE);
                } else if (choice.equals("Time")) {
                    pieChartExpenses.setVisibility(View.GONE);
                    pieChartTime.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
