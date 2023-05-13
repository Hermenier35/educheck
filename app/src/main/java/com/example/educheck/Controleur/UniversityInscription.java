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

public class UniversityInscription extends AppCompatActivity {

    private Intent intentRegistration1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_inscription);
        intentRegistration1 = new Intent(getApplicationContext(),registration1.class);


        JSONArray universities = new JSONArray();
        try {
            universities.put(new JSONObject().put("name", "rennes1"));
            universities.put(new JSONObject().put("name", "rennes2"));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //Chargez le fichier JSON et convertissez-le en une liste de noms

        Gson gson = new Gson();
        Type type = new TypeToken<List<University>>() {
        }.getType();
        JSONObject jsonObject = null;
        //List<University> Listuniversities = gson.fromJson(jsonObject.getJSONArray("universities").toString(), type);

        List<University> univs = new ArrayList<>();
        univs.add(new University("univ1"));
        univs.add(new University("univ2"));



        LinearLayout layout = findViewById(R.id.list_button);



        for (University university : univs) {
            Button button = new Button(this);
            button.setText(university.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentRegistration1.putExtra("university",university);
                    startActivity(intentRegistration1);
                }
            });
            layout.addView(button);
        }

    }
}