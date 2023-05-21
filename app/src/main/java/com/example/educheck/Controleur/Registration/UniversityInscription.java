package com.example.educheck.Controleur.Registration;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;


import com.example.educheck.Modele.Implementation.InscriptionImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Logger;


public class UniversityInscription extends AppCompatActivity implements AsyncTaskcallback {

    private Intent intentRegistration1;
    private InscriptionImplementation inscriptionImplementation;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adpater_card;
    private ArrayList<University> univs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.getGlobal().info("University itent init");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_inscription);
        intentRegistration1 = new Intent(getApplicationContext(), registration1.class);
        layoutManager = new LinearLayoutManager(this);
        univs = new ArrayList<>();
        myOnClickListener = new MyOnClickListener(this, recyclerView);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        inscriptionImplementation = new InscriptionImplementation(this);
        inscriptionImplementation.getAllUniversities();
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        for(int i = 0; i < items.length(); i++){
            JSONObject uniJson = items.getJSONObject(i);
            University university = new University(uniJson.getString("name"), uniJson.getString("suffixe_teacher"));
            univs.add(university);
        }
            adpater_card = new univ_adapter_card(univs);
            recyclerView.setAdapter(adpater_card);
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
            University university = univs.get(v.getVerticalScrollbarPosition());
            System.out.println("test ici : " +  v.getVerticalScrollbarPosition());
            intentRegistration1.putExtra("university",university);
            startActivity(intentRegistration1);
        }
    }
}