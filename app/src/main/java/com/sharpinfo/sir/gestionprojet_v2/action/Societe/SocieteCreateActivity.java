package com.sharpinfo.sir.gestionprojet_v2.action.Societe;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import service.SocieteService;

public class SocieteCreateActivity extends AppCompatActivity {

    private EditText raisonSociale;
    SocieteService societeService = new SocieteService(this);

    //Date
    Context context = this;
    EditText editDate;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd-MM-yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_societe_create);
        editDate = (EditText) findViewById(R.id.textViewDate);

        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = simpleDateFormat.format(currentdate);
        editDate.setText(dateString);

        // set calendar date and update editDate
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

    private void updateDate() {
        editDate.setText(simpleDateFormat.format(myCalendar.getTime()));

    }


    public void create(View view) {
        Date dateFondation = new Date();
        Log.d("tag8", "1");
        try {
            //hadi hya la date li khsha tmchi l sqllite
            Log.d("tag2",""+myCalendar.getTime());
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
