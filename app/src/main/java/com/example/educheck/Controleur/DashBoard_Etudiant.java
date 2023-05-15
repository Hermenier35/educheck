package com.example.educheck.Controleur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.educheck.R;

public class DashBoard_Etudiant extends AppCompatActivity {
    Button main_button_Course;
    Button main_button_Planning;
    Button main_button_Messages;

    Intent click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_etudiant);

        main_button_Course = findViewById(R.id.main_button_Course);
        main_button_Planning = findViewById(R.id.main_button_Planning);
        main_button_Messages = findViewById(R.id.main_button_messages);

        main_button_Course.setOnClickListener(v->{clickCourse();});
        main_button_Messages.setOnClickListener(v->{clickMsg();});


    }

    void clickCourse(){
        click = new Intent(this,Courses.class);
        startActivity(click);
    }
    void clickMsg (){
        click = new Intent(this,Messages.class);
                startActivity(click);
    }
}