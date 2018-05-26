package com.sharpinfo.sir.gestionprojet_v2.action.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.MainActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.menu.ChangePasswordActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.menu.MenuActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.menu.SideMenuActivity;

import bean.User;
import helper.Dispacher;
import helper.Session;
import service.UserService;

public class SignInActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView textMsg;
    UserService userService = new UserService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Button signUp;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        int rec = userService.countUser();
        Log.d("tag", "The number of records in user table is : " + rec);
        if (rec == 0) {
            userService.createDefaultUser();
        } else {
            Log.d("tag", "Welcome back !");
        }
        injectParam();
//        signUp = (Button) findViewById(R.id.signup);
//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dispacher.forward(SignInActivity.this, SignUpActivity.class);
//            }
//        });
    }

    private void injectParam() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        textMsg = findViewById(R.id.errorMsg);
    }

    private User getParam() {
        User user = new User();
        user.setId(String.valueOf(username.getText()));
        user.setPassword(String.valueOf(password.getText()));
        return user;
    }

    private void clear() {
        username.setText(null);
        password.setText(null);
    }

    public void signIn(View view) {
        User user = getParam();
        int res = userService.connect(user);
        if (res < 1) {
            textMsg.setText(R.string.username_or_password_incorrect);
            clear();
        } else {
            User u = userService.find(user.getId());
            Session.setAttribute(u, "connectedUser");
            u.setNbrConnection(u.getNbrConnection() + 1);
            userService.edit(u);
            Toast.makeText(getBaseContext(), "WELCOME BACK " + u.getLastName() + " " + u.getFirstName(), Toast.LENGTH_LONG).show();
            Dispacher.forward(SignInActivity.this, SideMenuActivity.class);
            finish();
        }

    }

}
