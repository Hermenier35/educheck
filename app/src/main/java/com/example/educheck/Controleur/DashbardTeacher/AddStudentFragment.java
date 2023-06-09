package com.example.educheck.Controleur.DashbardTeacher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

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
    private static final String GET_ACADEMIC_BACKGROUNDS = "getAcademicBackground", POST_COURSES_STUDENT = "postCoursesStudent";

    // TODO: Rename and change types of parameters
    private String token;
    private University university;
    private EditText emailStudent;
    private TextView filecsv;
    private Button btnAddStudent, btnAddFile;
    private String request, idPath, idCourse;
    private Spinner spnType, spnAcaB, spnCour;
    private ArrayList<String> dataParcours, dataCourse;
    private ArrayList<AcademicBackground> academicBackgrounds;
    private Map<String, ArrayList<Cours>> allCourse;
    private DashboardImplementation dashboardImplementation;
    private ArrayList<String> mails;


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
        filecsv = view.findViewById(R.id.filecsv);

        dataCourse = new ArrayList<>();
        dataParcours = new ArrayList<>();
        academicBackgrounds = new ArrayList<>();
        mails = new ArrayList<>();
        dashboardImplementation = new DashboardImplementation(this);
        allCourse = new HashMap<>();
        dataParcours.add("Select");
        dataCourse.add("Select");

        ArrayAdapter<String> adapterDataParcour = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dataParcours);
        adapterDataParcour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAcaB.setAdapter(adapterDataParcour);

        ArrayAdapter<String> adapterDataCourses = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dataCourse);
        adapterDataParcour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCour.setAdapter(adapterDataCourses);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(adapter);

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typePath = spnType.getSelectedItem().toString();
                if(!typePath.equals("Select"))
                    initialisationSpinnerPath(typePath);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnAcaB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String acaBName = spnAcaB.getSelectedItem().toString();
                if(!acaBName.equals("Select")) {
                    initIdPath(acaBName);
                    initialisationSpinnerCourse(idPath);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnCour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String courseName = spnCour.getSelectedItem().toString();
                if(!courseName.equals("Select"))
                    initIdCourse(courseName, idPath);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        emailStudent.addTextChangedListener(watcher);
        filecsv.addTextChangedListener(watcher);
        btnAddStudent.setEnabled(false);
        btnAddFile.setEnabled(false);
        btnAddStudent.setOnClickListener(v -> initMails("btnAddStudent"));
        btnAddFile.setOnClickListener(v -> initMails("btnAddFile"));

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
                        ArrayList<Cours> cours = new ArrayList<>();
                        JSONArray coursArray = json.getJSONArray("cours");
                        for(int j = 0; j < coursArray.length(); j++){
                            JSONObject c = coursArray.getJSONObject(j);
                            Cours cc = new Cours(c.getString("name"), c.getString("profName"), Integer.parseInt(c.getString("credit")), c.getString("_id"));
                            cours.add(cc);
                        }
                        allCourse.put(json.getString("_id"), cours);
                        academicBackgrounds.add(parcour);
                    }
                }
                break;
            case POST_COURSES_STUDENT:
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
            case POST_COURSES_STUDENT:
                dashboardImplementation.postCoursesStudent(token, mails, idCourse, idPath);
                break;
        }
    }

    private void initialisationSpinnerPath(String typePath){
        dataParcours.clear();
        dataCourse.clear();
        dataCourse.add("Select");
        dataParcours.add("Select");
        spnAcaB.setSelection(0);
        spnCour.setSelection(0);
        for (AcademicBackground aca : academicBackgrounds){
            if(aca.getType().equals(typePath))
                dataParcours.add(aca.getName());
        }
    }

    private void initialisationSpinnerCourse(String idPath){
        dataCourse.clear();
        dataCourse.add("Select");
        if(allCourse.size()>0)
            for (Cours cour : allCourse.get(idPath)){
                dataCourse.add(cour.getName());
            }
    }

    private void initIdPath(String acaName){
        for (AcademicBackground aca : academicBackgrounds){
            if(aca.getName().equals(acaName))
                idPath = aca.get_id();
        }
    }

    private void initIdCourse(String courseName, String idPath){
        if(!courseName.equals("Select"))
            for(Cours cour : allCourse.get(idPath)){
                if(cour.getName().equals(courseName))
                    idCourse = cour.get_id();
            }
    }

    private void initMails(String button){
        mails.clear();
        if(button.equals("btnAddStudent")){
            mails.add(emailStudent.getText().toString());
        }else{

        }
        sendRequest(POST_COURSES_STUDENT);
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
            btnAddStudent.setEnabled(Patterns.EMAIL_ADDRESS.matcher(emailStudent.getText()).matches());
            btnAddFile.setEnabled(true);
        }
    };
}