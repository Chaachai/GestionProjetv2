package com.sharpinfo.sir.gestionprojet_v2.action.menu;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;

import bean.User;
import helper.Dispacher;
import helper.Session;
import service.UserService;

public class EditProfileActivity extends AppCompatActivity {
    UserService userService = new UserService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        final EditText nom = findViewById(R.id.edit_profile_nom);
        final EditText prenom = findViewById(R.id.edit_profile_prenom);
        User user = (User) Session.getAttribut("connectedUser");
        nom.setText(user.getLastName());
        prenom.setText(user.getFirstName());
        Button save = findViewById(R.id.edit_profile_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userService.editProfile(nom.getText() + "", prenom.getText() + "");
                Toast.makeText(getBaseContext(), "Profile edited successfully !", Toast.LENGTH_LONG).show();
                Dispacher.forward(EditProfileActivity.this, SideMenuActivity.class);
            }
        });
    }
}
