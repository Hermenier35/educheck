package com.example.educheck.Controleur.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.educheck.R;


public class Frag_Send_mail extends Fragment {

    View view;

     EditText editTextTo, editTextSubject, editTextMessage;
     Button buttonSend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_frag__send_mail, container, false);

        editTextTo = view.findViewById(R.id.editTextTo);
        editTextSubject = view.findViewById(R.id.editTextSubject);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);


        return view;
    }
}