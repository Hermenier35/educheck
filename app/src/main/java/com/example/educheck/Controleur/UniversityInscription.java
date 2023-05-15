package com.example.educheck.Controleur;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.LinearLayout;


import com.example.educheck.Modele.Implementation.InscriptionImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class UniversityInscription extends AppCompatActivity implements AsyncTaskcallback {

    private Intent intentRegistration1;
    private InscriptionImplementation inscriptionImplementation;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_inscription);
        intentRegistration1 = new Intent(getApplicationContext(),registration1.class);

        inscriptionImplementation = new InscriptionImplementation(this);
        inscriptionImplementation.getAllUniversities();

        layout = findViewById(R.id.list_button);

    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        for(int i = 0; i < items.length(); i++){
            JSONObject uniJson = items.getJSONObject(i);
            University university = new University(uniJson.getString("name"), uniJson.getString("suffixe"));
            System.out.println(uniJson.getString("suffixe") + "regarde icicicicici");
            Button button = new Button(this);
            button.setText(university.getName());
            layout.addView(button);
            button.setOnClickListener(v-> {
                intentRegistration1.putExtra("university",university);
                startActivity(intentRegistration1);
            });
        }
    }
}