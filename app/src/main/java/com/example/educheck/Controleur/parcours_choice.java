package com.example.educheck.Controleur;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educheck.R;

public class parcours_choice extends AppCompatActivity {

    String [] universities = new String[101];
    AutoCompleteTextView autoComplete;
    ArrayAdapter<String> arrayAdapter;
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

    }
}
