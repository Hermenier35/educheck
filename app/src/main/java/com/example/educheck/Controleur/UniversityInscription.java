package com.example.educheck.Controleur;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;


import com.example.educheck.Modele.Implementation.InscriptionImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UniversityInscription extends AppCompatActivity implements AsyncTaskcallback {

    private Intent intentRegistration1;
    private InscriptionImplementation inscriptionImplementation;
    private LinearLayout layout;

    private SearchView searchView;

    private List<Button> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_inscription);
        intentRegistration1 = new Intent(getApplicationContext(),registration1.class);

        inscriptionImplementation = new InscriptionImplementation(this);
        inscriptionImplementation.getAllUniversities();

        layout = findViewById(R.id.list_button);
        searchView = findViewById(R.id.searchView);
        buttons = new ArrayList<>();

        //Configurer les listeners pour la barre de recherche
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                for (Button button : buttons) {
                    String buttonText = button.getText().toString();

                    if (buttonText.toLowerCase().contains(s.toLowerCase())) {
                        button.setVisibility(View.VISIBLE);
                    } else {
                        button.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false);

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
            buttons.add(button);
        }
    }
}