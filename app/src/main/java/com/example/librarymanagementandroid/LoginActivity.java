package com.example.librarymanagementandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout InputEmail;
    private TextInputLayout InputPassword;
    private TextInputEditText Email;
    private TextInputEditText Password;
    private TextView Type;
    private Button Login;
    private int setPtype;
    private String EmailText,PasswordText;
    private SharedPreferences pref;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setPtype = 1;

        InputEmail = (TextInputLayout)findViewById(R.id.layout_email);
        InputPassword = (TextInputLayout)findViewById(R.id.layout_password);

        Email = (TextInputEditText)findViewById(R.id.email);
        Password = (TextInputEditText)findViewById(R.id.password);

        Type = (TextView)findViewById(R.id.type);
        Login = (Button)findViewById(R.id.btn_login);

        Type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword();
            }
        });

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View e) {
                validateForm();
            }
        });
    }

    public void showPassword(){
        if(setPtype == 1) {
            setPtype = 0;
            Password.setTransformationMethod(null);
            if (Password.getText().length() > 0) {
                Password.setSelection(Password.getText().length());
                Type.setBackgroundResource(R.drawable.visible_eye);
            }
        }
        else{
            setPtype = 1;
            Password.setTransformationMethod(new PasswordTransformationMethod());
            if (Password.getText().length()>0){
                Password.setSelection(Password.getText().length());
                Type.setBackgroundResource(R.drawable.eye_blue);
            }
        }

    }

    public boolean validateEmail(){
        String EmailInput = Email.getText().toString().trim();
        if (EmailInput.isEmpty()){
            InputEmail.setError("Obligatoire");
            return false;
        }

        else if (!isEmailValidate(Email)){
            InputEmail.setError("Email non valide");
            return false;
        }

        else {
            InputEmail.setError(null);
            return true;
        }
    }

    public boolean isEmailValidate(EditText Chaine){
        CharSequence email = Chaine.getText().toString();
        return (!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean validatePassword(){
        String PasswordInput = Password.getText().toString().trim();
        if (PasswordInput.isEmpty()){
            InputPassword.setError("Obligatoire");
            return false;
        }

        else {
            InputPassword.setError(null);
            return true;
        }
    }

    public void validateForm() {

        if ((!validateEmail())|(!validatePassword())){
            return;
        }
        else {
            loginAdmin();
        }
    }

    public void loginAdmin(){
        EmailText = Email.getText().toString().trim();
        PasswordText = Password.getText().toString().trim();
        if((EmailText.equals("admin@admin.com")) && (PasswordText.equals("admin"))){
            connectToAccount();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Désolé : Votre Email ou Password est incorrect.");
            builder.setTitle("Login");
            builder.setIcon(R.drawable.error_icon);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void connectToAccount(){
        Login.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Connexion...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        succesToLogin();
                        progressDialog.dismiss();
                    }
                }, 4000);
    }

    public void succesToLogin(){
        EmailText = Email.getText().toString().trim();
        PasswordText = Password.getText().toString().trim();
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username",EmailText);
        editor.putString("password",PasswordText);
        editor.commit();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
}