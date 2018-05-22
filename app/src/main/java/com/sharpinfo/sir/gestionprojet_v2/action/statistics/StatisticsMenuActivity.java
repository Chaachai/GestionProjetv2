package com.sharpinfo.sir.gestionprojet_v2.action.statistics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sharpinfo.sir.gestionprojet_v2.R;

import helper.Dispacher;

public class StatisticsMenuActivity extends AppCompatActivity {
    private Button projetStatistics;
    private Button societeStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_menu);

        projetStatistics = (Button) findViewById(R.id.projet_statistics);
        projetStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dispacher.forward(StatisticsMenuActivity.this, ProjetStatisticsActivity.class);
            }
        });

        societeStatistics = (Button) findViewById(R.id.societe_statistics);
        societeStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dispacher.forward(StatisticsMenuActivity.this, SocieteStatisticsActivity.class);
            }
        });
    }

}
