package com.example.educheck.Controleur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.educheck.Controleur.Login.login;
import com.example.educheck.Modele.AcademicBackground;
import com.example.educheck.Modele.Implementation.InscriptionImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.Inscription;
import com.example.educheck.Modele.Student;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parcours_choices extends AppCompatActivity implements AsyncTaskcallback {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    public static View.OnClickListener myOnClickListener;
    private InscriptionImplementation inscription;
    private University university;
    private Student student;
    private ArrayList<AcademicBackground> academicBackgrounds;
    private InscriptionImplementation inscriptionImplementation;
    private Intent logging_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcours_choices);

        myOnClickListener = new MyOnClickListener(this, recyclerView);
        recyclerView = findViewById(R.id.my_recycler_view);
        inscription = new InscriptionImplementation(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        university = (University) getIntent().getSerializableExtra("university") ;
        student = (Student) getIntent().getSerializableExtra("student");
        logging_page = new Intent(this, login.class);
        inscription.getAllAcademicBackgrounds(university.getSuffixe());
        inscriptionImplementation = new InscriptionImplementation(this);
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        if(!items.getJSONObject(0).has("status")) {
            academicBackgrounds = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject json = items.getJSONObject(i);
                AcademicBackground parcour = new AcademicBackground(json.getString("name"), json.getString("type"));
                //parcour.setImage(R.drawable.logo);
                academicBackgrounds.add(parcour);
            }
            adapter = new RecyclerAdapter(academicBackgrounds);
            recyclerView.setAdapter(adapter);
        }
    }

    private class MyOnClickListener implements View.OnClickListener{
        private Context context;
        private RecyclerView recyclerView;
        public MyOnClickListener(Context context, RecyclerView recyclerView){
            this.context = context;
            this.recyclerView = recyclerView;
        }
        @Override
        public void onClick(View v) {
            AcademicBackground academic = academicBackgrounds.get(v.getVerticalScrollbarPosition());
            startActivity(logging_page);
            inscriptionImplementation.registerAcademicBackground(academic, student, university);
        }
    }

}