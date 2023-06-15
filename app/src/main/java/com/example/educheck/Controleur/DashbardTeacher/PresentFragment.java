package com.example.educheck.Controleur.DashbardTeacher;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.educheck.Modele.AcademicBackground;
import com.example.educheck.Modele.Cours;
import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.ButtonListenerCallBack;
import com.example.educheck.Modele.Justify;
import com.example.educheck.Modele.Student;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PresentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PresentFragment extends Fragment implements AsyncTaskcallback, ButtonListenerCallBack {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TOKEN = "token";
    private static final String GET_ACADEMIC_BACKGROUNDS = "getAcademicBackground", ADD_ABS = "addAbsent",
            GET_USERS = "getUsers", GET_ALL_JUST = "getAllJust", ACCEPT = "accept";
    private static final String UNIVERSITY = "university";

    // TODO: Rename and change types of parameters
    private String token;

    private Map<String, ArrayList<Cours>> allCourse;
    private University university;
    private String request;

    private String  idPath, idCourse, mailStudent, idJustify;
    private Spinner spinnerDegree;
    private Spinner spinnerCareer;
    private Spinner spinnerCourses;

    private DashboardImplementation dashboardImplementation;
    private ArrayList<String> dataCareer;
    private ArrayList<String> dataCourses;
    private ArrayList<String> dataDegree;
    private ArrayList<AcademicBackground> academicBackgrounds;
    private ArrayList<Student> students;
    private ArrayList<Student> studentsFilter;
    private Button btnSend;
    private RecyclerView listView;
    private PresentAdapter presentAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public PresentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param token      token session.
     * @param university University of teacher.
     * @return A new instance of fragment PresentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PresentFragment newInstance(String token, University university) {
        PresentFragment fragment = new PresentFragment();
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
        View view = inflater.inflate(R.layout.fragment_present, container, false);
        spinnerCareer = view.findViewById(R.id.spinnerCareerChoice);
        spinnerCourses = view.findViewById(R.id.spinnerCoursesChoice);
        btnSend=view.findViewById(R.id.btnAbsence);
        spinnerDegree = view.findViewById(R.id.spinnerDegreeChoice);
        listView = view.findViewById(R.id.listViewStudent);

        dashboardImplementation = new DashboardImplementation(this);
        allCourse = new HashMap<>();
        students= new ArrayList<>();
        studentsFilter = new ArrayList<>();
        dataCareer = new ArrayList<>();
        dataCourses = new ArrayList<>();
        dataDegree = new ArrayList<>();
        academicBackgrounds = new ArrayList<>();
        dataCareer.add("Select");
        dataCourses.add("Select");
        layoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(layoutManager);
        presentAdapter = new PresentAdapter(studentsFilter, getContext(), this);
        listView.setAdapter(presentAdapter);

        ArrayAdapter<String> adapterDataCareer = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dataCareer);
        adapterDataCareer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCareer.setAdapter(adapterDataCareer);

        ArrayAdapter<String> adapterDataCourses = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dataCourses);
        adapterDataCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(adapterDataCourses);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDegree.setAdapter(adapter);

        setOnItemSelectedSpinnerListener();

        sendRequest(GET_ACADEMIC_BACKGROUNDS);

        btnSend.setOnClickListener(v -> sendRequest(ADD_ABS));

        return view;
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        switch (request) {
            case GET_ACADEMIC_BACKGROUNDS:
                if (items.length() > 0 && !items.getJSONObject(0).has("status")) {
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject json = items.getJSONObject(i);
                        AcademicBackground parcour = new AcademicBackground(json.getString("name"), json.getString("type"),
                                null, json.getString("_id"), json.getString("referant"));
                        ArrayList<Cours> cours = new ArrayList<>();
                        JSONArray coursArray = json.getJSONArray("cours");
                        for (int j = 0; j < coursArray.length(); j++) {
                            JSONObject c = coursArray.getJSONObject(j);
                            Cours cc = new Cours(c.getString("name"), c.getString("profName"), Integer.parseInt(c.getString("credit")), c.getString("_id"));
                            cours.add(cc);
                        }
                        allCourse.put(json.getString("_id"), cours);
                        academicBackgrounds.add(parcour);
                    }
                }
                sendRequest(GET_USERS);
                break;
            case GET_USERS:
                if (items.length() > 0) {
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject user = items.getJSONObject(i);
                        JSONArray path = user.getJSONArray("path");
                        Student student = new Student(user.getString("firstName"), user.getString("lastName"), user.getString("mail"), user.getString("ine"), "Student");
                        for (int k = 0; k < path.length(); k++) {
                            JSONObject parcour = path.getJSONObject(k);
                            JSONArray cours = parcour.getJSONArray("cours");
                            student.addPath(parcour.getString("id"));
                            for (int j = 0; j < cours.length(); j++) {
                                JSONObject res = cours.getJSONObject(j);
                                student.addCour(res.getString("idCour"));
                            }
                        }
                        students.add(student);
                    }
                    studentsFilter.addAll(students);
                }
                sendRequest(GET_ALL_JUST);
                break;
            case ADD_ABS:
            case ACCEPT:
                JSONObject response = items.getJSONObject(0);
                Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                break;
            case GET_ALL_JUST:
                if (items.length() > 0) {
                    JSONObject marksJson = items.getJSONObject(0);
                    JSONArray justifs = marksJson.getJSONArray("justif");
                    for (int i = 0; i < justifs.length(); i++) {
                        JSONObject abences = justifs.getJSONObject(i);
                        String pdf = "";
                        if(abences.has("image"))
                            pdf = abences.getString("image");
                        Justify justify = new Justify(abences.getString("id_j"), abences.getString("mailStudent"), abences.getString("date"), abences.getString("nameCours"), abences.getString("justifie"), pdf);
                        students.forEach(student -> addJustify(student, justify));
                    }
                }
                break;
        }
    }

    private void sendRequest(String name) {
        this.request = name;

        switch (request){
            case GET_ACADEMIC_BACKGROUNDS:
                dashboardImplementation.getAllAcademicBackgrounds(university.getSuffixeTeacher());
                break;
            case GET_USERS:
                students.clear();
                dashboardImplementation.getUsers(token);
                break;
            case ADD_ABS:
                Calendar instance = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm" );
                String formattedDate = sdf.format(instance.getTime());
                ArrayList mail = initSendDataAbs();
                dashboardImplementation.addAbs(token, mail, spinnerCourses.getSelectedItem().toString(), formattedDate);
                break;
            case GET_ALL_JUST:
                dashboardImplementation.getAllJust(token);
                break;
            case ACCEPT:
                dashboardImplementation.justifyProf(this.idJustify, this.token, this.mailStudent);
                break;
        }
    }

    private void addJustify(Student s, Justify justify){
        if(s.getMail().equals(justify.getMailStudent()))
            s.getJustifies().add(justify);
    }

    private ArrayList<String> initSendDataAbs(){
        ArrayList<String> mails = new ArrayList<>();
        ArrayList<PresentAdapter.StudentViewHolder> studentViewHolders = presentAdapter.getViewHolders();
        for(int i =0; i <studentViewHolders.size(); i++){
            if(studentViewHolders.get(i).checkBox.isChecked()) {
                mails.add(studentViewHolders.get(i).textViewMail.getText().toString());
            }
        }

        return mails;
    }



    private void initialisationSpinnerPath(String typePath) {
        dataCareer.clear();
        dataCourses.clear();
        dataCourses.add("Select");
        dataCareer.add("Select");
        spinnerCareer.setSelection(0);
        spinnerCourses.setSelection(0);
        for (AcademicBackground aca : academicBackgrounds) {
            if (aca.getType().equals(typePath))
                dataCareer.add(aca.getName());
        }
    }
    private void initialisationSpinnerCourse(String idPath){
        dataCourses.clear();
        dataCourses.add("Select");
        if(allCourse.size()>0)
            for (Cours cour : allCourse.get(idPath)){
                dataCourses.add(cour.getName());
            }
    }
    private void initIdPath(String acaName){
        for (AcademicBackground aca : academicBackgrounds){
            if(aca.getName().equals(acaName) && aca.getType().equals(spinnerDegree.getSelectedItem().toString()))
                idPath = aca.get_id();
        }
    }
    private void initIdCourse(String courseName, String idPath){
        for(Cours cour : allCourse.get(idPath)){
            if(cour.getName().equals(courseName))
                idCourse = cour.get_id();
        }
    }
    private void filterStudent(String name, String id){
        studentsFilter.clear();
        studentsFilter.addAll(students);
        if(name.equals("path")){
            studentsFilter.removeIf(student -> !student.getPaths().contains(id));
        }else if(name.equals("course")) {
            studentsFilter.removeIf(student -> !student.getCours().contains(id));
        }
        presentAdapter = new PresentAdapter(studentsFilter, getContext(), this);
        listView.setAdapter(presentAdapter);
    }

    private boolean isCourseSelected(){
        return !spinnerDegree.getSelectedItem().toString().equals("Select") &&
                !spinnerCareer.getSelectedItem().toString().equals("Select") &&
                !spinnerCourses.getSelectedItem().toString().equals("Select");
    }

    private void setOnItemSelectedSpinnerListener(){

        spinnerDegree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typePath = spinnerDegree.getSelectedItem().toString();
                if(!typePath.equals("Select")) {
                    initialisationSpinnerPath(typePath);
                    filterStudent("type", "");
                }

                btnSend.setEnabled(isCourseSelected());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCareer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String acaBName = spinnerCareer.getSelectedItem().toString();
                if(!acaBName.equals("Select")) {
                    initIdPath(acaBName);
                    initialisationSpinnerCourse(idPath);
                    filterStudent("path", idPath);
                }
                btnSend.setEnabled(isCourseSelected());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String courseName = spinnerCourses.getSelectedItem().toString();
                if(!courseName.equals("Select")) {
                    initIdCourse(courseName, idPath);
                    filterStudent("course", idCourse);
                }
                btnSend.setEnabled(isCourseSelected());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

        }
    };

    @Override
    public void callBackListener(String request) {
        String data[];
        if(request.startsWith("accept")) {
             data = request.split(" ");
             this.mailStudent = data[2];
             this.idJustify = data[1];
            sendRequest(ACCEPT);
        }
        else if(request.startsWith("openFile")){
            data = request.split(" ");
            String pdf = data[1];
            openPdfFile(Base64.getDecoder().decode(pdf));
        }
    }

    private void openPdfFile(byte[] pdfByteArray) {
        try {
            // Create a temporary file to save the PDF
            File pdfFile = File.createTempFile("temp", ".pdf", getActivity().getCacheDir());

            // Write the byte array to the temporary file
            FileOutputStream fos = new FileOutputStream(pdfFile);
            fos.write(pdfByteArray);
            fos.close();

            // Generate a content URI for the file using FileProvider
            Uri contentUri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".fileprovider", pdfFile);

            // Create an intent to open the PDF file
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(contentUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                // Grant read permissions to the receiving app
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Attempt to open the PDF file using an external PDF viewer app
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // If no PDF viewer app is available, show an error message
                Toast.makeText(getActivity(), "No PDF viewer app found.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}