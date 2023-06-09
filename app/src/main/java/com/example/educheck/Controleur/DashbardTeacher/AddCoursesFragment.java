package com.example.educheck.Controleur.DashbardTeacher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educheck.Modele.AcademicBackground;
import com.example.educheck.Modele.Cours;
import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCoursesFragment extends Fragment implements AsyncTaskcallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TOKEN = "token";
    private static final String UNIVERSITY = "university";
    private static final String GET_ACADEMIC_BACKGROUNDS = "getAcademicBackground", ADD_COURSES = "AddCourse";

    // TODO: Rename and change types of parameters
    private String token;
    private University university;
    private String request;
    private Spinner type, path;
    private String idPath;
    private EditText ects, teacher, name;
    private Button add;
    private DashboardImplementation dashboardImplementation;
    private ArrayList<AcademicBackground> academicBackgrounds;
    private ArrayList<String> dataParcours;


    public AddCoursesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param token token session.
     * @param university University of teacher.
     * @return A new instance of fragment PresentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCoursesFragment newInstance(String token, University university) {
        AddCoursesFragment fragment = new AddCoursesFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_courses, container, false);

        type = view.findViewById(R.id.spinner_select_type);
        path = view.findViewById(R.id.spinner_select_parcour);
        ects = view.findViewById(R.id.editects);
        teacher = view.findViewById(R.id.editteacher);
        name = view.findViewById(R.id.editname);
        add = view.findViewById(R.id.btnaddCourse);

        academicBackgrounds = new ArrayList<>();
        dataParcours = new ArrayList<>();
        dashboardImplementation = new DashboardImplementation(this);
        dataParcours.add("Select");


        ArrayAdapter<String> adapterDataParcour = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dataParcours);
        adapterDataParcour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        path.setAdapter(adapterDataParcour);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typePath = type.getSelectedItem().toString();
                initialisationSpinnerPath(typePath);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        path.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initIdPath(path.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ects.addTextChangedListener(watcher);
        teacher.addTextChangedListener(watcher);
        name.addTextChangedListener(watcher);

        add.setOnClickListener(v -> sendRequest(ADD_COURSES));
        add.setEnabled(false);

        sendRequest(GET_ACADEMIC_BACKGROUNDS);
        return view;
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        switch (request){
            case GET_ACADEMIC_BACKGROUNDS:
                if(items.length()>0 && !items.getJSONObject(0).has("status")) {
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject json = items.getJSONObject(i);
                        AcademicBackground parcour = new AcademicBackground(json.getString("name"), json.getString("type"),
                                null, json.getString("_id"), json.getString("referant"));
                        academicBackgrounds.add(parcour);
                    }
                }
                break;
            case ADD_COURSES:
                JSONObject response = items.getJSONObject(0);
                Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void sendRequest(String name){
        this.request = name;

        switch (request){
            case GET_ACADEMIC_BACKGROUNDS:
                dashboardImplementation.getAllAcademicBackgrounds(university.getSuffixeTeacher());
                break;
            case ADD_COURSES:
                int credit = Integer.parseInt(this.ects.getText().toString());
                Cours cour = new Cours(this.name.getText().toString(), this.teacher.getText().toString(), credit);
                dashboardImplementation.addCourse(token, cour, idPath);
                ects.setText("");
                teacher.setText("");
                this.name.setText("");
                break;
        }
    }

    private void initialisationSpinnerPath(String type){
        dataParcours.clear();
        dataParcours.add("Select");
        path.setSelection(0);
        for (AcademicBackground aca : academicBackgrounds){
            if(aca.getType().equals(type))
                dataParcours.add(aca.getName());
        }
    }

    private void initIdPath(String acaName){
        if(!acaName.equals("Select"))
            for (AcademicBackground aca : academicBackgrounds){
                if(aca.getName().equals(acaName))
                    idPath = aca.get_id();
            }
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            boolean valueSpin = !path.getSelectedItem().toString().equals("Select") && !type.getSelectedItem().toString().equals("Select");
            int credit = 0;
            if(ects.getText().length()>0)
                credit = Integer.parseInt(ects.getText().toString());
            add.setEnabled(valueSpin && name.getText().length() > 2 && teacher.getText().length() > 3 && credit!=-1);
        }
    };
}