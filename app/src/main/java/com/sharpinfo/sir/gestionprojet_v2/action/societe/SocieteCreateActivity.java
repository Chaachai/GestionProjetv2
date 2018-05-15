package com.sharpinfo.sir.gestionprojet_v2.action.societe;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.adapter.ManagerSpinnerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Manager;
import bean.Societe;
import helper.Dispacher;
import service.ManagerService;
import service.SocieteService;

public class SocieteCreateActivity extends AppCompatActivity {

    //tag for log
    private static final String TAG = "SocieteCreate";

    private EditText raisonSociale;
    private Spinner managerSpinner;
    private Button managerCreateBtn;


    private ManagerSpinnerAdapter managerSpinnerAdapter;

    SocieteService societeService = new SocieteService(this);
    ManagerService managerService = new ManagerService(this);

    private Manager manager = null;
    //Date
    Context context = this;
    EditText editDate;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd-MM-yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());


    private void initManagerSpinner() {
        managerSpinner = (Spinner) findViewById(R.id.manager_spinner);
//        List<String> managerNames = new ArrayList();
        List<Manager> managers = managerService.findAll();
//
//        if (managers.isEmpty()) {
//            managerNames.add("There is no manager");
//        } else {
//            managerNames.add("Please select a manager");
//            for (int i = 0; i < managers.size(); i++) {
//                managerNames.add("Nom et Prenom :" + managers.get(i).getNom() + " " + managers.get(i).getPrenom());
//            }
//        }
        managerSpinnerAdapter = new ManagerSpinnerAdapter(this, android.R.layout.simple_spinner_item, managers);
        managerSpinner.setAdapter(managerSpinnerAdapter);
        managerSpinnerAdapter.notifyDataSetChanged();
    }

    private void updateManageSpinner() {
        List<Manager> managers = managerService.findAll();
        managerSpinnerAdapter = new ManagerSpinnerAdapter(this, android.R.layout.simple_spinner_item, managers);
        managerSpinner.setAdapter(managerSpinnerAdapter);
        managerSpinnerAdapter.notifyDataSetChanged();
    }

    private void initPopupCreateManager() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.manager_create_popup, null);

        final EditText managerFirstNameText = (EditText) mView.findViewById(R.id.manager_firstname_textView);
        final EditText managerLastNameText = (EditText) mView.findViewById(R.id.manager_lastname_textView);
        Button managerCreatebtn = (Button) mView.findViewById(R.id.managerCreatebtn);

        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();

        managerCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = 0;
                if (managerFirstNameText.getText().toString().isEmpty() && managerLastNameText.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),
                            R.string.manager_creation_fail,
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Log.d(TAG, String.valueOf(managerFirstNameText.getText()));
                    Log.d(TAG, String.valueOf(managerLastNameText.getText()));
                    res = managerService.create(String.valueOf(managerFirstNameText.getText()), String.valueOf(managerLastNameText.getText()));
                    Log.d("manager create", "result " + res);
                    Log.d("manager create", "error");
                    Toast.makeText(getBaseContext(),
                            R.string.manager_creation_success,
                            Toast.LENGTH_SHORT)
                            .show();
                    updateManageSpinner();
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.show();

    }

    private void updateDate() {
        editDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    private void initPopupDate() {
        // set calendar date and update editDate
        editDate = (EditText) findViewById(R.id.textViewDate);
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
        editDate = (EditText) findViewById(R.id.textViewDate);
        editDate.setText(dateString);
        initPopupDate();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_societe_create);
        initManagerSpinner();

        getManagerFromSpinner();
        managerCreateBtn = (Button) findViewById(R.id.create_manager_btn);
        managerCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupCreateManager();
            }
        });

        // init - set date to current date
        initDate();

    }


    public void create(View view) {

        Societe societe = setParam();
        societeService.create(societe);
        Toast.makeText(getBaseContext(), "Societe cree avec succes! with date " + societe.getDateFondation() + " " + societe.getManager().getNom() + ", !", Toast.LENGTH_LONG).show();
        Dispacher.forward(this, SocieteListActivity.class);
        finish();

    }

    private Societe setParam() {
        raisonSociale = findViewById(R.id.textViewRaisonSociale);
        Societe societe = new Societe();
        societe.setRaisonSociale(String.valueOf(raisonSociale.getText()));
        societe.setManager(manager);

        Date dateFondation = new Date();

        try {
            //hadi hya la date li khsha tmchi l sqllite
            dateFondation = simpleDateFormat.parse(simpleDateFormat.format(myCalendar.getTime()));
            societe.setDateFondation(dateFondation);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return societe;

    }

    private Manager getManagerFromSpinner() {

        managerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manager = managerSpinnerAdapter.getItem(position);
                Log.d("test", "no error");
                Log.d(TAG, "2");
                Log.d(TAG, manager.getNom() + " 2" + manager.getPrenom());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return manager;
    }

}
