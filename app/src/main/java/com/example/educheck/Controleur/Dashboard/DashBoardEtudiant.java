package com.example.educheck.Controleur.Dashboard;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.TextView;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Student;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;

public class DashBoardEtudiant extends AppCompatActivity implements AsyncTaskcallback {
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    TextView textView;
    Intent click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_etudiant);
        String name= getIntent().getStringExtra("mail");
        btn1 = findViewById(R.id.menu1);
        btn2 = findViewById(R.id.menu2);
        btn3 = findViewById(R.id.menu3);
        btn4 = findViewById(R.id.menu4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Schedule());
            }
        }
        );

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Frag_GetCours());
            }
        }
        );

        btn3.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                    public void onClick(View v) {
                                        replaceFragment(new FragMessages1());
                                    }
                                }
        );

        btn4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        replaceFragment(new Frag4());
                                    }
                                }
        );
    }

    private void replaceFragment(Fragment fragment){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame1,fragment);
            fragmentTransaction.commit();
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {

    }
}

    /**
        btn1.setOnClickListener(v->{clickCourse();});
        btn2.setOnClickListener(v->{clickMsg();});
    }

    void clickCourse(){
        click = new Intent(DashBoard_Etudiant.this, Courses.class);
        startActivity(click);
    }
    void clickMsg (){
        click = new Intent(DashBoard_Etudiant.this, Messages.class);
                startActivity(click);
    }
     */