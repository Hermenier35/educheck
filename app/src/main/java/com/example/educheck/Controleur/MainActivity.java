package com.example.educheck.Controleur;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.educheck.R;

public class MainActivity extends AppCompatActivity {
    Intent login_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_activity = new  Intent(getApplicationContext(),login.class);
        Button login = findViewById(R.id.button);
        login.setOnClickListener(v -> startActivity(login_activity));

    }

}