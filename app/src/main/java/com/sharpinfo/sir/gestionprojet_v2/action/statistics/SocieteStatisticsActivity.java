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

import bean.Depense;
import bean.Societe;
import service.DepenseService;
import service.SocieteService;
import service.TacheService;


public class SocieteStatisticsActivity extends AppCompatActivity {
    Context context = this;
    Spinner spinner;
    PieChart pieChartExpense;
    PieChart pieChartTime;
    private String choice = "";
    DepenseService depenseService = new DepenseService(context);

    private void initPieChartExpense() {
        pieChartExpense = findViewById(R.id.chartsociete);

        SocieteService societeService = new SocieteService(context);

        List<Societe> societes = societeService.findAll();

        List<PieEntry> entries = new ArrayList<PieEntry>();

        BigDecimal total = depenseService.totalDepense();
        Log.d("chart", total + "");
        if (!total.equals(BigDecimal.ZERO)) {
            for (Societe societe : societes) {
                for (Depense depense : societe.getDepenses()) {
                    Log.d("societeStats", depense.getMontant().toString());
                }
                BigDecimal depenseSociete = depenseService.depenseBySociete(societe);
                BigDecimal pourcentage = depenseSociete.divide(total, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                Log.d("chart", pourcentage + "");
                entries.add(new PieEntry(pourcentage.floatValue(), societe.getRaisonSociale()));
            }
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

//        Description description = new Description();
//        description.setText("Depenses Par Societe");
//        description.setTextSize(20f);
//        description.setXOffset(10f);
//        description.setYOffset(10f);
//        pieChartExpense.setDescription(description);

        Description description = pieChartExpense.getDescription();
        description.setEnabled(false);
        pieChartExpense.setCenterText("Depense Par Societe");

        dataSet.setValueTextSize(13f);
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setValueTextColor(Color.BLACK);

        //legend
        Legend legend = pieChartExpense.getLegend();
        legend.setFormSize(13f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(13f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setYOffset(0f);
        //

        //position
        pieChartExpense.setExtraBottomOffset(-20f);
        //


        //animation
        pieChartExpense.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        //
        dataSet.setSliceSpace(1f);//space between parts

        pieChartExpense.setDrawHoleEnabled(true);//for the hole inside

        PieData data = new PieData(dataSet);
        pieChartExpense.setUsePercentValues(true);
        pieChartExpense.setEntryLabelColor(getResources().getColor(R.color.black));
        pieChartExpense.setData(data);
        pieChartExpense.invalidate(); // refresh


    }

    private void initPieChartTime() {
        pieChartTime = findViewById(R.id.chart2societe);
        pieChartTime.setVisibility(View.GONE);
        TacheService tacheService = new TacheService(context);
        SocieteService societeService = new SocieteService(context);

        List<Societe> societes = societeService.findAll();
        Integer total = tacheService.totalTacheSociete();

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        if (total != 0) {
            for (Societe societe : societes) {
                Integer heure = tacheService.tacheBySociete(societe);

                BigDecimal totalBig = new BigDecimal(total);
                BigDecimal heureBig = new BigDecimal(heure);
                BigDecimal pourcentage = heureBig.divide(totalBig, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                Log.d("barcharttest", pourcentage.floatValue() + "");

                pieEntries.add(new PieEntry(pourcentage.floatValue(), societe.getRaisonSociale()));

            }
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        pieDataSet.setColors(
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.brown),
                getResources().getColor(R.color.purple),
                getResources().getColor(R.color.pink),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.yello),
                getResources().getColor(R.color.grey));


        Description description = pieChartTime.getDescription();
        description.setEnabled(false);
        pieChartTime.setCenterText("Time Par Societe");

        pieDataSet.setValueTextSize(13f);

        //animation
        pieChartTime.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        //
        pieDataSet.setValueTextSize(15f);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setValueTextColor(Color.BLACK);

        //legend
        Legend legend = pieChartTime.getLegend();
        legend.setFormSize(13f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(13f);
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
        pieChartTime.setEntryLabelColor(getResources().getColor(R.color.black));
        pieChartTime.setData(data);
        pieChartTime.invalidate(); // refresh
    }

    private void initChartSpinner() {
        spinner = findViewById(R.id.chart_choice_societe);
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
        setContentView(R.layout.activity_societe_statistics);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        initPieChartTime();
        initPieChartExpense();

        pieChartExpense = findViewById(R.id.chartsociete);
        pieChartTime = findViewById(R.id.chart2societe);

        initChartSpinner();
        getChoiceFromSpinner();
        Button go = findViewById(R.id.chart_go_societe);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "===== " + choice);
                if (choice.equals("Time")) {
                    pieChartExpense.setVisibility(View.GONE);
                    pieChartTime.setVisibility(View.VISIBLE);
                    pieChartTime.invalidate();
                } else if (choice.equals("Expense")) {
                    pieChartExpense.setVisibility(View.VISIBLE);
                    pieChartTime.setVisibility(View.GONE);
                    pieChartExpense.invalidate();
                }
            }
        });

    }
}
