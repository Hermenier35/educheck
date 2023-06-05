package com.example.educheck.Controleur.DashbardTeacher;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;

import com.example.educheck.Controleur.Dashboard.FragMessages2;
import com.example.educheck.R;

public class DashBoardTeacher extends AppCompatActivity {
    Button btn1, btn2,btn3;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_prof);

        btn1 = findViewById(R.id.tmenu1);
        btn2 = findViewById(R.id.tmenu2);
        btn3 = findViewById(R.id.tmenu3);

        token = getIntent().getStringExtra("token");

        btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        replaceFragment(new FragAddStudent());
                                    }
                                }
        );

        btn2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        replaceFragment(new FragMessages2());
                                    }
                                }
        );

        btn3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        replaceFragment(new FragConfirmationStudent());
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