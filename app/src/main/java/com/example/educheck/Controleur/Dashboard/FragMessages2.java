package com.example.educheck.Controleur.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Message;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;
import java.util.Date;


public class FragMessages2 extends Fragment implements AsyncTaskcallback {

    private View view;
     private EditText messageToSend;
     private Button buttonSend;

     private Message message;

     private String token;

     private String mailRecipient;

     private String mailSender;

    Calendar calendar = Calendar.getInstance();
    DashboardImplementation model_message;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment__messages, container, false);
        mailSender= getActivity().getIntent().getStringExtra("mail");
        mailRecipient = getArguments().getString("mailRecipient");
        token = getActivity().getIntent().getStringExtra("token");
        messageToSend = view.findViewById(R.id.messageToSend);
        buttonSend = view.findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(v -> send_message());

        return view;
    }

    public void send_message(){

        String text = messageToSend.getText().toString();
       Date d=new Date();

       System.out.println("Current date and time: " + d);
        System.out.println("Current mailRecipient: " + mailRecipient);
        System.out.println("Current mailSender: " + mailSender);
        System.out.println("Current message: " + text);


        Message mess=new Message(mailRecipient,mailSender,text,d);
       // model_message.sendMessageTo(mess,token);
    }

    public static FragMessages2 newInstance(String mailRecipient) {
        FragMessages2 fragment = new FragMessages2();
        Bundle args = new Bundle();
        args.putString("mailRecipient", mailRecipient);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
    }
    }