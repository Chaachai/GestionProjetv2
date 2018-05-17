package com.sharpinfo.sir.gestionprojet_v2.action.projet;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.SocieteSpinnerAdapter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Projet;
import bean.Societe;
import helper.Dispacher;
import service.ProjetService;
import service.SocieteService;

public class ProjetCreateActivity extends AppCompatActivity {

    private EditText nomProjet;
    private EditText descriptionProjet;
    private EditText budgetProjet;
    private Spinner societeSpinner;

    private Societe societe = null;
    private SocieteService societeService = new SocieteService(this);
    private ProjetService projetService = new ProjetService(this);

    private SocieteSpinnerAdapter societeSpinnerAdapter;

    Context context = this;
    private EditText editDate;
    private Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd-MM-yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());

    private void updateDate() {
        editDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    private void initPopupDate() {
        // set calendar date and update editDate
        editDate = (EditText) findViewById(R.id.projet_date);
        date = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        // onclick - popup datepicker
        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initSocieteSpinner() {
        societeSpinner = (Spinner) findViewById(R.id.societe_spinner);
        List<Societe> societes = societeService.findAll();
        societeSpinnerAdapter = new SocieteSpinnerAdapter(this, android.R.layout.simple_spinner_item, societes);
        societeSpinnerAdapter.add(new Societe(null, " ------SELECT A COMPANY------ "));
        societeSpinner.setAdapter(societeSpinnerAdapter);
        societeSpinnerAdapter.notifyDataSetChanged();
        societeSpinner.setSelection(societeSpinnerAdapter.getCount() + 1, true);
    }

    private void updateSocieteSpinner() {
        List<Societe> societes = societeService.findAll();
        societeSpinnerAdapter = new SocieteSpinnerAdapter(this, android.R.layout.simple_spinner_item, societes);
        societeSpinner.setAdapter(societeSpinnerAdapter);
        societeSpinnerAdapter.notifyDataSetChanged();
    }

    private Projet setParam() {
        Projet projet = new Projet();
        nomProjet = findViewById(R.id.projet_nom);
        descriptionProjet = findViewById(R.id.description_projet);
        budgetProjet = findViewById(R.id.budget_projet);

        //test bigdecimal if empty
        BigDecimal montantBigDecimal;
        String montantString = String.valueOf("" + budgetProjet.getText());
        if (montantString.isEmpty()) {
            montantBigDecimal = BigDecimal.ZERO;
        } else {
            montantBigDecimal = new BigDecimal(montantString);
        }
        projet.setBudget(montantBigDecimal);

        projet.setNom(String.valueOf(nomProjet.getText()));
        projet.setDescription(String.valueOf(descriptionProjet.getText()));
//        Societe societe = getSocieteFromSpinner();
//        if (societe == null) {
//            societe = new Societe();
//        }
//        Log.d("setParam", societe.toString());
        projet.setSociete(societe);
        Date dateProjet = new Date();
        try {
            //hadi hya la date li khsha tmchi l sqllite
            dateProjet = simpleDateFormat.parse(simpleDateFormat.format(myCalendar.getTime()));
            projet.setDateDebut(dateProjet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("insideSetParam", projet.toString());
        return projet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projet_create);

        initSocieteSpinner();
        getSocieteFromSpinner();
        long currentdate = System.currentTimeMillis();
        String dateString = simpleDateFormat.format(currentdate);
        editDate = (EditText) findViewById(R.id.projet_date);
//        editDate.setText(dateString);
        initPopupDate();
        initDate();
    }

    private Societe getSocieteFromSpinner() {
        societeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    societe = societeSpinnerAdapter.getItem(position);
                    Log.d("test", "no error");
                    Log.d("tag", "2");
                    Log.d("tag", societe.getRaisonSociale());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return societe;
    }

    public void createProjet(View view) {

        Projet projet = setParam();
        Log.d("insideCreateProjet", projet.toString());

        projetService.create(projet);
//        Toast.makeText(getBaseContext(), "Societe cree avec succes! " + projet.getNom() + " " + projet.getBudget() + ", !", Toast.LENGTH_LONG).show();

        Dispacher.forward(ProjetCreateActivity.this, ProjetListActivity.class);
        finish();
    }

    private void initDate() {
        long currentdate = System.currentTimeMillis();
        String dateString = simpleDateFormat.format(currentdate);
        editDate = (EditText) findViewById(R.id.projet_date);
//        editDate.setText(dateString);
        initPopupDate();
    }
}
