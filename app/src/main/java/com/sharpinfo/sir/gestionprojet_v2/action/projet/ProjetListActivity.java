package com.sharpinfo.sir.gestionprojet_v2.action.projet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.ProjetAdapter;

import java.util.ArrayList;
import java.util.List;

import bean.Projet;
import helper.Dispacher;
import service.ProjetService;

public class ProjetListActivity extends AppCompatActivity {
    private Context mContext = this;
    RecyclerView projetRecyclerView;
    SearchView searchView;
    ProjetAdapter projetAdapter;

    List<Projet> projets;
    ProjetService projetService = new ProjetService(mContext);

    private void injecterGUI() {
        projetRecyclerView = (RecyclerView) findViewById(R.id.projetRecyclerView);
    }

    private void initAdapter() {
        projets = projetService.findAll();

        projetAdapter = new ProjetAdapter(projets);

        projetRecyclerView.setAdapter(projetAdapter);
        projetRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        projetRecyclerView.addItemDecoration(itemDecoration);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projet_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        injecterGUI();
        initAdapter();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Dispacher.forward(ProjetListActivity.this, ProjetCreateActivity.class);
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
                final List<Projet> filteredProjets = filter(projets, newText);
                projetAdapter.setfilter(filteredProjets);
                return true;
            }
        });
        return true;
    }

    private List<Projet> filter(List<Projet> projetsUnfiltered, String query) {
        query = query.toLowerCase();
        final List<Projet> filteredSocietes = new ArrayList<>();
        for (Projet projet : projetsUnfiltered) {
            final String text = projet.getNom().toLowerCase();
            if (text.contains(query)) {
                filteredSocietes.add(projet);
            }
        }
        return filteredSocietes;
    }

}
