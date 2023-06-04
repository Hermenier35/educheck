package com.example.educheck.Controleur.DashboardAdmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.educheck.Modele.AcademicBackground;
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
 * Use the {@link ManagerAcademicBackgroundsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagerAcademicBackgroundsFragment extends Fragment implements AsyncTaskcallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TOKEN = "token";
    private static final String UNIVERSITY = "university";
    private static final String ADD_ACADEMIC_BACKGROUND = "addAcademicBackground", EDIT_ACADEMIC_BACKGROUND = "editAcademicBackground",
     DELETE_ACADEMIC_BACKGROUND = "deleteAcademicBackground", GET_ALL_ACADEMIC_BACKGROUNDS = "getAllAcademicBackgrounds";

    // TODO: Rename and change types of parameters
    private String token;
    private University university;
    private EditText nameAcaBackground, editNameAcaBackground;
    private EditText mailReferent, editMailReferent;
    private Button buttonAddPath, editPath, delete;
    private Spinner spinner, spinChoiceParcour;
    private DashboardImplementation dashboardImplementation;
    private String request;
    private ArrayList<String> dataParcours;
    private ArrayList<AcademicBackground>  academicBackgrounds;

    public ManagerAcademicBackgroundsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param token TOKEN of session.
     * @param university university of admin.
     * @return A new instance of fragment ManagerAcademicBackgroundsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManagerAcademicBackgroundsFragment newInstance(String token, University university) {
        ManagerAcademicBackgroundsFragment fragment = new ManagerAcademicBackgroundsFragment();
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
            university = (University)getArguments().getSerializable(UNIVERSITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_academic_backgrounds, container, false);

        spinner = view.findViewById(R.id.spinner_type_choice);
        spinChoiceParcour = view.findViewById(R.id.spinner_acaback_name);
        nameAcaBackground = view.findViewById(R.id.addpathname);
        mailReferent = view.findViewById(R.id.addpathreferentname);
        buttonAddPath = view.findViewById(R.id.addpathbutton);
        editPath = view.findViewById(R.id.editpathbutton);
        delete = view.findViewById(R.id.deletepathbutton);
        editMailReferent = view.findViewById(R.id.editpathreferentname);
        editNameAcaBackground = view.findViewById(R.id.editpathname);

        dashboardImplementation = new DashboardImplementation(this);
        dataParcours = new ArrayList<>();
        academicBackgrounds = new ArrayList<>();

        dataParcours.add("Please select");
        ArrayAdapter<String> adapterDataParcour = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dataParcours);
        adapterDataParcour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinChoiceParcour.setAdapter(adapterDataParcour);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mailReferent.addTextChangedListener(watcher);
        editMailReferent.addTextChangedListener(watcher);
        buttonAddPath.setOnClickListener(v -> sendRequest(ADD_ACADEMIC_BACKGROUND));
        editPath.setOnClickListener(v -> sendRequest(EDIT_ACADEMIC_BACKGROUND));
        delete.setOnClickListener(v -> sendRequest(DELETE_ACADEMIC_BACKGROUND));
        sendRequest(GET_ALL_ACADEMIC_BACKGROUNDS);
        spinChoiceParcour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                delete.setEnabled(!spinChoiceParcour.getSelectedItem().toString()
                        .equals("Please select"));
                AcademicBackground find = findAcaByName(spinChoiceParcour.getSelectedItem().toString());
                if(find!=null){
                    editMailReferent.setText(find.getReferent());
                    editNameAcaBackground.setText(find.getName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonAddPath.setEnabled(false);
        editPath.setEnabled(false);
        return view;
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        JSONObject response = items.getJSONObject(0);
        switch (request){
            case ADD_ACADEMIC_BACKGROUND:
            case EDIT_ACADEMIC_BACKGROUND:
            case DELETE_ACADEMIC_BACKGROUND:
                Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                sendRequest(GET_ALL_ACADEMIC_BACKGROUNDS);
                break;
            case GET_ALL_ACADEMIC_BACKGROUNDS:
                if(items.length()>0 && !items.getJSONObject(0).has("status")) {
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject json = items.getJSONObject(i);
                        dataParcours.add(json.getString("type") + " : "+ json.getString("name"));
                        academicBackgrounds.add(new AcademicBackground(json.getString("name"), json.getString("type"),
                                null,json.getString("_id"), json.getString("referant")));
                    }
                }
                break;
            default: System.err.println("ManagerAcademicBackgroundsFragment: request task not found");
        }
    }

    private void sendRequest(String name){
        this.request = name;
        switch(name){
            case GET_ALL_ACADEMIC_BACKGROUNDS:
                dashboardImplementation.getAllAcademicBackgrounds(university.getSuffixeTeacher());
                dataParcours.clear();
                academicBackgrounds.clear();
                dataParcours.add("Please select");
                break;
            case ADD_ACADEMIC_BACKGROUND:
                dashboardImplementation.addAcademicBackground(token, spinner.getSelectedItem().toString(), nameAcaBackground.getText().toString(),
                        university.getUniName(), mailReferent.getText().toString());
                break;
            case EDIT_ACADEMIC_BACKGROUND:
                String valueSpinner = spinChoiceParcour.getSelectedItem().toString();
                AcademicBackground find = findAcaByName(valueSpinner);
                find.setName(editNameAcaBackground.getText().toString());
                find.setReferent(editMailReferent.getText().toString());
                dashboardImplementation.editAcademicBackground(token, find);
                break;
            case DELETE_ACADEMIC_BACKGROUND:
                String valueSpinnerDelete = spinChoiceParcour.getSelectedItem().toString();
                AcademicBackground aca = findAcaByName(valueSpinnerDelete);
                dashboardImplementation.deleteAcademicBackground(token, aca.get_id());
                break;
            default: System.err.println("No request Found");
        }
    }

    private AcademicBackground findAcaByName(String valueSpinner){
        String data[] = valueSpinner.split(" : ");
        for(int i = 0; i< academicBackgrounds.size(); i++){
            if(academicBackgrounds.get(i).getType().equals(data[0]) && academicBackgrounds.get(i).getName().equals(data[1]))
                return academicBackgrounds.get(i);
        }
        System.err.println("Aucun parcours trouvé");
        return null;
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
            buttonAddPath.setEnabled(nameAcaBackground.getText().length()>=5
                    && Patterns.EMAIL_ADDRESS.matcher(mailReferent.getText()).matches()
            );

            editPath.setEnabled(Patterns.EMAIL_ADDRESS.matcher(editMailReferent.getText()).matches()
                && editNameAcaBackground.getText().length() >=5 && !spinChoiceParcour.getSelectedItem().toString()
                    .equals("Please select"));

        }
    };
}