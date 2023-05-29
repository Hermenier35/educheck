package com.example.educheck.Controleur.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.educheck.R;

import java.util.ArrayList;
import java.util.List;

public class FragAddStudent extends Fragment {

    View view;
    Spinner spinner;

    List CoursesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_frag_add_student, container, false);

        spinner = view.findViewById(R.id.courses_spinner);
        CoursesList = new ArrayList();
        CoursesList.add("Course1");
        CoursesList.add("Course2");
        ArrayAdapter adapter = new ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                CoursesList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return view;
    }
}