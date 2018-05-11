package com.sharpinfo.sir.gestionprojet_v2.action.depense;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.DepenseAdapter;

import java.util.List;

import bean.Depense;
import helper.Dispacher;
import service.DepenseService;

public class DepenseListActivity extends AppCompatActivity {

    DepenseService depenseService = new DepenseService(this);
    RecyclerView depenseRecyclerView;

    private void injecterGUI() {
        depenseRecyclerView = (RecyclerView) findViewById(R.id.depenseRecyclerView);
    }

    private void initAdapter() {
        List<Depense> depenses = depenseService.findAll();

        DepenseAdapter depenseAdapter = new DepenseAdapter(depenses);

        depenseRecyclerView.setAdapter(depenseAdapter);
        depenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_list);
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
                Dispacher.forward(DepenseListActivity.this, DepenseCreateActivity.class);
            }
        });
    }

}
