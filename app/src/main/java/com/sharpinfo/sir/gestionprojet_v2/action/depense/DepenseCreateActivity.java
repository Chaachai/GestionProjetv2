package com.sharpinfo.sir.gestionprojet_v2.action.depense;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.DepenseTypeSpinnerAdapter;
import com.sharpinfo.sir.gestionprojet_v2.adapter.ProjetSpinnerAdapter;
import com.sharpinfo.sir.gestionprojet_v2.adapter.SocieteSpinnerAdapter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Depense;
import bean.DepenseType;
import bean.Projet;
import bean.Societe;
import helper.Dispacher;
import helper.Session;
import service.DepenseService;
import service.DepenseTypeService;
import service.ProjetService;
import service.SocieteService;

public class DepenseCreateActivity extends AppCompatActivity {

    //tag for log
    private static final String TAG = "DepenseCreate";

    private EditText montantDepense;
    private EditText heurDepense;
    private Spinner societeSpinner;
    private Spinner projetSpinner;
    private Spinner depenseTypeSpinner;
    private TextView error;
    private Button depenseTypeCreateBtn;

    //TimePicker
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    ///

    DepenseService depenseService = new DepenseService(this);
    SocieteService societeService = new SocieteService(this);
    ProjetService projetService = new ProjetService(this);
    DepenseTypeService depenseTypeService = new DepenseTypeService(this);

    private SocieteSpinnerAdapter societeSpinnerAdapter;
    private ProjetSpinnerAdapter projetSpinnerAdapter;
    private DepenseTypeSpinnerAdapter depenseTypeSpinnerAdapter;

    private Societe societe = null;
    private Projet projet = null;
    private DepenseType depenseType = null;


    Context context = this;
    private EditText editDate;
    private Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd-MM-yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());

    private void initDepenseTypeSpinner() {
        depenseTypeSpinner = findViewById(R.id.depense_type_spinner);
        List<DepenseType> depenseTypes = depenseTypeService.findAll();
        depenseTypeSpinnerAdapter = new DepenseTypeSpinnerAdapter(this, android.R.layout.simple_spinner_item, depenseTypes);
        depenseTypeSpinnerAdapter.add(new DepenseType(null, " --CHOIX DU TYPE-- "));
        depenseTypeSpinner.setAdapter(depenseTypeSpinnerAdapter);
        depenseTypeSpinnerAdapter.notifyDataSetChanged();
        depenseTypeSpinner.setSelection(depenseTypeSpinnerAdapter.getCount() + 1, true);
    }

    private void initSocieteSpinner() {
        societeSpinner = findViewById(R.id.societe_spinner);
        List<Societe> societes = societeService.findAll();
        societeSpinnerAdapter = new SocieteSpinnerAdapter(this, android.R.layout.simple_spinner_item, societes);
        societeSpinnerAdapter.add(new Societe(null, " --SELECT A SOCIETE-- "));
        societeSpinner.setAdapter(societeSpinnerAdapter);
        societeSpinnerAdapter.notifyDataSetChanged();
        societeSpinner.setSelection(societeSpinnerAdapter.getCount() + 1, true);
    }

    private void initProjetSpinner() {
        projetSpinner = findViewById(R.id.projet_spinner);
        List<Projet> projets = projetService.findAll();
        projetSpinnerAdapter = new ProjetSpinnerAdapter(this, android.R.layout.simple_spinner_item, projets);
        projetSpinnerAdapter.add(new Projet(null, " --SELECT A PROJECT-- "));
        projetSpinner.setAdapter(projetSpinnerAdapter);
        projetSpinnerAdapter.notifyDataSetChanged();
        projetSpinner.setSelection(projetSpinnerAdapter.getCount() + 1, true);
    }

