package com.example.librarymanagementandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 4500;
    ImageView imageView,imageView2;
    TextView textView1,textView2;
    Animation top,bottom;
    private SharedPreferences pref;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        imageView = findViewById(R.id.image_view);
        imageView2 = findViewById(R.id.image_view_2);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        top = AnimationUtils.loadAnimation(this,R.anim.top);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom);

        imageView.setAnimation(top);
        textView1.setAnimation(bottom);
        textView2.setAnimation(bottom);
        imageView2.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                //startActivity(intent);
                pref = getSharedPreferences("user_details",MODE_PRIVATE);
                email = pref.getString("username",null);
                testExistSession(email);
                finish();
            }
        },SPLASH_SCREEN);
    }

    public void testExistSession(String email){
        if(email == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}