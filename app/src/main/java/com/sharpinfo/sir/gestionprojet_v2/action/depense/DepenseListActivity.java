package com.sharpinfo.sir.gestionprojet_v2.action.depense;

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
import com.sharpinfo.sir.gestionprojet_v2.adapter.DepenseAdapter;

import java.util.ArrayList;
import java.util.List;

import bean.Depense;
import helper.Dispacher;
import service.DepenseService;


public class DepenseListActivity extends AppCompatActivity {

    DepenseService depenseService = new DepenseService(this);
    private RecyclerView depenseRecyclerView;
    List<Depense> depenses;
    DepenseAdapter depenseAdapter;
    SearchView searchView;

    private void injecterGUI() {
        depenseRecyclerView = (RecyclerView) findViewById(R.id.depenseRecyclerView);
    }

    private void initAdapter() {
        depenses = depenseService.findAll();

        depenseAdapter = new DepenseAdapter(depenses);

        depenseRecyclerView.setAdapter(depenseAdapter);
        depenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        depenseRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_list);
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
                Dispacher.forward(DepenseListActivity.this, DepenseCreateActivity.class);
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
                final List<Depense> filteredDepenses = filter(depenses, newText);
                depenseAdapter.setfilter(filteredDepenses);
                return true;
            }
        });
        return true;
    }

    private List<Depense> filter(List<Depense> depensesUnfiltered, String query) {
        query = query.toLowerCase();
        final List<Depense> filteredDepenses = new ArrayList<>();
        for (Depense depense : depensesUnfiltered) {
            final String text = depense.getMontant().toString();
            if (text.contains(query)) {
                filteredDepenses.add(depense);
            }
        }
        return filteredDepenses;
    }

}
