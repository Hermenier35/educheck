package com.example.educheck.Controleur.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.educheck.R;

import java.util.ArrayList;
import java.util.List;

public class FragMarks extends Fragment {
    private static final String ARG_COURSE_NAME = "course_name";
    private static final String ARG_MARKS_LIST = "marks_list";

    private String courseName;
    private List<Integer> marksList;



    public static FragMarks newInstance(String nameCours,String token) {
        FragMarks fragment = new FragMarks();
        Bundle args = new Bundle();
        args.putString("nameCours", nameCours);
        args.putString("token",token);
        fragment.setArguments(args);
        return fragment;
    }
/*
    public static FragMarks newInstance(String courseName, List<Integer> marksList) {
        FragMarks fragment = new FragMarks();
        Bundle args = new Bundle();
        args.putString(ARG_COURSE_NAME, courseName);
        args.putIntegerArrayList(ARG_MARKS_LIST, new ArrayList<>(marksList));
        fragment.setArguments(args);
        return fragment;
    }
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseName = getArguments().getString(ARG_COURSE_NAME);
            marksList = getArguments().getIntegerArrayList(ARG_MARKS_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marks, container, false);
        TextView courseNameTextView = view.findViewById(R.id.courseNameTextView);
        TextView marksTextView = view.findViewById(R.id.marksTextView);

        // Display the course name
        courseNameTextView.setText(courseName);

        // Display the marks
        StringBuilder marksBuilder = new StringBuilder();
        for (int mark : marksList) {
            marksBuilder.append(mark).append(", ");
        }
        marksTextView.setText(marksBuilder.toString());



        return view;
    }

}
