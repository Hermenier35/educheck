package com.example.educheck.Controleur.DashboardAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;

public class DashboardAdmin extends AppCompatActivity implements AsyncTaskcallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {

    }
}