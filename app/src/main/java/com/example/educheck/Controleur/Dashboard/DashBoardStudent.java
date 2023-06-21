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

    private String token;
    private Toolbar toolbar;
    private ImageView menu1, menu2, menu3, menu4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_student);
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.menu3);
        menu4 = findViewById(R.id.menu4);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        token = getIntent().getStringExtra("token");


        menu1.setOnClickListener(v -> imageMenuAllBlackExcept("menu1"));

        menu2.setOnClickListener(v -> imageMenuAllBlackExcept("menu2"));

        menu3.setOnClickListener(v -> imageMenuAllBlackExcept("menu3"));

        menu4.setOnClickListener(v -> imageMenuAllBlackExcept("menu4"));
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

    public void imageMenuAllBlackExcept(String menu){
        switch(menu){
            case "menu1":
                this.menu1.setImageResource(R.drawable.ic_schedule_selected);
                this.menu2.setImageResource(R.drawable.ic_addcourse);
                this.menu3.setImageResource(R.drawable.ic_messenger);
                this.menu4.setImageResource(R.drawable.ic_present);
                replaceFragment(new Schedule());
                break;
            case "menu2":
                this.menu1.setImageResource(R.drawable.ic_schedule);
                this.menu2.setImageResource(R.drawable.ic_addcourse_selected);
                this.menu3.setImageResource(R.drawable.ic_messenger);
                this.menu4.setImageResource(R.drawable.ic_present);
                replaceFragment(new FragGetCourses());
                break;
            case "menu3":
                this.menu1.setImageResource(R.drawable.ic_schedule);
                this.menu2.setImageResource(R.drawable.ic_addcourse);
                this.menu3.setImageResource(R.drawable.ic_messenger_selected);
                this.menu4.setImageResource(R.drawable.ic_present);
                replaceFragment(new FragMessages1());
                break;
            case "menu4":
                this.menu1.setImageResource(R.drawable.ic_schedule);
                this.menu2.setImageResource(R.drawable.ic_addcourse);
                this.menu3.setImageResource(R.drawable.ic_messenger);
                this.menu4.setImageResource(R.drawable.ic_present_selected);
                replaceFragment(new FragJustification1());
                break;
        }
    }
}