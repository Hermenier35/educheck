package com.example.educheck.Controleur.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Marks;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragJustification1 extends Fragment implements AsyncTaskcallback {

    private String token;

    private ArrayList<Justificatif> justList;

    private DashboardImplementation request;


    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter_card;

    private String mail;

    public static FragJustification1 newInstance(String token,String mail) {
        FragJustification1 fragment = new FragJustification1();
        Bundle args = new Bundle();
        args.putString("token",token);
        args.putString("mail",mail);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_justification1, container, false);
        token = getActivity().getIntent().getStringExtra("token");
        mail = getActivity().getIntent().getStringExtra("mail");
        layoutManager = new LinearLayoutManager(getContext());
        justList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_just);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

         myOnClickListener = new FragJustification1.MyOnClickListener(getContext(), recyclerView);


        request = new DashboardImplementation(this);
        request.getAllJust(token);

        adapter_card = new JustifAdapterCard(justList);
        recyclerView.setAdapter(adapter_card);



        return view;
    }


    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {

        JSONObject marksJson = items.getJSONObject(0);
        System.out.println(marksJson.toString());
        JSONArray justifArray = marksJson.getJSONArray("justif");

        for (int i = 0; i < justifArray.length(); i++) {
            JSONObject justifJson = justifArray.getJSONObject(i);
            String name= justifJson.getString("nameCours");
            String id = justifJson.getString("id_j");
            String prof = justifJson.getString("mailProf");
            String date = justifJson.getString("date");
            Justificatif j = new Justificatif(id, date, new byte[0],prof,name,mail);
            justList.add(j);

        }

        adapter_card = new JustifAdapterCard(justList);
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
            int position = recyclerView.getChildAdapterPosition(v);
            if (position != RecyclerView.NO_POSITION) {
                Justificatif justificatif = justList.get(position);
                String id = justificatif.getId_justificatif();
                String name = justificatif.getNameCours();
                String prof = justificatif.getProfName();

                Fragment fragJust2 = FragJustification2.newInstance(token, id, prof, name);
                replaceFragment(fragJust2);
            }
        }

        private void replaceFragment(Fragment fragment) {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame1, fragment);
            fragmentTransaction.commit();
        }

    }
}
