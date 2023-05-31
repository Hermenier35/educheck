package com.example.educheck.Controleur.Dashboard;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Controleur.Registration.Registration1;
import com.example.educheck.Controleur.Registration.UnivAdapterCard;
import com.example.educheck.Controleur.Registration.UniversityInscription;
import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Implementation.InscriptionImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.Modele.Users;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.logging.Logger;

public class FragMessages1 extends AppCompatActivity implements AsyncTaskcallback {
    private Intent intentMess1;
    private DashboardImplementation messageImplementation;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adpater_card;
    private ArrayList<String>  users_mail ;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.getGlobal().info("University itent init");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_inscription);
        intentMess1 = new Intent(getApplicationContext(), FragMessages2.class);
        layoutManager = new LinearLayoutManager(this);
        users_mail = new ArrayList<>();
        token = getIntent().getStringExtra("token");
        myOnClickListener = new FragMessages1.MyOnClickListener(this, recyclerView);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        messageImplementation = new DashboardImplementation(this);
        messageImplementation.sendMexTo(token);
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        if(items.getJSONObject(0).has("code_retour"))
            System.out.println("code_retour: " + items.getJSONObject(0).get("code_retour"));
        for(int i = 0; i < items.length(); i++){
            JSONObject mailJson = items.getJSONObject(i);
            System.out.println("value de i = " + i);
            Iterator<String> it = mailJson.keys();
            while(it.hasNext())
                System.out.println(it.next());
            String mail =mailJson.getString("mail");
            users_mail.add(mail);
        }
        adpater_card = new MailAdapterCard(users_mail);
        recyclerView.setAdapter(adpater_card);


    }


    private class MyOnClickListener implements View.OnClickListener{
        private Context context;
        private RecyclerView recyclerView;
        public MyOnClickListener(Context context, RecyclerView recyclerView){
            this.context = context;
            this.recyclerView = recyclerView;
        }
        @Override
        public void onClick(View v) {
            String mailRecipient = users_mail.get(v.getVerticalScrollbarPosition());
            intentMess1.putExtra("mailRecipient",mailRecipient);
            startActivity(intentMess1);
        }
    }
}
