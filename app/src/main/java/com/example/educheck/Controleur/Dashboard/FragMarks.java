package com.example.educheck.Controleur.Dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.educheck.Modele.Cours;
import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Marks;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragMarks extends Fragment implements AsyncTaskcallback {

    private String courseName;

    private String token;
    private ArrayList<Marks> marksList;

    private TextView Marks_Name;

    private DashboardImplementation marksImplementation;

    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter_card;



    public static FragMarks newInstance(String coursName,String token) {
        FragMarks fragment = new FragMarks();
        Bundle args = new Bundle();
        args.putString("course_name", coursName);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marks, container, false);
        TextView courseNameTextView = view.findViewById(R.id.courseName);
        courseName = getArguments().getString("course_name");
        token=getArguments().getString("token");
        layoutManager = new LinearLayoutManager(getContext());
        courseNameTextView.setText(courseName+" : ");
        marksList = new ArrayList<>();
        myOnClickListener = new FragMarks.MyOnClickListener(getContext(), recyclerView);

        recyclerView = view.findViewById(R.id.marksTextView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        marksImplementation = new DashboardImplementation(this);
        marksImplementation.getMarks(token,courseName);

        adapter_card = new MarksAdapterCard(marksList);
        recyclerView.setAdapter(adapter_card);



        return view;
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {

        JSONObject marksJson = items.getJSONObject(0);
        System.out.println(marksJson.toString());
        System.out.println(courseName);
        JSONArray marksArray = marksJson.getJSONArray("notes");

        for (int i = 0; i < marksArray.length(); i++) {
            JSONObject markJson = marksArray.getJSONObject(i);
            String name= markJson.getString("nameCours");
            System.out.println(name+"   "+ courseName);
            if(name.equals(courseName)) {
                String type = markJson.getString("type");
                String prof = markJson.getString("nameProf");
                float note = Float.parseFloat(markJson.getString("note"));
                Marks mark = new Marks(prof, type, note);
                marksList.add(mark);
            }
        }

        adapter_card = new MarksAdapterCard(marksList);
        recyclerView.setAdapter(adapter_card);


    }


    private class MyOnClickListener implements View.OnClickListener {
        private Context context;
        private RecyclerView recyclerView;

        public MyOnClickListener(Context context, RecyclerView recyclerView) {
            this.context = context;
            this.recyclerView = recyclerView;
        }

        @Override
        public void onClick(View v) {
            //Fragment fragMessages2 = FragMessages2.newInstance("mymail",token);
            // startActivity(intentMess1);
            //replaceFragment("name of the fragment");
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1, fragment);
        fragmentTransaction.commit();
    }


}
