package com.sharpinfo.sir.gestionprojet_v2.action.dashboard;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.TempsAdapterDashboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Projet;
import bean.Societe;
import bean.Tache;
import helper.Session;
import service.TacheService;

public class TempsListDashboardActivity extends AppCompatActivity {
    TacheService tacheService = new TacheService(this);
    private RecyclerView tempsRecyclerView;
    private TextView titre;
    private TextView totaleTemps;
    List<Tache> taches;
    TempsAdapterDashboard tempsAdapterDashboard;

    private void injecterGUI() {
        tempsRecyclerView = findViewById(R.id.tempsDashboardRecyclerView);
    }

    private void initAdapter() {
        Societe societe = (Societe) Session.getAttribut("societeCriteria");
        Projet projet = (Projet) Session.getAttribut("projetCriteria");
        Date dateMin = (Date) Session.getAttribut("dateMinCriteria");
        Date dateMax = (Date) Session.getAttribut("dateMaxCriteria");

        titre = findViewById(R.id.titre_temps_dashboard);
        totaleTemps = findViewById(R.id.totale_temps_dashboard);

        if (societe != null) {
            titre.setText(societe.getRaisonSociale());
        }
        if (projet != null) {
            titre.setText(projet.getNom());
        }
        if (societe == null && projet == null) {
            titre.setText("Sociétés + Projets");
        }

        taches = tacheService.findByCriteria(societe, projet, dateMin, dateMax);

        Integer totale = tacheService.totalTempsCriteria(taches);
        int heure = totale / 60;
        int minute = totale % 60;
        totaleTemps.setText(String.format("%d:%d", heure, minute));

        tempsAdapterDashboard = new TempsAdapterDashboard(taches);

        tempsRecyclerView.setAdapter(tempsAdapterDashboard);
        tempsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        tempsRecyclerView.addItemDecoration(itemDecoration);

        Session.delete("societeCriteria");
        Session.delete("projetCriteria");
        Session.delete("dateMinCriteria");
        Session.delete("dateMaxCriteria");
    }

    private void showBySociete(Long idSociete) {

        final List<Tache> tachesBySociete = new ArrayList<>();
        for (Tache tache : taches) {
            if (tache.getSociete() != null) {
                if (tache.getSociete().getId().equals(idSociete)) {
                    tachesBySociete.add(tache);
                }
            }
        }
        tempsAdapterDashboard.setfilter(tachesBySociete);
        tempsAdapterDashboard.notifyDataSetChanged();
        Session.delete("societeRecherce");
    }

    private void showByProjet(Long idProjet) {

        final List<Tache> tachesByProjet = new ArrayList<>();
        for (Tache tache : taches) {
            if (tache.getProjet() != null) {
                if (tache.getProjet().getId().equals(idProjet)) {
                    tachesByProjet.add(tache);
                }
            }
        }
        tempsAdapterDashboard.setfilter(tachesByProjet);
        tempsAdapterDashboard.notifyDataSetChanged();
        Session.delete("societeRecherce");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temps_list_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
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
