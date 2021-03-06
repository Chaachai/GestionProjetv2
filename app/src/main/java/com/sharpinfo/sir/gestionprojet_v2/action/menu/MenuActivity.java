package com.sharpinfo.sir.gestionprojet_v2.action.menu;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.MainActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.societe.SocieteCreateActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.societe.SocieteListActivity;

import helper.Dispacher;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView projetCard;
    private CardView societeCard;
    private CardView depenseCard;
    private CardView tacheCard;
    private CardView statistiqueCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        projetCard = findViewById(R.id.projetCardView);
        societeCard = findViewById(R.id.societeCardView);
        projetCard.setOnClickListener(this);
        societeCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.societeCardView:
                Dispacher.forward(MenuActivity.this, SocieteListActivity.class);
                break;
            case R.id.projetCardView:
                Dispacher.forward(MenuActivity.this, MainActivity.class);
                break;
            default:
                break;
        }


    }

    public void goToProjets(View view) {
        projetCard = findViewById(R.id.projetCardView);
        Dispacher.forward(MenuActivity.this, SocieteCreateActivity.class);
    }

//    public void seeDashboard(View view) {
//    }

//    public void manageTasks(View view) {
//    }

//    public void manageExpenses(View view) {
//    }

//    public void testStatistics(View view) {
//    }


//    public void manageCompany(View view) {
//    }
}
