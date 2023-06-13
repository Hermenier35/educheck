package com.example.educheck.Controleur.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.ImageView;


import android.os.Bundle;

import com.example.educheck.R;

public class DashBoardStudent extends AppCompatActivity {

    String token;
    private Toolbar toolbar;
    //TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_student);
        ImageView menu1 = findViewById(R.id.menu1);
        ImageView menu2 = findViewById(R.id.menu2);
        ImageView menu3 = findViewById(R.id.menu3);
        ImageView menu4 = findViewById(R.id.menu4);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        token = getIntent().getStringExtra("token");


        menu1.setOnClickListener(v -> replaceFragment(new Schedule()));


        menu2.setOnClickListener(v -> replaceFragment(new FragGetCourses()));

        /*
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the marks data to FragMarks
                List<Integer> marksList = new ArrayList<>();
                marksList.add(18);
                marksList.add(19);
                marksList.add(10);
                FragMarks fragMarks = FragMarks.newInstance("Course Name", marksList);

                replaceFragment(fragMarks);
            }
        });

         */
        menu3.setOnClickListener(v -> replaceFragment(new FragMessages1()));

        menu4.setOnClickListener(v -> replaceFragment(new FragJustification1()));
        replaceFragment(new Schedule());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void replaceFragment(Fragment fragment){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame1,fragment);
            fragmentTransaction.commit();
    }
}