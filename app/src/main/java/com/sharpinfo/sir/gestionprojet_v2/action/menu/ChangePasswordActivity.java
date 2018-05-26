package com.sharpinfo.sir.gestionprojet_v2.action.menu;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;

import helper.Dispacher;
import service.UserService;

public class ChangePasswordActivity extends AppCompatActivity {
    UserService userService = new UserService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Button changePassword = findViewById(R.id.save_change_password);
        final EditText oldPassword = findViewById(R.id.old_password);
        final EditText newPassword = findViewById(R.id.new_password);
        final EditText confirmPassword = findViewById(R.id.confirm_new_password);
        final TextView error = findViewById(R.id.change_password_error);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = userService.changePassword(oldPassword.getText() + "", newPassword.getText() + "", confirmPassword.getText() + "");
                if (res == -1) {
                    error.setText("Incorrect password !");
                    oldPassword.setText(null);
                    newPassword.setText(null);
                    confirmPassword.setText(null);
                } else if (res == -2) {
                    error.setText("The two passwords are not equal !");
                    newPassword.setText(null);
                    confirmPassword.setText(null);
                } else {
                    Toast.makeText(getBaseContext(), "Password changed successfully", Toast.LENGTH_LONG).show();
                    Dispacher.forward(ChangePasswordActivity.this, SideMenuActivity.class);
                    finish();
                }
            }
        });
    }
}
