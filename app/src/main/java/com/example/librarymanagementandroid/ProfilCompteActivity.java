package com.example.librarymanagementandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfilCompteActivity extends AppCompatActivity {
    private ImageView retourBouton;
    private TextView nomCompte;
    private TextView prenomCompte;
    private TextView emailCompte;
    private TextView genreCompte;
    private TextView dateNaissanceCompte;
    private TextView cinCompte;
    private TextView mobileCompte;
    private TextView dateCreationCompte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_compte);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        retourBouton = (ImageView) findViewById(R.id.retour_bouton);
        retourBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retourListeCompte();
            }
        });

        nomCompte = (TextView) findViewById(R.id.nom);
        prenomCompte = (TextView) findViewById(R.id.prenom);
        emailCompte = (TextView) findViewById(R.id.email);
        genreCompte = (TextView) findViewById(R.id.genre);
        dateNaissanceCompte = (TextView) findViewById(R.id.date_naissance);
        cinCompte = (TextView) findViewById(R.id.cin);
        mobileCompte = (TextView) findViewById(R.id.mobile);
        dateCreationCompte = (TextView) findViewById(R.id.date_creation);
        loadInformations();
    }

    public void retourListeCompte(){
        Intent intent = new Intent(ProfilCompteActivity.this,ListeCompteActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.godown, R.anim.goup);
    }

    public void loadInformations(){
        Url adresse_ip = new Url();
        String adresse = adresse_ip.getUrl_ip();
        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        String url_ip = adresse + "/bibliotheque/php/api_android/information_utilisateur_api.php?email="+email;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_ip, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i<array.length(); i++){
                        JSONObject user = array.getJSONObject(i);

                        String nom = user.getString("nom");
                        String prenom = user.getString("prenom");
                        String genre = user.getString("genre");
                        String dateNaisance = user.getString("date_naissance");
                        String cin = user.getString("cin");
                        String mobile = user.getString("mobile");
                        String dateCreation = user.getString("date_creation");

                        nomCompte.setText(nom);
                        prenomCompte.setText(prenom);
                        genreCompte.setText(genre);
                        dateNaissanceCompte.setText(dateNaisance);
                        cinCompte.setText(cin);
                        mobileCompte.setText(mobile);
                        dateCreationCompte.setText(dateCreation);
                        emailCompte.setText(email);

                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilCompteActivity.this);
                builder.setMessage("Désolé : les informations sont actuellement indisponible.");
                builder.setTitle("Information du compte");
                builder.setIcon(R.drawable.error_icon);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

}