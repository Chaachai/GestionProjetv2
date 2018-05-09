package com.sharpinfo.sir.gestionprojet_v2.action.Societe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.MainActivity;

import helper.Dispacher;

public class SocieteMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView projetCard;
    private CardView societeCard;
    private CardView depenseCard;
    private CardView tacheCard;
    private CardView statistiqueCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_societe_menu);
        projetCard = (CardView) findViewById(R.id.projetCardView);
        societeCard = (CardView) findViewById(R.id.societeCardView);
        projetCard.setOnClickListener(this);
        societeCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.societeCardView:
                Dispacher.forward(SocieteMenuActivity.this, SocieteListActivity.class);
                break;
            case R.id.projetCardView:
                Dispacher.forward(SocieteMenuActivity.this, MainActivity.class);
                break;
                default:break;
        }


    }

    public void goToProjets(View view) {
        projetCard = (CardView) findViewById(R.id.projetCardView);
        Dispacher.forward(SocieteMenuActivity.this, SocieteCreateActivity.class);
    }


}
