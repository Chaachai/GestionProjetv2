package com.sharpinfo.sir.gestionprojet_v2.action.dashboard;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.DepenseAdapter;
import com.sharpinfo.sir.gestionprojet_v2.adapter.DepenseAdapterDashboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Depense;
import bean.DepenseType;
import bean.Projet;
import bean.Societe;
import helper.Session;
import service.DepenseService;

public class DepenseListDashboardActivity extends AppCompatActivity {

    DepenseService depenseService = new DepenseService(this);
    private RecyclerView depenseRecyclerView;
    private TextView titre;
    private TextView totaleDepense;
    List<Depense> depenses;
    DepenseAdapterDashboard depenseAdapterDashboard;

    private void injecterGUI() {
        depenseRecyclerView = (RecyclerView) findViewById(R.id.depenseDashboardRecyclerView);
    }

    private void initAdapter() {
        Societe societe = (Societe) Session.getAttribut("societeCriteria");
        Projet projet = (Projet) Session.getAttribut("projetCriteria");
        Date dateMin = (Date) Session.getAttribut("dateMinCriteria");
        Date dateMax = (Date) Session.getAttribut("dateMaxCriteria");
        DepenseType depenseType = (DepenseType) Session.getAttribut("depenseTypeCriteria");

        titre = findViewById(R.id.titre_depense_dashboard);
        totaleDepense = findViewById(R.id.totale_depense_dashboard);

        if (societe != null) {
            titre.setText(societe.getRaisonSociale());
        }
        if (projet != null) {
            titre.setText(projet.getNom());
        }
        if (societe == null && projet == null) {
            titre.setText("Sociétés + Projets");
        }

        depenses = depenseService.findByCriteria(societe, projet, dateMin, dateMax, depenseType);

        BigDecimal totale = depenseService.totalDepenseCriteria(depenses);
        totaleDepense.setText(totale + "");

        depenseAdapterDashboard = new DepenseAdapterDashboard(depenses);

        depenseRecyclerView.setAdapter(depenseAdapterDashboard);
        depenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        depenseRecyclerView.addItemDecoration(itemDecoration);

        Session.delete("societeCriteria");
        Session.delete("projetCriteria");
        Session.delete("dateMinCriteria");
        Session.delete("dateMaxCriteria");
        Session.delete("depenseTypeCriteria");
    }

    private void showBySociete(Long idSociete) {

        final List<Depense> depensesBySociete = new ArrayList<>();
        for (Depense depense : depenses) {
            if (depense.getSociete() != null) {
                if (depense.getSociete().getId().equals(idSociete)) {
                    Log.d("tag", "noErrors");
                    depensesBySociete.add(depense);

                }
            }
        }
        depenseAdapterDashboard.setfilter(depensesBySociete);
        depenseAdapterDashboard.notifyDataSetChanged();
        Session.delete("societeRecherce");
    }

    private void showByProjet(Long idProjet) {

        final List<Depense> depensesByProjet = new ArrayList<>();
        for (Depense depense : depenses) {
            if (depense.getProjet() != null) {
                if (depense.getProjet().getId().equals(idProjet)) {
                    Log.d("tag", "noErrors");
                    depensesByProjet.add(depense);

                }
            }
        }
        depenseAdapterDashboard.setfilter(depensesByProjet);
        depenseAdapterDashboard.notifyDataSetChanged();
        Session.delete("projetRecherche");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_list_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        injecterGUI();
        initAdapter();

        Societe societeRecherce = (Societe) Session.getAttribut("societeRecherce");
        if (societeRecherce != null) {
            showBySociete(societeRecherce.getId());
        }

        Projet projetRecherche = (Projet) Session.getAttribut("projetRecherche");
        if (projetRecherche != null) {
            showByProjet(projetRecherche.getId());
        }

    }

}
