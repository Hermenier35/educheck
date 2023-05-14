package com.example.educheck.Controleur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educheck.Modele.Student;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

import java.util.logging.Logger;

public class parcours_choice extends AppCompatActivity {

    String [] universities = new String[101];
    AutoCompleteTextView autoComplete;
    ArrayAdapter<String> arrayAdapter;
    Button nextPageButton;

    Intent nextIntent;
    String parcours ="";

    @Override
    protected void onCreate(Bundle save) {


        super.onCreate(save);
        setContentView(R.layout.parcours_choice);
        for(int i= 0; i< universities.length; i++){
            universities[i] = "parcours " +i;
        }
        autoComplete = findViewById(R.id.autoComplete);
        arrayAdapter = new ArrayAdapter(this,R.layout.university_list_item,universities);
        autoComplete.setAdapter(arrayAdapter);
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parcours = parent.getItemAtPosition(position).toString();
                nextPageButton.setEnabled(true);
            }
        });

        nextPageButton = findViewById(R.id.nextPage);
        nextPageButton.setOnClickListener(v -> {onNextPageClick();});


    }
    private void onNextPageClick(){
        Logger.getGlobal().info("Parcours choisi: "+parcours);
        nextIntent.putExtra("university",(University)getIntent().getSerializableExtra("university"));
        nextIntent.putExtra("student",(Student)getIntent().getSerializableExtra("student"));
        nextIntent.putExtra("parcours",parcours);
        startActivity(nextIntent);

    }
}
