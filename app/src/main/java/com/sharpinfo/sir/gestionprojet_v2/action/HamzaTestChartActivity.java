package com.sharpinfo.sir.gestionprojet_v2.action;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
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

import bean.Societe;
import service.DepenseService;
import service.SocieteService;

public class HamzaTestChartActivity extends AppCompatActivity {

    Context context = this;
    PieChart pieChart;
    DepenseService depenseService = new DepenseService(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamza_test_chart);

        ///for testing
        SocieteService societeService = new SocieteService(context);

        List<Societe> societes = societeService.findAll();

        pieChart = findViewById(R.id.charthamza);
        List<PieEntry> entries = new ArrayList<>();

        BigDecimal total = depenseService.totalDepense();
        Log.d("chart", total + "");
        for (Societe societe : societes) {
            BigDecimal depenseSociete = depenseService.depenseBySociete(societe);
            BigDecimal pourcentage = depenseSociete.divide(total, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            Log.d("chart", pourcentage + "");
            entries.add(new PieEntry(pourcentage.floatValue(), societe.getRaisonSociale()));
        }


        PieDataSet dataSet = new PieDataSet(entries, "Depense Par Societe");


        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        Description description = new Description();
        description.setText("Depenses Par Societe");
        description.setTextSize(20f);
        description.setXOffset(10f);
        description.setYOffset(10f);
        pieChart.setDescription(description);
        dataSet.setColors(colors);
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

//    private BigDecimal getPercentage(BigDecimal depense, BigDecimal total) {
//        return depense.multiply(new BigDecimal(100)).divide(total);
//    }

}

