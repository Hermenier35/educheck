package com.example.educheck.Controleur.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.educheck.Modele.Cours;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;
import com.example.educheck.Modele.Implementation.DashboardImplementation;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class Frag_GetCours extends Fragment implements AsyncTaskcallback {
    String token;
    View view;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> coursList;
    DashboardImplementation dashboardImp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag1, container, false);
        dashboardImp.getCourses(token);

        // TO DO
        listView = view.findViewById(R.id.listView);

        coursList = new ArrayList<>();
        coursList.add("Math1");
        coursList.add("INF1");
        coursList.add("CODAGE");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, coursList);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        //TO DO
    }
}