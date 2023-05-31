package com.example.educheck.Controleur.DashboardAdmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

    // TODO: Rename and change types of parameters
    private String token;
    private University university;
    private EditText nameAcaBackground;
    private EditText mailReferent;
    private Button buttonAddPath;
    private Spinner spinner, spinChoiceParcour;
    private DashboardImplementation dashboardImplementation;
    private String request;
    private ArrayList<String> dataParcours;

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
        dashboardImplementation = new DashboardImplementation(this);
        spinner = view.findViewById(R.id.spinner_type_choice);
        spinChoiceParcour = view.findViewById(R.id.spinner_acaback_name);
        dataParcours = new ArrayList<>();
        dataParcours.add("please select");
        ArrayAdapter<String> adapterDataParcour = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataParcours);
        adapterDataParcour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinChoiceParcour.setAdapter(adapterDataParcour);
        nameAcaBackground = view.findViewById(R.id.addpathname);
        mailReferent = view.findViewById(R.id.addpathreferentname);
        buttonAddPath = view.findViewById(R.id.addpathbutton);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        mailReferent.addTextChangedListener(watcher);
        buttonAddPath.setOnClickListener(v -> {addAcademicBackground();});
        request = "getAllAcademicBackgrounds";
        dashboardImplementation.getAllAcademicBackgrounds(university.getSuffixeTeacher());
        return view;
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        JSONObject response = items.getJSONObject(0);
        switch (request){
            case "addAcademicBackground" :
                Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                break;
            case "getAllAcademicBackgrounds" :
                if(items.length()>0 && !items.getJSONObject(0).has("status")) {
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject json = items.getJSONObject(i);
                        dataParcours.add(json.getString("type") + " : "+ json.getString("name"));
                    }
                }
                break;
            default: System.err.println("ManagerAcademicBackgroundsFragment: request task not found");
        }
    }

    private void addAcademicBackground(){
        request = "addAcademicBackground";
        dashboardImplementation.addAcademicBackground(token, spinner.getSelectedItem().toString(), nameAcaBackground.getText().toString(),
                university.getUniName(), mailReferent.getText().toString());
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
        }
    };
}