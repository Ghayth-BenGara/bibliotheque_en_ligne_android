package com.example.librarymanagementandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToogle;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private ImageView deconnexionBouton,sesame,listeUtilisateurs;
    private SharedPreferences pref;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        View header = navigationView.getHeaderView(0);
        mToolbar = (Toolbar) findViewById(R.id.navbar_action);
        setSupportActionBar(mToolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToogle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        email = pref.getString("username",null);

        deconnexionBouton = (ImageView) findViewById(R.id.deconnexion_bouton);
        deconnexionBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDeconnexion();
            }
        });

        sesame = (ImageView) findViewById(R.id.sesame);
        sesame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirProfilSesame();
            }
        });

        listeUtilisateurs = (ImageView) findViewById(R.id.users);
        listeUtilisateurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargementListeUtilisateurs();
            }
        });

    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("Désolé : vous ne pouvez pas retourner à la page précédente.");
        builder.setTitle("Retour");
        builder.setIcon(R.drawable.error_icon);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void confirmationDeconnexion(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
        builder1.setTitle("Déconnexion");
        builder1.setIcon(R.drawable.deconnexion_icon);
        builder1.setMessage("Fermer votre session ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Fermer",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deconnexionAdmin();
                    }
                });

        builder1.setNegativeButton(
                "Annuler",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public void deconnexionAdmin(){
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Déconnexion...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(intent);
                        overridePendingTransition(R.anim.godown, R.anim.goup);
                        progressDialog.dismiss();
                    }
                }, 4000);
    }

    public void ouvrirProfilSesame(){
        Intent intent = new Intent(HomeActivity.this,ProfilActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void ouvrirAideApplication(){
        Intent intent = new Intent(HomeActivity.this,HelpActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void chargementListeUtilisateurs(){
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Chargement des utilisateurs...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        ouvrirListeUtilisateurs();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void ouvrirListeUtilisateurs(){
        Intent intent = new Intent(HomeActivity.this,ListeCompteActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.deconnexion_menu){
            confirmationDeconnexion();
        }
        else if (id == R.id.sesame_menu){
            ouvrirProfilSesame();
        }
        else if (id == R.id.aide_menu){
            ouvrirAideApplication();
        }
        else if(id == R.id.users_menu){
            chargementListeUtilisateurs();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToogle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}