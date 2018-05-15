package com.sharpinfo.sir.gestionprojet_v2.action.depense;

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
    private Spinner societeSpinner;
    private Spinner projetSpinner;
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
    }

    private Depense setParam() {
        Depense depense = new Depense();
        montantDepense = findViewById(R.id.montant);
        commentaireDepense = findViewById(R.id.commentaire_depense);

        double montantDouble = Double.valueOf("" + montantDepense.getText());
        BigDecimal montantBigDecimal = BigDecimal.valueOf(montantDouble);

        depense.setMontant(montantBigDecimal);
        depense.setCommentaire("" + commentaireDepense.getText());
        depense.setSociete(societe);
        depense.setProjet(projet);

        Date dateDepense = new Date();
        try {
            //hadi hya la date li khsha tmchi l sqllite
            dateDepense = simpleDateFormat.parse(simpleDateFormat.format(myCalendar.getTime()));
            depense.setDate(dateDepense);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return depense;

    }

    public void createDepense(View view) {
        Depense depense = setParam();
        Log.d("he", "========= montant: " + depense.getMontant() + " date " + depense.getDate() + " comment " + depense.getCommentaire() + " projet " + depense.getProjet() + " societe " + depense.getSociete());
        depenseService.create(depense);
        Dispacher.forward(this, DepenseListActivity.class);
        finish();
    }

    private Societe getSocieteFromSpinner() {
        societeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                societe = societeSpinnerAdapter.getItem(position);
                if (societe.getId() == null) {
                    societe = null;
                }

                Log.d("test", "no error");
                Log.d(TAG, "2");
                Log.d(TAG, societe.getRaisonSociale());
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

                Log.d("test", "no error");
                Log.d(TAG, "2");
                Log.d(TAG, projet.getNom());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return projet;
    }
}
