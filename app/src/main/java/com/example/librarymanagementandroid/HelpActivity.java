package com.example.librarymanagementandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {
    private OnBoaringAdapter onBoaringAdapter;
    private ViewPager2 viewPager2;
    private LinearLayout layoutOnBoringIndicators;
    private Button buttonOnBoaringAction;
    private ImageView retourButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        buttonOnBoaringAction = (Button) findViewById(R.id.button_boarding_action);
        layoutOnBoringIndicators = (LinearLayout) findViewById(R.id.layout_boaring_indicators);
        setUpOnBoringItems();

        viewPager2 = (ViewPager2) findViewById(R.id.boaring_view_pager);
        viewPager2.setAdapter(onBoaringAdapter);

        setUpOnboaringIndicators();
        setCurrentOnBoaringIndicator(0);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnBoaringIndicator(position);
            }
        });

        buttonOnBoaringAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager2.getCurrentItem() + 1 < onBoaringAdapter.getItemCount()){
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
                }
                else {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem()-1);
                }
            }
        });

        retourButton = (ImageView) findViewById(R.id.retour_bouton_help);
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retourVersDashboard();
            }
        });
    }

    public void setUpOnBoringItems(){
        List<OnBoaringItem> onBoringItems = new ArrayList<>();
        OnBoaringItem comptes = new OnBoaringItem();
        comptes.setTitle("Gestion des comptes");
        comptes.setDescription("Gérer la liste des utilisateurs inscrit sur l'application.");
        comptes.setImage(R.drawable.users);

        OnBoaringItem livres = new OnBoaringItem();
        livres.setTitle("Gestion des livres");
        livres.setDescription("Gérer la liste des livres trouvées sur l'application.");
        livres.setImage(R.drawable.livre);

        OnBoaringItem demandes = new OnBoaringItem();
        demandes.setTitle("Gestion des demandes");
        demandes.setDescription("Gérer les demandes de réservation envoyés par les utlisateurs.");
        demandes.setImage(R.drawable.demande);

        OnBoaringItem statistiques = new OnBoaringItem();
        statistiques.setTitle("Consultation des statistiques");
        statistiques.setDescription("Consulter les statistiques du l'utilisation de l'application fournit par le systéme.");
        statistiques.setImage(R.drawable.statistique);

        OnBoaringItem historique = new OnBoaringItem();
        historique.setTitle("Consultation de l'historique");
        historique.setDescription("Consulter l'historique de réservaton des livres.");
        historique.setImage(R.drawable.historique);

        OnBoaringItem profil = new OnBoaringItem();
        profil.setTitle("Gestion de profil");
        profil.setDescription("Gérer le profil et les informations de sesame.");
        profil.setImage(R.drawable.sesame);


        onBoringItems.add(livres);
        onBoringItems.add(demandes);
        onBoringItems.add(statistiques);
        onBoringItems.add(historique);
        onBoringItems.add(profil);

        onBoaringAdapter = new OnBoaringAdapter(onBoringItems);
    }

    public void setUpOnboaringIndicators(){
        ImageView[] indicators = new ImageView[onBoaringAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for(int i = 0; i<indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboargin_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnBoringIndicators.addView(indicators[i]);
        }
    }

    public void setCurrentOnBoaringIndicator(int index){
        int childCount = layoutOnBoringIndicators.getChildCount();
        for (int i = 0; i<childCount; i++){
            ImageView imageView = (ImageView) layoutOnBoringIndicators.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboaring_indicator_active)
                );
            }
            else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboargin_indicator_inactive)
                );
            }
        }
        if( index == onBoaringAdapter.getItemCount() - 1){
            buttonOnBoaringAction.setText("précédent");
        }
        else{
            buttonOnBoaringAction.setText("Suivant");
        }
    }

    public void retourVersDashboard(){
        Intent intent = new Intent(HelpActivity.this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.godown, R.anim.goup);
    }

}