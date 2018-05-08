package com.sharpinfo.sir.gestionprojet_v2.action.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;

import service.UserService;

public class SignUpActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    UserService userService = new UserService(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signUp(View view) {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        userService.createUser(String.valueOf(username.getText()), String.valueOf(password.getText()));
        Toast.makeText(getBaseContext(), "User has been created successfully!", Toast.LENGTH_LONG).show();
        //Dispacher.forward(SignUpActivity.this, MainActivity.class);
    }
}
