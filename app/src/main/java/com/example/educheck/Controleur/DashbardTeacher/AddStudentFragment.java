package com.example.educheck.Controleur.DashbardTeacher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStudentFragment extends Fragment implements AsyncTaskcallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TOKEN = "token";
    private static final String UNIVERSITY = "university";

    // TODO: Rename and change types of parameters
    private String token;
    private University university;
    private EditText emailStudent;
    private Button btnAddStudent, btnAddFile;
    private String request;
    private Spinner spnType, spnAcaB, spnCour;


    public AddStudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param token token session.
     * @param university University of teacher.
     * @return A new instance of fragment AddStudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddStudentFragment newInstance(String token, University university) {
        AddStudentFragment fragment = new AddStudentFragment();
        Bundle args = new Bundle();
        args.putString(TOKEN, token);
        args.putSerializable(UNIVERSITY, university);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token = getArguments().getString(TOKEN);
            university = (University) getArguments().getSerializable(UNIVERSITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_student2, container, false);
        emailStudent = view.findViewById(R.id.emailStudent);
        btnAddStudent= view.findViewById(R.id.btnAdd);
        btnAddFile = view.findViewById(R.id.btnAddFile);
        spnType = view.findViewById(R.id.spinner_type_choice_teacher);
        spnAcaB = view.findViewById(R.id.spinner_acaback_name_teacher);
        spnCour = view.findViewById(R.id.spinner_course_name_teacher);
        return view;
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        switch (request){

        }
    }

    private void sendRequest(String name){
        this.request = name;

        switch (request){

        }
    }
}