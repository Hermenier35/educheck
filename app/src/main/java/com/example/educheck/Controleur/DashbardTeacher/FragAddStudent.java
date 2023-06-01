package com.example.educheck.Controleur.DashbardTeacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragAddStudent extends Fragment implements AsyncTaskcallback {

        private View view;
        private Spinner spinner;
        private List CoursesList;
        private Button registre;

        private EditText mailStudent;

        private DashboardImplementation request;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            view =  inflater.inflate(R.layout.fragment_add_student, container, false);

            mailStudent = view.findViewById(R.id.mail_student);
            spinner = view.findViewById(R.id.courses_spinner);
            registre = view.findViewById(R.id.addStudentToCourse);

            CoursesList = new ArrayList();

            request = new DashboardImplementation(this);
            request.getPersonalCourses("token");// les cours enseignés par le prof

            registre.setOnClickListener(v->{saveToBDD();});

            return view;
        }

        public void saveToBDD(){
            //TODO
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
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

        }

}
