package com.sharpinfo.sir.gestionprojet_v2.action.societe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.sharpinfo.sir.gestionprojet_v2.adapter.SocieteAdapter;

import java.util.ArrayList;
import java.util.List;

import bean.Societe;
import helper.Dispacher;
import service.SocieteService;

public class SocieteListActivity extends AppCompatActivity {

    SocieteService societeService = new SocieteService(this);
    RecyclerView societeRecyclerView;
    SearchView searchView;
    SocieteAdapter societeAdapter;
    List<Societe> societes;

    private void injecterGUI() {
        societeRecyclerView = (RecyclerView) findViewById(R.id.societeRecyclerView);
    }

    private void initAdapter() {
        societes = societeService.findAll();

        societeAdapter = new SocieteAdapter(societes);

        societeRecyclerView.setAdapter(societeAdapter);
        societeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        societeRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_societe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        //populate data in the recyclerView
        injecterGUI();
        initAdapter();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dispacher.forward(SocieteListActivity.this, SocieteCreateActivity.class);
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
                final List<Societe> filteredSocietes = filter(societes, newText);
                societeAdapter.setfilter(filteredSocietes);
                return true;
            }
        });
        return true;
    }

    private List<Societe> filter(List<Societe> societesUnfiltered, String query) {
        query = query.toLowerCase();
        final List<Societe> filteredSocietes = new ArrayList<>();
        for (Societe societe : societesUnfiltered) {
            final String text = societe.getRaisonSociale().toLowerCase();
            if (text.contains(query)) {
                filteredSocietes.add(societe);
            }
        }
        return filteredSocietes;
    }
}
