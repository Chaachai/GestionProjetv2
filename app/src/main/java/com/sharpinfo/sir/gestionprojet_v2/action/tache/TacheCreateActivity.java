package com.sharpinfo.sir.gestionprojet_v2.action.tache;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.notification.NotificationReceiverTache;
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
    private EditText heureDebutTache;
    private EditText heureFinTache;
    private Spinner societeSpinner;
    private Spinner projetSpinner;
    private TextView error;

    //TimePicker HeureDebut
    TimePickerDialog timePickerDialogDebut;
    Calendar calendarDebut;
    int heureDebut;
    int minuteDebut;

    int hourDebutTache;
    int minuteDebutTache;
    ///

    //TimePicker HeureFin
    TimePickerDialog timePickerDialogFin;
    Calendar calendarFin;
    int heureFin;
    int minuteFin;

    int hourFinTache;
    int minuteFinTache;
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
        societeSpinnerAdapter.add(new Societe(null, " ------CHOIX SOCIETE------ "));
        societeSpinner.setAdapter(societeSpinnerAdapter);
        societeSpinnerAdapter.notifyDataSetChanged();
        societeSpinner.setSelection(societeSpinnerAdapter.getCount() + 1, true);
    }

    private void initProjetSpinner() {
        projetSpinner = findViewById(R.id.projet_spinner_tache);
        List<Projet> projets = projetService.findAll();
        projetSpinnerAdapter = new ProjetSpinnerAdapter(this, android.R.layout.simple_spinner_item, projets);
        projetSpinnerAdapter.add(new Projet(null, " ------CHOIX  PROJET------ "));
        projetSpinner.setAdapter(projetSpinnerAdapter);
        projetSpinnerAdapter.notifyDataSetChanged();
        projetSpinner.setSelection(projetSpinnerAdapter.getCount() + 1, true);
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
        initHeureDebutPicker();
        initHeureFinPicker();
        notifyUser();
//        injectParam();
//        long currentdate = System.currentTimeMillis();
//        String dateString = simpleDateFormat.format(currentdate);
//        editDate = (EditText) findViewById(R.id.date_tache);
//        editDate.setText(dateString);
        initPopupDate();
        initDate();
    }

    private void notifyUser() {
        Intent intent = new Intent(getApplicationContext(), NotificationReceiverTache.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 4444, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        assert alarmManager != null;
        Log.d("tacheNotif", "tachenotif");
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + AlarmManager.INTERVAL_HOUR,
                pendingIntent);
    }

    private void initHeureDebutPicker() {
        heureDebutTache = findViewById(R.id.heure_debut_tache);
        //////
        heureDebutTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDebut = Calendar.getInstance();
                heureDebut = calendarDebut.get(Calendar.HOUR_OF_DAY);
                minuteDebut = calendarDebut.get(Calendar.MINUTE);

                timePickerDialogDebut = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heureDebutTache.setText(String.format("%02d:%02d", hourOfDay, minute));
                        hourDebutTache = hourOfDay;
                        minuteDebutTache = minute;
                        Log.d("insideTacheCreate", hourDebutTache + " :" + minuteDebutTache);
                    }
                }, heureDebut, minuteDebut, true);

                timePickerDialogDebut.show();
            }
        });
    }

    private void initHeureFinPicker() {
        heureFinTache = findViewById(R.id.heure_fin_tache);
        //////
        heureFinTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarFin = Calendar.getInstance();
                heureFin = calendarFin.get(Calendar.HOUR_OF_DAY);
                minuteFin = calendarFin.get(Calendar.MINUTE);

                timePickerDialogFin = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heureFinTache.setText(String.format("%02d:%02d", hourOfDay, minute));
                        hourFinTache = hourOfDay;
                        minuteFinTache = minute;
                        Log.d("insideTacheFin", hourFinTache + ":" + minuteFinTache);
                    }
                }, heureFin, minuteFin, true);
                timePickerDialogFin.show();
            }
        });
    }

    private Integer calculateNbrOfMinutes() {
       int hourFinTacheInMinutes = hourFinTache * 60;
        Integer totalHeureFin = hourFinTacheInMinutes + minuteFinTache;
        Log.d("tacheCreate", totalHeureFin.toString());
        int hourDebutTacheInMinutes = hourDebutTache * 60;
        Integer totalHeureDebut = hourDebutTacheInMinutes + minuteDebutTache;
        Log.d("tacheCreate", totalHeureDebut.toString());

        Integer nbrMinute = totalHeureFin - totalHeureDebut;
        if (nbrMinute == 0) {
            return -2;
        }
        if (nbrMinute > 0) {
            return nbrMinute;
        }
        if (nbrMinute < 0) {
            return -1;
        }
        return 0;
    }

    private Tache setParam() {
        Tache tache = new Tache();


        nbrHeurTache = findViewById(R.id.heure_fin_tache);
        commentaireTache = findViewById(R.id.commentaire_tache);


        /////
//        double montantDouble = Double.valueOf("" + montantDepense.getText());
//        BigDecimal montantBigDecimal = BigDecimal.valueOf(montantDouble);

        Integer totalMinutes = calculateNbrOfMinutes();
        tache.setNbrHeures(totalMinutes);

        tache.setCommentaire("" + commentaireTache.getText());
        tache.setHeureDebut("" + heureDebutTache.getText());
        tache.setHeureFin("" + heureFinTache.getText());
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

    public void createTache(View view) {
        final Tache tache = setParam();
        error = findViewById(R.id.error_tache);
        if (tache.getNbrHeures() == -1) {
            error.setText("L'heure debut est superieur a l'heure fin");
            heureFinTache.setText("");
            heureDebutTache.setText("");


        } else if (tache.getNbrHeures() == -2) {
            error.setText("Veuillez choisir une heure debut et une heure fin");
        } else {
            if (tache.getProjet().getId() == null && tache.getSociete().getId() == null) {
                AlertDialog.Builder alert = new AlertDialog.Builder(TacheCreateActivity.this);
                alert.setTitle("Info");
                alert.setMessage("Si vous ne choisissez pas de societe , le temps sera affecté comme personel, confirmez-vous?");
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tacheService.ajouterTache(tache);
                        Dispacher.forward(TacheCreateActivity.this, TacheListActivity.class);
                        finish();
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();

            } else if (tache.getSociete().getId() != null && tache.getProjet().getId() != null) {

                error.setText(R.string.error_depense);
            } else {
                tacheService.ajouterTache(tache);
                Dispacher.forward(TacheCreateActivity.this, TacheListActivity.class);
                finish();
            }
        }

//        tacheService.create(tache);
//        Dispacher.forward(this, TacheListActivity.class);

    }

    private void getSocieteFromSpinner() {
        societeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                societe = societeSpinnerAdapter.getItem(position);
                assert societe != null;
                if (societe.getId() == null) {
                    societe = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getProjetFromSpinner() {
        projetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projet = projetSpinnerAdapter.getItem(position);
                assert projet != null;
                if (projet.getId() == null) {
                    projet = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
