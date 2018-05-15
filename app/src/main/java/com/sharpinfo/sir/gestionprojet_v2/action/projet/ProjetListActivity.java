package com.sharpinfo.sir.gestionprojet_v2.action.projet;

import android.app.Activity;
import android.content.Context;
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
import com.sharpinfo.sir.gestionprojet_v2.adapter.ProjetAdapter;

import java.util.List;

import bean.Projet;
import helper.Dispacher;
import service.ProjetService;

public class ProjetListActivity extends AppCompatActivity {
    private Context mContext = this;
    RecyclerView projetRecyclerView;

    ProjetService projetService = new ProjetService(mContext);

    private void injecterGUI() {
        projetRecyclerView = (RecyclerView) findViewById(R.id.projetRecyclerView);
    }

    private void initAdapter() {
        List<Projet> projets = projetService.findAll();

        ProjetAdapter projetAdapter = new ProjetAdapter(projets);

        projetRecyclerView.setAdapter(projetAdapter);
        projetRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            }
        });
    }

}
