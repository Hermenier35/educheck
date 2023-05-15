package com.example.educheck.Controleur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educheck.Modele.Implementation.InscriptionImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.Inscription;
import com.example.educheck.Modele.Student;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Logger;

public class parcours_choice extends AppCompatActivity implements AsyncTaskcallback {

    String [] universities = new String[101];
    AutoCompleteTextView autoComplete;
    ArrayAdapter<String> arrayAdapter;
    Button nextPageButton;

    Intent nextIntent;
    String parcours ="";
    Inscription inscription;
    University university;

    @Override
    protected void onCreate(Bundle save) {


        super.onCreate(save);
        setContentView(R.layout.parcours_choice);
        for(int i= 0; i< universities.length; i++){
            universities[i] = "parcours " +i;
        }
        inscription = new InscriptionImplementation(this);
        university = (University) getIntent().getSerializableExtra("university") ;
        inscription.getAllAcademicBackgrounds(university.getSuffixe());
        autoComplete = findViewById(R.id.autoComplete);


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

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        ArrayList<String> parcoursList = new ArrayList<>();

        for (int i = 0; i < items.length(); i++) {
            JSONObject parcour = items.getJSONObject(i);
            parcoursList.add(parcour.getString("name"));
        }
        arrayAdapter = new ArrayAdapter(this,R.layout.university_list_item,parcoursList);
        autoComplete.setAdapter(arrayAdapter);
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parcours = parent.getItemAtPosition(position).toString();
                nextPageButton.setEnabled(true);
            }
        });

    }
}
