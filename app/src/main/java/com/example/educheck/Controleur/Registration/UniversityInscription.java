package com.example.educheck.Controleur.Registration;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;


import com.example.educheck.Modele.Implementation.InscriptionImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UniversityInscription extends AppCompatActivity implements AsyncTaskcallback {

    private Intent intentRegistration1;
    private InscriptionImplementation inscriptionImplementation;


    private List<Button> buttons;


    RecyclerView recyclerView;
    private univ_adapter_card adpater_card;
    private ArrayList<University> univs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.getGlobal().info("University itent init");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_inscription);

        intentRegistration1 = new Intent(getApplicationContext(), registration1.class);

        inscriptionImplementation = new InscriptionImplementation(this);
       InitializeCardView();
    }
    private  void InitializeCardView(){
        Logger.getGlobal().info("InitializeCardView");

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        univs = new ArrayList<>();
        adpater_card = new univ_adapter_card(this,univs);
        recyclerView.setAdapter(adpater_card);
        setUniv();
    }

    private void setUniv(){
        Logger.getGlobal().info("setUniv");

        for (int  i= 0; i < 99;i ++) {
            univs.add(new University("université" + i, "suffixe"+i));
        }
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        for(int i = 0; i < items.length(); i++){
            JSONObject uniJson = items.getJSONObject(i);
            University university = new University(uniJson.getString("name"), uniJson.getString("suffixe"));
            /*
            System.out.println(uniJson.getString("suffixe") + "regarde icicicicici");
            Button button = new Button(this);
            button.setText(university.getName());
            layout.addView(button);
            button.setOnClickListener(v-> {
                intentRegistration1.putExtra("university",university);
                startActivity(intentRegistration1);
            });
            buttons.add(button);
            */
            // univs.add(university);
          //  Logger.getGlobal().info(university.toString() + "iccicicicicicicicicicicicicicici");

        }

    }
}