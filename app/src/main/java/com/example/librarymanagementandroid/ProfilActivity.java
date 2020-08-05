package com.example.librarymanagementandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

public class ProfilActivity extends AppCompatActivity {
    private ImageView retourBouton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        retourBouton = (ImageView) findViewById(R.id.retour_bouton);
        retourBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retourVersDashboard();
            }
        });
    }

    public void retourVersDashboard(){
        Intent intent = new Intent(ProfilActivity.this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.godown, R.anim.goup);
    }
}