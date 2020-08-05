package com.example.librarymanagementandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
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
import java.util.ArrayList;
import java.util.List;

public class ListeCompteActivity extends AppCompatActivity {
    private List<ItemListeUser> userList;
    private RecyclerView recyclerView;
    private ImageView retourButton;
    private TextView erreurText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_compte);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        loadUser();

        retourButton = (ImageView) findViewById(R.id.retour_bouton_compte);
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retourHome();
            }
        });

        erreurText = (TextView) findViewById(R.id.text_erreur);

    }

    public void loadUser(){
        Url adresse_ip = new Url();
        String adresse = adresse_ip.getUrl_ip();
        String url_ip = adresse + "/bibliotheque/php/api_android/liste_utilisateur_api.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_ip, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i<array.length(); i++){
                        JSONObject user = array.getJSONObject(i);

                        String email = user.getString("email");
                        String nom = user.getString("nom");
                        String prenom = user.getString("prenom");

                        userList.add(new ItemListeUser(
                                email,
                                prenom + " " + nom
                        ));

                        OnUserAdapter adapter = new OnUserAdapter(userList, getApplicationContext(), new ClickChoix(){
                            @Override
                            public void OnMyClick(View v, int position) {
                                String email = userList.get(position).getEmail();
                                chargementInformations(email);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                erreurText.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListeCompteActivity.this);
                builder.setMessage("Désolé : la liste est actuellement indisponible.");
                builder.setTitle("Liste des comptes");
                builder.setIcon(R.drawable.error_icon);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                erreurText.setText("Aucune compte trouvée.");
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void retourHome(){
        Intent intent = new Intent(ListeCompteActivity.this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.godown, R.anim.goup);
    }

    public void chargementInformations(final String email){
        final ProgressDialog progressDialog = new ProgressDialog(ListeCompteActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Chargement des informations...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        ouvrirProfilCompte(email);
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void ouvrirProfilCompte(String email){
        Intent intent = new Intent(ListeCompteActivity.this, ProfilCompteActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}