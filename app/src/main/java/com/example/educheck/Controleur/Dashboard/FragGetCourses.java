package com.example.educheck.Controleur.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

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
    //private Spinner course;
    private ListView listView;
    private List CoursesList;

    private EditText search;    // search bar
    private DashboardImplementation request;

    private ArrayAdapter<String> adapter;
    private List<String> coursesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag1, container, false);

        coursesList = new ArrayList<>();
        coursesList.add("Math1");
        coursesList.add("INF1");
        coursesList.add("CODAGE");

        //course = view.findViewById(R.id.courses_spinner);
        request = new DashboardImplementation(this);
        request.getCourses(token);

        request.getPersonalCourses("token");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, coursesList);
        listView.setAdapter(adapter);

        listView = view.findViewById(R.id.listView);
        return view;

    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        for(int i = 0; i < items.length(); i++){
            JSONObject coursesJson = items.getJSONObject(i);
            CoursesList.add(coursesJson.getString("name"));
        }

        ArrayAdapter adapter = new ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                CoursesList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
    }
}