package com.example.educheck.Controleur.DashbardTeacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Student;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class PresentFragment2 extends Fragment implements AsyncTaskcallback {

    private RecyclerView recyclerViewStudents;

    private PresentAdapter presentAdapter;
    private List<Student> studentList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_precence, container, false);

        // Initialisez le RecyclerView
        recyclerViewStudents = view.findViewById(R.id.recyclerViewStudents);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialisez la liste d'étudiants
        //studentList = getStudentList();

        // Initialisez l'adaptateur et associez-le au RecyclerView
        presentAdapter = new PresentAdapter(studentList);
        recyclerViewStudents.setAdapter(presentAdapter);

        return view;
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {

    }
}
