package com.sharpinfo.sir.gestionprojet_v2.action.depense;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.ProjetSpinnerAdapter;
import com.sharpinfo.sir.gestionprojet_v2.adapter.SocieteSpinnerAdapter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Depense;
import bean.Projet;
import bean.Societe;
import helper.Dispacher;
import service.DepenseService;
import service.ProjetService;
import service.SocieteService;

public class DepenseCreateActivity extends AppCompatActivity {

    //tag for log
    private static final String TAG = "DepenseCreate";

    private EditText montantDepense;
    private EditText commentaireDepense;
    private EditText heurDepense;
    private Spinner societeSpinner;
    private Spinner projetSpinner;
    private TextView error;

    DepenseService depenseService = new DepenseService(this);
    SocieteService societeService = new SocieteService(this);
    ProjetService projetService = new ProjetService(this);

    private SocieteSpinnerAdapter societeSpinnerAdapter;
    private ProjetSpinnerAdapter projetSpinnerAdapter;

    private Societe societe = null;
    private Projet projet = null;

    Context context = this;
    private EditText editDate;
    private Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd-MM-yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());


    private void initSocieteSpinner() {
        societeSpinner = (Spinner) findViewById(R.id.societe_spinner);
        List<Societe> societes = societeService.findAll();
        societeSpinnerAdapter = new SocieteSpinnerAdapter(this, android.R.layout.simple_spinner_item, societes);
        societeSpinnerAdapter.add(new Societe(null, " --SELECT A SOCIETE-- "));
        societeSpinner.setAdapter(societeSpinnerAdapter);
        societeSpinnerAdapter.notifyDataSetChanged();
        societeSpinner.setSelection(societeSpinnerAdapter.getCount() + 1, true);
    }

    private void initProjetSpinner() {
        projetSpinner = (Spinner) findViewById(R.id.projet_spinner);
        List<Projet> projets = projetService.findAll();
        projetSpinnerAdapter = new ProjetSpinnerAdapter(this, android.R.layout.simple_spinner_item, projets);
        projetSpinnerAdapter.add(new Projet(null, " --SELECT A PROJECT-- "));
        projetSpinner.setAdapter(projetSpinnerAdapter);
        projetSpinnerAdapter.notifyDataSetChanged();
        projetSpinner.setSelection(projetSpinnerAdapter.getCount() + 1, true);
    }

    private void updateSocieteSpinner() {
        List<Societe> societes = societeService.findAll();
        societeSpinnerAdapter = new SocieteSpinnerAdapter(this, android.R.layout.simple_spinner_item, societes);
        societeSpinner.setAdapter(societeSpinnerAdapter);
        societeSpinnerAdapter.notifyDataSetChanged();
    }

    private void updateProjetSpinner() {
        List<Projet> projets = projetService.findAll();
        projetSpinnerAdapter = new ProjetSpinnerAdapter(this, android.R.layout.simple_spinner_item, projets);
        projetSpinner.setAdapter(projetSpinnerAdapter);
        projetSpinnerAdapter.notifyDataSetChanged();
    }

    private void updateDate() {
        editDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    private void initPopupDate() {
        // set calendar date and update editDate
        editDate = (EditText) findViewById(R.id.date_depense);
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

    private void initDate() {
        long currentdate = System.currentTimeMillis();
        String dateString = simpleDateFormat.format(currentdate);
        editDate = (EditText) findViewById(R.id.date_depense);
        editDate.setText(dateString);
        initPopupDate();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_create);
        initSocieteSpinner();
        getSocieteFromSpinner();
        initProjetSpinner();
        getProjetFromSpinner();
//        injectParam();
        long currentdate = System.currentTimeMillis();
        String dateString = simpleDateFormat.format(currentdate);
        editDate = (EditText) findViewById(R.id.date_depense);
//        editDate.setText(dateString);
        initPopupDate();
        initDate();
    }

    private Depense setParam() {
        Depense depense = new Depense();
        montantDepense = findViewById(R.id.montant);
        heurDepense = findViewById(R.id.heur_depense);
        commentaireDepense = findViewById(R.id.commentaire_depense);


        BigDecimal montantBigDecimal;
        String montantString = String.valueOf("" + montantDepense.getText());
        if (montantString.isEmpty()) {
            montantBigDecimal = BigDecimal.ZERO;
        } else {
            montantBigDecimal = new BigDecimal(montantString);
        }
        depense.setMontant(montantBigDecimal);
        depense.setHeur(heurDepense.getText() + "");
        depense.setCommentaire("" + commentaireDepense.getText());
        depense.setSociete(societe);
        depense.setProjet(projet);

        Date dateDepense = new Date();
        try {
            dateDepense = simpleDateFormat.parse(simpleDateFormat.format(myCalendar.getTime()));
            depense.setDate(dateDepense);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return depense;

    }

    public void createDepense(final View view) {
        final Depense depense = setParam();
//        Log.d("he", "========= montant: " + depense.getMontant() + " date " + depense.getDate() + " Heure " + depense.getHeur() + " comment " + depense.getCommentaire() + " projet " + depense.getProjet().getNom() + " societe " + depense.getSociete().getRaisonSociale());
        if (depense.getProjet().getId() == null && depense.getSociete().getId() == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(DepenseCreateActivity.this);
            alert.setTitle("Info");
            alert.setMessage("If you don't choose neither a project nor a company, the expense will be affected as personal, do you confirm ?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    depenseService.ajouterDepense(depense);
                    Dispacher.forward(DepenseCreateActivity.this, DepenseListActivity.class);
                    finish();
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

        } else if (depense.getSociete().getId() != null && depense.getProjet().getId() != null) {
            error = findViewById(R.id.error_depense);
            error.setText(R.string.error_depense);
        } else {
            depenseService.ajouterDepense(depense);
            Dispacher.forward(DepenseCreateActivity.this, DepenseListActivity.class);
            finish();
        }
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
}
