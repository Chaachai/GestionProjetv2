package com.sharpinfo.sir.gestionprojet_v2.action.dashboard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.ProjetSpinnerAdapter;
import com.sharpinfo.sir.gestionprojet_v2.adapter.SocieteSpinnerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Depense;
import bean.Projet;
import bean.Societe;
import helper.Dispacher;
import helper.Session;
import service.DepenseService;
import service.ProjetService;
import service.SocieteService;

public class DepenseDashboardActivity extends AppCompatActivity {
    private Spinner societeSpinner;
    private Spinner projetSpinner;
    private EditText editDateMin;
    private EditText editDateMax;
    private ImageButton cleatDateMin;
    private ImageButton cleatDateMax;
    private TextView error;
    private Calendar myCalendarMin = Calendar.getInstance();
    private Calendar myCalendarMax = Calendar.getInstance();

    private Societe societe = null;
    private Projet projet = null;

    private SocieteService societeService = new SocieteService(this);
    private ProjetService projetService = new ProjetService(this);
    private DepenseService depenseService = new DepenseService(this);

    private SocieteSpinnerAdapter societeSpinnerAdapter;
    private ProjetSpinnerAdapter projetSpinnerAdapter;

    Context context = this;
    String dateFormat = "dd-MM-yyyy";
    DatePickerDialog.OnDateSetListener dateMin;
    DatePickerDialog.OnDateSetListener dateMax;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());


    // ************************************************ DATE MIN **********************************************************

    private void updateDateMin() {
        editDateMin.setText(simpleDateFormat.format(myCalendarMin.getTime()));
    }

    private void initPopupDateMin() {
        editDateMin = (EditText) findViewById(R.id.dashboard_depense_date_min);
        dateMin = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarMin.set(Calendar.YEAR, year);
                myCalendarMin.set(Calendar.MONTH, monthOfYear);
                myCalendarMin.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateMin();
            }

        };

        // onclick - popup datepicker
        editDateMin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, dateMin, myCalendarMin
                        .get(Calendar.YEAR), myCalendarMin.get(Calendar.MONTH),
                        myCalendarMin.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initDateMin() {
        long currentdate = System.currentTimeMillis();
        String dateString = simpleDateFormat.format(currentdate);
        editDateMin = (EditText) findViewById(R.id.dashboard_depense_date_min);
//        editDate.setText(dateString);
        initPopupDateMin();
    }

    // ************************************************ DATE MAX **********************************************************

    private void updateDateMax() {
        editDateMax.setText(simpleDateFormat.format(myCalendarMax.getTime()));
    }

    private void initPopupDateMax() {
        editDateMax = (EditText) findViewById(R.id.dashboard_depense_date_max);
        dateMax = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarMax.set(Calendar.YEAR, year);
                myCalendarMax.set(Calendar.MONTH, monthOfYear);
                myCalendarMax.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateMax();
            }

        };

        // onclick - popup datepicker
        editDateMax.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, dateMax, myCalendarMax
                        .get(Calendar.YEAR), myCalendarMax.get(Calendar.MONTH),
                        myCalendarMax.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initDateMax() {
        long currentdate = System.currentTimeMillis();
        String dateString = simpleDateFormat.format(currentdate);
        editDateMax = (EditText) findViewById(R.id.dashboard_depense_date_max);
//        editDate.setText(dateString);
        initPopupDateMin();
    }


    // ************************************************ SOCIETE SPINNER **********************************************************


    private void initSocieteSpinner() {
        societeSpinner = (Spinner) findViewById(R.id.dashboard_depense_societe_spinner);
        List<Societe> societes = societeService.findAll();
        societeSpinnerAdapter = new SocieteSpinnerAdapter(this, android.R.layout.simple_spinner_item, societes);
        societeSpinnerAdapter.add(new Societe(null, " --SELECT A COMPANY-- "));
        societeSpinner.setAdapter(societeSpinnerAdapter);
        societeSpinnerAdapter.notifyDataSetChanged();
        societeSpinner.setSelection(societeSpinnerAdapter.getCount() + 1, true);
    }

    private Societe getSocieteFromSpinner() {
        societeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                societe = societeSpinnerAdapter.getItem(position);
                if (societe.getId() == null) {
                    societe = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return societe;
    }


    // ************************************************ PROJET SPINNER **********************************************************


    private void initProjetSpinner() {
        projetSpinner = (Spinner) findViewById(R.id.dashboard_depense_projet_spinner);
        List<Projet> projets = projetService.findAll();
        projetSpinnerAdapter = new ProjetSpinnerAdapter(this, android.R.layout.simple_spinner_item, projets);
        projetSpinnerAdapter.add(new Projet(null, " --SELECT A PROJECT-- "));
        projetSpinner.setAdapter(projetSpinnerAdapter);
        projetSpinnerAdapter.notifyDataSetChanged();
        projetSpinner.setSelection(projetSpinnerAdapter.getCount() + 1, true);
    }

    private Projet getProjetFromSpinner() {
        projetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projet = projetSpinnerAdapter.getItem(position);
                if (projet.getId() == null) {
                    projet = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return projet;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_dashboard);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        initSocieteSpinner();
        getSocieteFromSpinner();

        initProjetSpinner();
        getProjetFromSpinner();

        editDateMin = (EditText) findViewById(R.id.dashboard_depense_date_min);
        initPopupDateMin();
        initDateMin();

        editDateMax = (EditText) findViewById(R.id.dashboard_depense_date_max);
        initPopupDateMax();
        initDateMax();

        cleatDateMin = findViewById(R.id.clear_date_min);
        cleatDateMax = findViewById(R.id.clear_date_max);

        cleatDateMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDateMin.setText("");
            }
        });

        cleatDateMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDateMax.setText("");
            }
        });
    }

    public void recherche(View view) {

        error = findViewById(R.id.error_depense_dashboard);

        if (societe != null && projet != null) {
            error.setText(R.string.error_depense_criteria);
        } else {

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

            editDateMin = findViewById(R.id.dashboard_depense_date_min);
            editDateMax = findViewById(R.id.dashboard_depense_date_max);

            String dateMinString = editDateMin.getText() + "";
            String dateMaxString = editDateMax.getText() + "";

            Date dateMin = null;
            Date dateMax = null;

            if (!dateMinString.isEmpty()) {
                try {
                    dateMin = format.parse(editDateMin.getText() + "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (!dateMaxString.isEmpty()) {
                try {
                    dateMax = format.parse(editDateMax.getText() + "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Session.setAttribute(societe, "societeCriteria");
            Session.setAttribute(projet, "projetCriteria");
            Session.setAttribute(dateMin, "dateMinCriteria");
            Session.setAttribute(dateMax, "dateMaxCriteria");


            Dispacher.forward(DepenseDashboardActivity.this, DepenseListDashboardActivity.class);
        }
    }
}
