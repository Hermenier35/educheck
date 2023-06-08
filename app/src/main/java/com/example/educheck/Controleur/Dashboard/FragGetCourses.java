package com.example.educheck.Controleur.Dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.widget.EditText;
import android.widget.ListView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Cours;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;
import com.example.educheck.Modele.Implementation.DashboardImplementation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragGetCourses extends Fragment implements AsyncTaskcallback {
    String token;
    private View view;
    private RecyclerView recyclerView;

    public static View.OnClickListener myOnClickListener;

    private DashboardImplementation request;
    private MailAdapterCard adapter;

    private RecyclerView.Adapter adapter_card;

    private RecyclerView.LayoutManager layoutManager;
    private List<Cours> coursList;

    private ArrayList<String> nameCourses;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_get_cours, container, false);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recyclerView_Cours);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        token = getActivity().getIntent().getStringExtra("token");

        myOnClickListener = new FragGetCourses.MyOnClickListener(getContext(),recyclerView);
        coursList = new ArrayList<>();
        nameCourses= new ArrayList<>();
        adapter = new MailAdapterCard(nameCourses);
        recyclerView.setAdapter(adapter);
        request = new DashboardImplementation(this);
        request.getCourses(token);
        request.getPersonalCourses("token");
        return view;
    }


    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        JSONObject coursesJson = items.getJSONObject(0);
        JSONArray coursesArray = coursesJson.getJSONArray("cours");

        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject courseJson = coursesArray.getJSONObject(i);
            String name = courseJson.getString("name");
            String prof = courseJson.getString("profName");
            int credit = Integer.parseInt(courseJson.getString("credit"));

            Cours course = new Cours(name, prof, credit);
            nameCourses.add(name);
            coursList.add(course);
        }
        coursList.forEach(cours -> System.out.println(cours.toString()));
        adapter_card = new MailAdapterCard(nameCourses);
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
            String nameCours = nameCourses.get(v.getVerticalScrollbarPosition());
            System.out.println(nameCours);
            Fragment Fm = FragMarks.newInstance(nameCours,token);;
            replaceFragment(Fm);
        }
    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1, fragment);
        fragmentTransaction.commit();
    }



}