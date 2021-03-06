package com.sharpinfo.sir.gestionprojet_v2.action.statistics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpinfo.sir.gestionprojet_v2.R;

import helper.Dispacher;

public class StatisticsMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_menu);

        ImageView projetStatistics = findViewById(R.id.projet_statistics);
        projetStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dispacher.forward(StatisticsMenuActivity.this, ProjetStatisticsActivity.class);
            }
        });

        TextView projetStatisticsText = findViewById(R.id.projet_statistics_text);
        projetStatisticsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dispacher.forward(StatisticsMenuActivity.this, ProjetStatisticsActivity.class);
            }
        });

        ImageView societeStatistics = findViewById(R.id.societe_statistics);
        societeStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dispacher.forward(StatisticsMenuActivity.this, SocieteStatisticsActivity.class);
            }
        });

        TextView societeStatisticsText = findViewById(R.id.societe_statistics_text);
        societeStatisticsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dispacher.forward(StatisticsMenuActivity.this, SocieteStatisticsActivity.class);
            }
        });
    }

}
