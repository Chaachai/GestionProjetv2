package com.sharpinfo.sir.gestionprojet_v2.action.tache;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.TacheAdapter;

import java.util.ArrayList;
import java.util.List;

import bean.Projet;
import bean.Societe;
import bean.Tache;
import helper.Dispacher;
import helper.Session;
import service.TacheService;

public class TacheListActivity extends AppCompatActivity {

    TacheService tacheService = new TacheService(this);
    private RecyclerView tacheRecyclerView;
    List<Tache> taches;
    TacheAdapter tacheAdapter;
    SearchView searchView;

    private void injecterGUI() {
        tacheRecyclerView = findViewById(R.id.tacheRecyclerView);
    }

    private void initAdapter() {
        taches = tacheService.findAll();

        tacheAdapter = new TacheAdapter(taches);

        tacheRecyclerView.setAdapter(tacheAdapter);
        tacheRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        tacheRecyclerView.addItemDecoration(itemDecoration);
    }

    private void showBySociete(Long idSociete) {

        final List<Tache> tachesBySociete = new ArrayList<>();
        for (Tache tache : taches) {
            if (tache.getSociete() != null) {
                if (tache.getSociete().getId().equals(idSociete)) {
                    Log.d("tag", "noErrors");
                    tachesBySociete.add(tache);

                }
            }
        }
        tacheAdapter.setfilter(tachesBySociete);
        tacheAdapter.notifyDataSetChanged();
        Session.delete("societeRecherce");
    }

    private void showByProjet(Long idProjet) {

        final List<Tache> tachesByProjet = new ArrayList<>();
        for (Tache tache : taches) {
            if (tache.getProjet() != null) {
                if (tache.getProjet().getId().equals(idProjet)) {
                    Log.d("tag", "noErrors");
                    tachesByProjet.add(tache);
                }
            }
        }
        tacheAdapter.setfilter(tachesByProjet);
        tacheAdapter.notifyDataSetChanged();
        Session.delete("societeRecherce");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tache_list);
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


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dispacher.forward(TacheListActivity.this, TacheCreateActivity.class);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfile, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.search_id);
        searchView = (SearchView) myActionMenuItem.getActionView();
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Tache> filteredTaches = filter(taches, newText);
                tacheAdapter.setfilter(filteredTaches);
                return true;
            }
        });
        return true;
    }

    private List<Tache> filter(List<Tache> TachesUnfiltered, String query) {
        query = query.toLowerCase();
        final List<Tache> filteredTaches = new ArrayList<>();
        for (Tache tache : TachesUnfiltered) {
            final String text = String.valueOf(tache.getNbrHeures());
            if (text.contains(query)) {
                filteredTaches.add(tache);
            }
        }
        return filteredTaches;
    }

}
