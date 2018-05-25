package com.sharpinfo.sir.gestionprojet_v2.action.dashboard;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sharpinfo.sir.gestionprojet_v2.R;

import helper.Dispacher;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void dashboardDepense(View view) {
        Dispacher.forward(DashboardActivity.this, DepenseDashboardActivity.class);
    }

    public void dashboardTemps(View view) {
        Dispacher.forward(DashboardActivity.this, TempsDashboardActivity.class);
    }
}
