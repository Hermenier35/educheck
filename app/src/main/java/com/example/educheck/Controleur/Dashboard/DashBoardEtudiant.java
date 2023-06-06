package com.example.educheck.Controleur.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.educheck.Modele.Student;
import com.example.educheck.R;

public class DashBoardEtudiant extends AppCompatActivity {

    private String token;
    //TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_etudiant);
        //textView = findViewById(R.id.welcome);
        ImageView menu1 = findViewById(R.id.menu1);
        ImageView menu2 = findViewById(R.id.menu2);
        ImageView menu3 = findViewById(R.id.menu3);
        ImageView menu4 = findViewById(R.id.menu4);

        token = getIntent().getStringExtra("token");


        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Schedule());
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragGetCourses());
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragMessages1());
            }
        });

        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Frag4());
            }
        });
    }

    private void replaceFragment(Fragment fragment){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame1,fragment);
            fragmentTransaction.commit();
    }
}