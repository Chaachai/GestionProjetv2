package com.sharpinfo.sir.gestionprojet_v2.action.Societe;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bean.Manager;
import helper.Dispacher;
import service.ManagerService;
import service.SocieteService;

public class SocieteCreateActivity extends AppCompatActivity {

    private EditText raisonSociale;
    private Spinner managerSpinner;
    private Button managerCreateBtn;
    SocieteService societeService = new SocieteService(this);
    ManagerService managerService = new ManagerService(this);

    //Date
    Context context = this;
    EditText editDate;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd-MM-yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());


    private void initManagerSpinner() {
        managerSpinner = (Spinner) findViewById(R.id.manager_spinner);
        List<String> managerNames = new ArrayList<>();
        List<Manager> managers = managerService.findAll();
        if (managers.isEmpty()) {
            managerNames.add("There is no manager");
        } else {
            for (int i = 0; i < managers.size(); i++) {
                managerNames.add(managers.get(i).getNom() + " " + managers.get(i).getPrenom());
            }
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, managerNames);
        managerSpinner.setAdapter(stringArrayAdapter);
    }

    private void initPopupCreateManager() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.manager_create_popup, null);

        final EditText managerFirstNameText = (EditText) mView.findViewById(R.id.manager_firstname_textView);
        final EditText managerLastNameText = (EditText) mView.findViewById(R.id.manager_lastname_textView);
        Button managerCreatebtn = (Button) mView.findViewById(R.id.managerCreatebtn);

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
                    Toast.makeText(getBaseContext(),
                            R.string.manager_creation_success,
                            Toast.LENGTH_SHORT)
                            .show();
                    Dispacher.forward(context,SocieteCreateActivity.class);

                }
            }
        });
        builder.setView(mView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void updateDate() {
        editDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
    private void initPopupDate(){
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_societe_create);
        initManagerSpinner();
        managerCreateBtn =(Button) findViewById(R.id.create_manager_btn);
        managerCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupCreateManager();
            }
        });
        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = simpleDateFormat.format(currentdate);
        editDate.setText(dateString);
        initPopupDate();
    }




    //create btn
    public void create(View view) {
        Date dateFondation = new Date();
        Log.d("tag8", "1");
        try {
            //hadi hya la date li khsha tmchi l sqllite
            Log.d("tag2", "" + myCalendar.getTime());
            dateFondation = simpleDateFormat.parse(simpleDateFormat.format(myCalendar.getTime()));
            raisonSociale = findViewById(R.id.textViewRaisonSociale);
            int res = societeService.create(String.valueOf(raisonSociale.getText()), dateFondation);
            if (res == 1) {
                Toast.makeText(getBaseContext(), "Societe cree avec succes! with date " + myCalendar.getTime() + ", !", Toast.LENGTH_LONG).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
