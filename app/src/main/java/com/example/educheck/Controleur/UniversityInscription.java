package com.example.educheck.Controleur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.educheck.Modele.University;
import com.example.educheck.R;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UniversityInscription extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_inscription);


        JSONArray universities = new JSONArray();
        try {
            universities.put(new JSONObject().put("name","rennes1"));
            universities.put(new JSONObject().put("name","rennes2"));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //Chargez le fichier JSON et convertissez-le en une liste de noms

        //Gson gson = new Gson();
        //Type type = new TypeToken<List<University>>(){}.getType();
        JSONObject jsonObject = null;
        //List<University> Listuniversities = gson.fromJson(jsonObject.getJSONArray("universities").toString(), type);

        //List<String> names = new ArrayList<>();
        //for (University university : Listuniversities) {
          //  names.add(university.getName());
        //}
    }


}