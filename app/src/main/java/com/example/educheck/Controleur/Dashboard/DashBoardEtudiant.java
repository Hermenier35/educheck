package com.example.educheck.Controleur.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.TextView;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.educheck.Modele.Student;
import com.example.educheck.R;

public class DashBoardEtudiant extends AppCompatActivity {
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

        Student student = new Student("yasser","el mellali","yelmellali@gmail.com","1234567899","student");
        String name = student.getFirstName();
        textView = findViewById(R.id.welcome);
        String myString = "Hello ";
        textView.setText(myString + name);

        btn1 = findViewById(R.id.menu1);
        btn2 = findViewById(R.id.menu2);
        btn3 = findViewById(R.id.menu3);
        btn4 = findViewById(R.id.menu4);

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