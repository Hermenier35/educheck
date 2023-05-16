package com.example.educheck.Controleur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.educheck.Modele.AcademicBackground;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parcours_choices extends AppCompatActivity implements AsyncTaskcallback {
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private View.OnClickListener myOnClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcours_choices);

        myOnClickListener = new MyOnClickListener(this);
        recyclerView = findViewById(R.id.card_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        ArrayList<AcademicBackground> academicBackgrounds = new ArrayList<>();
        for (int i = 0; i< items.length(); i++){
            JSONObject json = items.getJSONObject(i);
            AcademicBackground parcour = new AcademicBackground(json.getString("name"));
            parcour.setImage(R.drawable.logo);
            parcour.setDetails("detail");
            academicBackgrounds.add(parcour);
        }
        adapter = new RecyclerAdapter(academicBackgrounds);
        recyclerView.setAdapter(adapter);
    }

    private class MyOnClickListener implements View.OnClickListener{
        private Context context;
        public MyOnClickListener(Context context){
            this.context = context;
        }
        @Override
        public void onClick(View v) {

        }
    }

}