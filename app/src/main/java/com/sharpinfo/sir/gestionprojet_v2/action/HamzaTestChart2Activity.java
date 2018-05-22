package com.sharpinfo.sir.gestionprojet_v2.action;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

import bean.Societe;
import service.SocieteService;
import service.TacheService;

public class HamzaTestChart2Activity extends AppCompatActivity {
    PieChart pieChart;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamza_test_chart2);
        pieChart = findViewById(R.id.charthamza2);

        TacheService tacheService = new TacheService(context);
        SocieteService societeService = new SocieteService(context);

        List<Societe> societes = societeService.findAll();
        Integer total = tacheService.totalTacheSociete();

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        ArrayList<String> societeNames = new ArrayList<>();

        for (Societe societe : societes) {
            Integer heure = tacheService.tacheBySociete(societe);

            BigDecimal totalBig = new BigDecimal(total);
            BigDecimal heureBig = new BigDecimal(heure);
            BigDecimal pourcentage = heureBig.divide(totalBig, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            Log.d("barcharttest", pourcentage.floatValue() + "");

            pieEntries.add(new PieEntry(pourcentage.floatValue(),societe.getRaisonSociale()));


        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"temps");

        pieDataSet.setColors(getResources().getColor(R.color.yello),
                getResources().getColor(R.color.purple),
                getResources().getColor(R.color.pink),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.brown),
                getResources().getColor(R.color.grey));


        Description description = pieChart.getDescription();
        description.setEnabled(false);
        pieChart.setCenterText("Time Par Societe");

        pieDataSet.setValueTextSize(15f);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setValueTextColor(Color.BLACK);

        //legend
        Legend legend = pieChart.getLegend();
        legend.setFormSize(15f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(15f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setYOffset(0f);
        //

        //position
        pieChart.setExtraBottomOffset(-20f);
        //

        pieDataSet.setSliceSpace(1f);//space between parts

        pieChart.setDrawHoleEnabled(true);//for the hole inside

        PieData data = new PieData(pieDataSet);
        pieChart.setUsePercentValues(true);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh

        
       
    }
}
