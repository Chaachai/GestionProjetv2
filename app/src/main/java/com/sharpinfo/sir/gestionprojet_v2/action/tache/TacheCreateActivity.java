package com.sharpinfo.sir.gestionprojet_v2.action.tache;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.ProjetSpinnerAdapter;
import com.sharpinfo.sir.gestionprojet_v2.adapter.SocieteSpinnerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Projet;
import bean.Societe;
import bean.Tache;
import helper.Dispacher;
import helper.Session;
import service.ProjetService;
import service.SocieteService;
import service.TacheService;

public class TacheCreateActivity extends AppCompatActivity {

    //tag for log
    private static final String TAG = "TacheCreate";

    private EditText nbrHeurTache;
    private EditText commentaireTache;
    private EditText heurTache;
    private Spinner societeSpinner;
    private Spinner projetSpinner;
    private TextView error;

    //TimePicker
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    ///
    TacheService tacheService = new TacheService(this);
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
        societeSpinner = findViewById(R.id.societe_spinner_tache);
        List<Societe> societes = societeService.findAll();
        societeSpinnerAdapter = new SocieteSpinnerAdapter(this, android.R.layout.simple_spinner_item, societes);
        societeSpinnerAdapter.add(new Societe(null, " ------SELECT A COMPANY------ "));
        societeSpinner.setAdapter(societeSpinnerAdapter);
        societeSpinnerAdapter.notifyDataSetChanged();
        societeSpinner.setSelection(societeSpinnerAdapter.getCount() + 1, true);
    }

    private void initProjetSpinner() {
        projetSpinner = findViewById(R.id.projet_spinner_tache);
        List<Projet> projets = projetService.findAll();
        projetSpinnerAdapter = new ProjetSpinnerAdapter(this, android.R.layout.simple_spinner_item, projets);
        projetSpinnerAdapter.add(new Projet(null, " ------SELECT A PROJECT------ "));
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
        editDate = findViewById(R.id.date_tache);
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
        editDate = findViewById(R.id.date_tache);
        editDate.setText(dateString);
        initPopupDate();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tache_create);

        Session.setAttribute(context, "tacheContext");

        initSocieteSpinner();
        getSocieteFromSpinner();
        initProjetSpinner();
        getProjetFromSpinner();
        initHeurePicker();
//        injectParam();
//        long currentdate = System.currentTimeMillis();
//        String dateString = simpleDateFormat.format(currentdate);
//        editDate = (EditText) findViewById(R.id.date_tache);
//        editDate.setText(dateString);
        initPopupDate();
        initDate();
    }

    private void initHeurePicker() {
        heurTache = findViewById(R.id.heur_tache);
        //////
        heurTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heurTache.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });
    }

    private Tache setParam() {
        Tache tache = new Tache();


        nbrHeurTache = findViewById(R.id.duree_tache);
        commentaireTache = findViewById(R.id.commentaire_tache);


        /////
//        double montantDouble = Double.valueOf("" + montantDepense.getText());
//        BigDecimal montantBigDecimal = BigDecimal.valueOf(montantDouble);
        Double nbrHeurDouble;
        String nbrHeurString = String.valueOf("" + nbrHeurTache.getText());
        if (nbrHeurString.isEmpty()) {
            nbrHeurDouble = 0.0;
        } else {
            nbrHeurDouble = Double.valueOf(nbrHeurString);
        }

        tache.setNbrHeures(nbrHeurDouble);
        tache.setCommentaire("" + commentaireTache.getText());
        tache.setHeur("" + heurTache.getText());
        tache.setSociete(societe);
        tache.setProjet(projet);

        Date dateTache = new Date();
        try {
            dateTache = simpleDateFormat.parse(simpleDateFormat.format(myCalendar.getTime()));
            tache.setDate(dateTache);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tache;

    }

    public void createDepense(View view) {
        final Tache tache = setParam();
        if (tache.getProjet().getId() == null && tache.getSociete().getId() == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(TacheCreateActivity.this);
            alert.setTitle("Info");
            alert.setMessage("If you don't choose neither a project nor a company, the expense will be affected as personal, do you confirm ?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (tache.getHeur().isEmpty()) {
                        tache.setHeur("--:--");
                        tacheService.ajouterTache(tache);
                        Dispacher.forward(TacheCreateActivity.this, TacheListActivity.class);
                        finish();
                    } else {
                        tacheService.ajouterTache(tache);
                        Dispacher.forward(TacheCreateActivity.this, TacheListActivity.class);
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

        } else if (tache.getSociete().getId() != null && tache.getProjet().getId() != null) {
            error = findViewById(R.id.error_tache);
            error.setText(R.string.error_depense);
        } else {
            if (tache.getHeur().isEmpty()) {
                tache.setHeur("--:--");
                tacheService.ajouterTache(tache);
                Dispacher.forward(TacheCreateActivity.this, TacheListActivity.class);
                finish();
            } else {
                tacheService.ajouterTache(tache);
                Dispacher.forward(TacheCreateActivity.this, TacheListActivity.class);
                finish();
            }
        }

//        tacheService.create(tache);
//        Dispacher.forward(this, TacheListActivity.class);
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
