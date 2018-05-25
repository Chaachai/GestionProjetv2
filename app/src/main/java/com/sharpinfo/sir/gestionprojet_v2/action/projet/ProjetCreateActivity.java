package com.sharpinfo.sir.gestionprojet_v2.action.projet;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    private TextView error;

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
        editDate = findViewById(R.id.projet_date);
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
        societeSpinner = findViewById(R.id.societe_spinner);
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
        editDate = findViewById(R.id.projet_date);
//        editDate.setText(dateString);
        initPopupDate();
        initDate();
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

    public void createProjet(View view) {
        final Projet projet = setParam();
        error = findViewById(R.id.error_create_projet);
        if (projet.getNom().isEmpty()) {
            error.setText(R.string.nom_du_projet_required);
        } else if (projet.getSociete().getId() == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(ProjetCreateActivity.this);
            alert.setTitle("Info");
            alert.setMessage("If you don't choose a company, the project will be affected as personal, do you confirm ?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (projet.getDescription().isEmpty()) {
                        projet.setDescription("No description is available !");
                        projetService.create(projet);
                        Dispacher.forward(ProjetCreateActivity.this, ProjetListActivity.class);
                        finish();
                    } else {
                        projetService.create(projet);
                        Dispacher.forward(ProjetCreateActivity.this, ProjetListActivity.class);
                        finish();
                    }
                    dialog.dismiss();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alert.show();
        } else {
            if(projet.getDescription().isEmpty()){
                projet.setDescription("No description is available !");
                projetService.create(projet);
                Dispacher.forward(ProjetCreateActivity.this, ProjetListActivity.class);
                finish();
            }else {
                projetService.create(projet);
                Dispacher.forward(ProjetCreateActivity.this, ProjetListActivity.class);
                finish();
            }
        }
    }

    private void initDate() {
        long currentdate = System.currentTimeMillis();
        String dateString = simpleDateFormat.format(currentdate);
        editDate = findViewById(R.id.projet_date);
//        editDate.setText(dateString);
        initPopupDate();
    }
}
