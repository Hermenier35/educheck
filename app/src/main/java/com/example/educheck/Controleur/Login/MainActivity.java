package com.example.educheck.Controleur.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.educheck.Controleur.DashboardAdmin.DashboardAdmin;
import com.example.educheck.Controleur.Registration.UniversityInscription;
import com.example.educheck.R;

public class MainActivity extends AppCompatActivity {
    Intent login_activity;
    Intent registration_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_activity = new  Intent(getApplicationContext(), Login.class);
        registration_activity = new Intent(getApplicationContext(), UniversityInscription.class);
        Button login = findViewById(R.id.sign_in);
        Button sign_up = findViewById(R.id.sign_up);
        login.setOnClickListener(v -> startActivity(login_activity));
        sign_up.setOnClickListener(v -> startActivity(registration_activity));
    }

}