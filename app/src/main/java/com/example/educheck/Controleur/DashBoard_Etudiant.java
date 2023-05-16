package com.example.educheck.Controleur;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.educheck.Modele.Student;
import com.example.educheck.R;

public class DashBoard_Etudiant extends AppCompatActivity {
    Button main_button_Course;
    Button main_button_Planning;
    Button main_button_Messages;
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


        main_button_Course = findViewById(R.id.main_button_Course);
        main_button_Planning = findViewById(R.id.main_button_Planning);
        main_button_Messages = findViewById(R.id.main_button_messages);

        main_button_Course.setOnClickListener(v->{clickCourse();});
        main_button_Messages.setOnClickListener(v->{clickMsg();});


    }

    void clickCourse(){
        click = new Intent(DashBoard_Etudiant.this,Courses.class);
        startActivity(click);
    }
    void clickMsg (){
        click = new Intent(DashBoard_Etudiant.this,Messages.class);
                startActivity(click);
    }

}