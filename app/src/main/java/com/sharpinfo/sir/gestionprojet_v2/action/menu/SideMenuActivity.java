package com.sharpinfo.sir.gestionprojet_v2.action.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sharpinfo.sir.gestionprojet_v2.R;
import com.sharpinfo.sir.gestionprojet_v2.action.HamzaTestChart2Activity;
import com.sharpinfo.sir.gestionprojet_v2.action.dashboard.DashboardActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.projet.ProjetListActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.societe.SocieteListActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.depense.DepenseListActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.statistics.StatisticsMenuActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.tache.TacheListActivity;
import com.sharpinfo.sir.gestionprojet_v2.action.user.SignInActivity;

import bean.User;
import helper.Dispacher;
import helper.Session;
import service.ProjetService;
import service.UserService;

public class SideMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProjetService projetService = new ProjetService(this);
    UserService userService = new UserService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//
//        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        User user = (User) Session.getAttribut("connectedUser");
        Log.d("tag", "Nbr Connection ========= " + user.getNbrConnection());
        if (user.getNbrConnection() == 1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(SideMenuActivity.this);
            alert.setTitle("Change Password");
            alert.setMessage("Welcome to Expert Projects, it seems that this is your first time here, therefore, we recommend that you change your password.");
            alert.setPositiveButton("Later", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alert.setNegativeButton("Change Password Now", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Dispacher.forward(SideMenuActivity.this, ChangePasswordActivity.class);
                    dialog.dismiss();
                }
            });
            user.setNbrConnection(2);
            userService.edit(user);
            alert.show();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_first_layout) {
//            fragmentManager.beginTransaction().replace(R.id.content_frame, new FirstFragment()).commit();
            Dispacher.forward(SideMenuActivity.this, EditProfileActivity.class);
            // Handle the camera action
        } else if (id == R.id.nav_second_layout) {
//            fragmentManager.beginTransaction().replace(R.id.content_frame, new SecondFragment()).commit();
            Dispacher.forward(SideMenuActivity.this, ChangePasswordActivity.class);
        } else if (id == R.id.nav_third_layout) {
//            fragmentManager.beginTransaction().replace(R.id.content_frame, new ThirdFragment()).commit();
            Dispacher.forward(SideMenuActivity.this, AboutActivity.class);
        } else if (id == R.id.nav_share) {
            Dispacher.forward(SideMenuActivity.this, SignInActivity.class);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void testStatistics(View view) {
        Dispacher.forward(SideMenuActivity.this, StatisticsMenuActivity.class);
    }

    public void manageCompany(View view) {
        Dispacher.forward(SideMenuActivity.this, SocieteListActivity.class);
    }

    public void manageExpenses(View view) {
        Dispacher.forward(SideMenuActivity.this, DepenseListActivity.class);
    }

    public void manageProjects(View view) {
        Dispacher.forward(SideMenuActivity.this, ProjetListActivity.class);
    }

    public void manageTasks(View view) {
        Dispacher.forward(SideMenuActivity.this, TacheListActivity.class);
    }

    public void seeDashboard(View view) {
        Dispacher.forward(SideMenuActivity.this, DashboardActivity.class);
    }

}
