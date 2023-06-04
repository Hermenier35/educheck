package com.example.educheck.Controleur.Dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

public class FragMessages1 extends Fragment implements AsyncTaskcallback {
    private Intent intentMess1;
    private DashboardImplementation messageImplementation;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter_card;
    private ArrayList<String> users_mail;

    private String token;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.getGlobal().info("Message itent init");
        View view = inflater.inflate(R.layout.fragment_messages1, container, false);
        intentMess1 = new Intent(getContext(), FragMessages2.class);
        layoutManager = new LinearLayoutManager(getContext());
        users_mail = new ArrayList<>();
        token = getActivity().getIntent().getStringExtra("token");
        myOnClickListener = new FragMessages1.MyOnClickListener(getContext(), recyclerView);

        recyclerView = view.findViewById(R.id.recycler_mex);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        messageImplementation = new DashboardImplementation(this);
        messageImplementation.sendMexTo(token);

        adapter_card = new MailAdapterCard(users_mail);
        recyclerView.setAdapter(adapter_card);

        return view;
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        if (items.getJSONObject(0).has("code_retour"))
            System.out.println("code_retour: " + items.getJSONObject(0).get("code_retour"));
            JSONObject mailJson = items.getJSONObject(0);
            String mail = mailJson.getString("mail").replaceAll("[\\[\\]\" ]" , "");
            String[] mails= mail.split(",");
            for (int j=0;j<mails.length;j++) {
                if(!mails[j].equals(users_mail)) {
                    users_mail.add(mails[j]);
                }
            }


        adapter_card = new MailAdapterCard(users_mail);
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
            String mailRecipient = users_mail.get(v.getVerticalScrollbarPosition());
            Fragment fragMessages2 = FragMessages2.newInstance(mailRecipient,token);
            // startActivity(intentMess1);
            replaceFragment(fragMessages2);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1, fragment);
        fragmentTransaction.commit();
    }

}
