package com.sharpinfo.sir.gestionprojet_v2.action.depense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sharpinfo.sir.gestionprojet_v2.R;

import java.math.BigDecimal;
import java.util.Date;

import bean.Depense;
import bean.Projet;
import bean.Societe;
import helper.Dispacher;
import service.DepenseService;
import service.ProjetService;
import service.SocieteService;

public class DepenseCreateActivity extends AppCompatActivity {

    private EditText montantDepense;
    private EditText commentaireDepense;
    private Spinner societeSpinner;
    private Spinner projetSpinner;
    DepenseService depenseService = new DepenseService(this);
    SocieteService societeService = new SocieteService(this);
    ProjetService projetService = new ProjetService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_create);
        injectParam();
    }

    private void injectParam() {
        montantDepense = findViewById(R.id.montant);
        commentaireDepense = findViewById(R.id.commentaire_depense);
        societeSpinner = findViewById(R.id.societe_spinner);
        projetSpinner = findViewById(R.id.projet_spinner);
    }

    private Depense getParam() {
        Depense depense = new Depense();
        double montantDouble = Double.valueOf("" + montantDepense.getText());
        BigDecimal montantBigDecimal = BigDecimal.valueOf(montantDouble);
        depense.setMontant(montantBigDecimal);
        depense.setCommentaire("" + commentaireDepense.getText());
        Projet projet = projetService.find(1L);
        Societe societe = societeService.find(1L);
        depense.setSociete(societe);
        depense.setProjet(projet);
        depense.setDate(new Date());
        return depense;
    }

    public void createDepense(View view) {
        Depense depense = getParam();
        Log.d("he", "========= montant: " + depense.getMontant() + " date " + depense.getDate() + " comment " + depense.getCommentaire() + " projet " + depense.getProjet() + " societe " + depense.getSociete());
        depenseService.create(depense);
        Dispacher.forward(DepenseCreateActivity.this, DepenseListActivity.class);

    }
}
