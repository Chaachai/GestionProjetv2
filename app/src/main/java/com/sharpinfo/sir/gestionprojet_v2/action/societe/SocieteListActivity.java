package com.sharpinfo.sir.gestionprojet_v2.action.societe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.SocieteAdapter;

import java.util.List;

import bean.Societe;
import helper.Dispacher;
import service.SocieteService;

public class SocieteListActivity extends AppCompatActivity {

    SocieteService societeService = new SocieteService(this);
    RecyclerView societeRecyclerView;

    private void injecterGUI() {
        societeRecyclerView = (RecyclerView) findViewById(R.id.societeRecyclerView);
    }

    private void initAdapter() {
        List<Societe> societes = societeService.findAll();

        SocieteAdapter societeAdapter = new SocieteAdapter(societes);

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

}