//    private void updateSocieteSpinner() {
//        List<Societe> societes = societeService.findAll();
//        societeSpinnerAdapter = new SocieteSpinnerAdapter(this, android.R.layout.simple_spinner_item, societes);
//        societeSpinner.setAdapter(societeSpinnerAdapter);
//        societeSpinnerAdapter.notifyDataSetChanged();
//    }
//
//    private void updateProjetSpinner() {
//        List<Projet> projets = projetService.findAll();
//        projetSpinnerAdapter = new ProjetSpinnerAdapter(this, android.R.layout.simple_spinner_item, projets);
//        projetSpinner.setAdapter(projetSpinnerAdapter);
//        projetSpinnerAdapter.notifyDataSetChanged();
//    }

    private void updateDate() {
        editDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    private void initPopupDate() {
        // set calendar date and update editDate
        editDate = findViewById(R.id.date_depense);
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
        editDate = findViewById(R.id.date_depense);
        editDate.setText(dateString);
        initPopupDate();
    }

    private void initPopupDepenseTypeCreate() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.depense_type_create_popup, null);

        final EditText depenseTypeNom = mView.findViewById(R.id.depense_type_nom_textView);
        Button depenseTypeCreateBtn = mView.findViewById(R.id.depenseTypeCreateBtn);

        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();

        depenseTypeCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (depenseTypeNom.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),
                            "Vous n'avez pas saisie le nom du type de la depense",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    int res = depenseTypeService.create(String.valueOf(depenseTypeNom.getText()));
                    if (res == -1) {
                        Toast.makeText(getBaseContext(),
                                "Type de depense existe deja ",
                                Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(getBaseContext(),
                                "Type de depense a été crée",
                                Toast.LENGTH_SHORT)
                                .show();
                        updateDepenseTypeSpinner();
                        alertDialog.dismiss();
                    }
                }
            }
        });
        alertDialog.show();

    }

    private void updateDepenseTypeSpinner() {
        List<DepenseType> depenseTypes = depenseTypeService.findAll();
        depenseTypeSpinnerAdapter = new DepenseTypeSpinnerAdapter(this, android.R.layout.simple_spinner_item, depenseTypes);
        depenseTypeSpinner.setAdapter(depenseTypeSpinnerAdapter);
        depenseTypeSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_create);

        Session.setAttribute(context, "depenseContext");

        initSocieteSpinner();
        getSocieteFromSpinner();
        initProjetSpinner();
        getProjetFromSpinner();
        initDepenseTypeSpinner();
        getDepenseTypeFromSpinner();

        depenseTypeCreateBtn = findViewById(R.id.create_depense_type_btn);
        depenseTypeCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupDepenseTypeCreate();
            }
        });
//        injectParam();
        long currentdate = System.currentTimeMillis();
        String dateString = simpleDateFormat.format(currentdate);
        editDate = findViewById(R.id.date_depense);
//        editDate.setText(dateString);
        initPopupDate();
        initDate();
        initHeurePicker();
    }

    private void initHeurePicker() {
        heurDepense = findViewById(R.id.heur_depense);
        heurDepense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heurDepense.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });
    }

    private Depense setParam() {
        Depense depense = new Depense();
        montantDepense = findViewById(R.id.montant);


        BigDecimal montantBigDecimal;
        String montantString = String.valueOf("" + montantDepense.getText());
        if (montantString.isEmpty()) {
            montantBigDecimal = BigDecimal.ZERO;
        } else {
            montantBigDecimal = new BigDecimal(montantString);
        }
        depense.setMontant(montantBigDecimal);
        depense.setHeur(heurDepense.getText() + "");
        depense.setSociete(societe);
        depense.setProjet(projet);
        depense.setDepenseType(depenseType);

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
        if (depense.getDepenseType().getId() == null) {
            error = findViewById(R.id.error_depense);
            error.setText("Vous Devez choisir un type de depense");
            error.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            if (depense.getProjet().getId() == null && depense.getSociete().getId() == null) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DepenseCreateActivity.this);
                alert.setTitle("Info");
                alert.setMessage("If you don't choose neither a project nor a company, the expense will be affected as personal, do you confirm ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (depense.getHeur().isEmpty()) {
                            depense.setHeur("--:--");
                            depenseService.ajouterDepense(depense);
                            Dispacher.forward(DepenseCreateActivity.this, DepenseListActivity.class);
                            finish();
                        } else {
                            depenseService.ajouterDepense(depense);
                            Dispacher.forward(DepenseCreateActivity.this, DepenseListActivity.class);
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

            } else if (depense.getSociete().getId() != null && depense.getProjet().getId() != null) {
                error = findViewById(R.id.error_depense);
                error.setText(R.string.error_depense);
            } else {
                if (depense.getHeur().isEmpty()) {
                    depense.setHeur("--:--");
                }
                depenseService.ajouterDepense(depense);
                Dispacher.forward(DepenseCreateActivity.this, DepenseListActivity.class);
                finish();
            }
        }
    }


    private void getSocieteFromSpinner() {
        societeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                societe = societeSpinnerAdapter.getItem(position);
                if (societe != null && societe.getId() == null) {
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
                if (projet != null && projet.getId() == null) {
                    projet = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getDepenseTypeFromSpinner() {
        depenseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depenseType = depenseTypeSpinnerAdapter.getItem(position);
                if (depenseType != null && depenseType.getId() == null) {
                    depenseType = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
