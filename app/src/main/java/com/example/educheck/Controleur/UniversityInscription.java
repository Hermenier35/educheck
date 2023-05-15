package com.example.educheck.Controleur;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.educheck.Modele.Implementation.InscriptionImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
            University university = new University(uniJson.getString("name"));
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