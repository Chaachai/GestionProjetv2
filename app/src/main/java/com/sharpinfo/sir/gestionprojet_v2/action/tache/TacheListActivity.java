package com.sharpinfo.sir.gestionprojet_v2.action.tache;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.depense.DepenseCreateActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.depense.DepenseListActivity;
import com.sharpinfo.sir.gestionprojet_v2.adapter.DepenseAdapter;
import com.sharpinfo.sir.gestionprojet_v2.adapter.TacheAdapter;

import java.util.List;

import bean.Depense;
import bean.Tache;
import helper.Dispacher;
import service.DepenseService;
import service.TacheService;

public class TacheListActivity extends AppCompatActivity {

    TacheService tacheService = new TacheService(this);
    private RecyclerView tacheRecyclerView;

    private void injecterGUI() {
        tacheRecyclerView = (RecyclerView) findViewById(R.id.tacheRecyclerView);
    }

    private void initAdapter() {
        List<Tache> taches = tacheService.findAll();

        TacheAdapter tacheAdapter = new TacheAdapter(taches);

        tacheRecyclerView.setAdapter(tacheAdapter);
        tacheRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tache_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        injecterGUI();
        initAdapter();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dispacher.forward(TacheListActivity.this, TacheCreateActivity.class);
            }
        });
    }

}
