package com.example.educheck.Controleur.Dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.educheck.Modele.Student;
import com.example.educheck.R;

public class DashBoard_Teacher extends AppCompatActivity {
    Button btn1, btn2,btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_prof);

        btn1 = findViewById(R.id.tmenu1);
        btn2 = findViewById(R.id.tmenu2);
        btn3 = findViewById(R.id.tmenu3);

        btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        replaceFragment(new Frag1());
                                    }
                                }
        );

        btn2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        replaceFragment(new Frag2());
                                    }
                                }
        );

        btn3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        replaceFragment(new Frag3());
                                    }
                                }
        );


    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameTeacher,fragment);
        fragmentTransaction.commit();
    }
}