package com.example.educheck.Controleur.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.widget.EditText;
import android.widget.ListView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Cours;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;
import com.example.educheck.Modele.Implementation.DashboardImplementation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragGetCourses extends Fragment implements AsyncTaskcallback {
    String token;
    private View view;
    private RecyclerView recyclerView;
    private ListView listView;
    private EditText search;    // search bar
    private DashboardImplementation request;
    private CoursAdapter adapter;
    private List<Cours> coursList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_get_cours, container, false);
        //listView = view.findViewById(R.id.listView);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        token = "token";
        coursList = new ArrayList<>();
        adapter = new CoursAdapter(coursList);
        //adapter = new CoursAdapter<Cours>(getContext(), android.R.layout.simple_list_item_1, coursList);
        recyclerView.setAdapter(adapter);
        request = new DashboardImplementation(this);
        request.getCourses(token);
        request.getPersonalCourses("token");
       // ArrayAdapter<Cours> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, coursList);
       // listView.setAdapter(adapter);
       // listView = view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        for (int i = 0; i < items.length(); i++) {
            JSONObject coursesJson = items.getJSONObject(i);
            String name = coursesJson.getString("name");
            Cours course = new Cours(name,"","","","",0);
            coursList.add(course);
        }
        adapter.notifyDataSetChanged();
    }
}