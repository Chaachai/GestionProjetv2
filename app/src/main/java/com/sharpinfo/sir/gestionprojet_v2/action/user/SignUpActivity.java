package com.sharpinfo.sir.gestionprojet_v2.action.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;

import bean.User;
import helper.Dispacher;
import service.UserService;

public class SignUpActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText lastName;
    private EditText firstName;
    UserService userService = new UserService(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        injectParam();
    }

    private void injectParam() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        lastName = findViewById(R.id.lastname);
        firstName = findViewById(R.id.firstname);
    }

    private User getParam() {
        User user = new User(String.valueOf(username.getText()), String.valueOf(password.getText()), String.valueOf(lastName.getText()), String.valueOf(firstName.getText()));
        return user;
    }

    public void signUp(View view) {
        User user = getParam();
        userService.createUser(user);
        Toast.makeText(getBaseContext(), R.string.user_created, Toast.LENGTH_LONG).show();
        Dispacher.forward(SignUpActivity.this, SignInActivity.class);
    }
}
