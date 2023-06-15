package com.example.educheck.Controleur.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.educheck.Modele.AcademicBackground;
import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Message;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
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

    private RecyclerView recyclerView;

    private static String request;

    private MessageAdapter messageAdapter;

    private ListView messagesView;

    private ArrayList<MessageLayout> users_messages;

    private ArrayList<Integer> index;
    private String idLastMex;
    private static final long REQUEST_DELAY_MS = 5000; // 30 seconds

    private Handler requestHandler;
    private Runnable requestRunnable;
    DashboardImplementation requestMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view =  inflater.inflate(R.layout.fragment__messages, container, false);
        mailSender= getActivity().getIntent().getStringExtra("mail");
        mailRecipient = getArguments().getString("mailRecipient");
        token = getArguments().getString("token");
        messageToSend = view.findViewById(R.id.messageToSend);
        buttonSend = view.findViewById(R.id.buttonSend);

        messageAdapter = new MessageAdapter(getContext());
        messagesView = (ListView) view.findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);



        requestMessage = new DashboardImplementation(this);
        users_messages = new ArrayList<>();
        index= new ArrayList<>();
        sendRequest("getMex");
        requestHandler = new Handler(Looper.getMainLooper());
        requestRunnable = new Runnable() {
            @Override
            public void run() {
                sendRequest("getMex");
                requestHandler.postDelayed(this, REQUEST_DELAY_MS);
            }
        };


        buttonSend.setOnClickListener(v -> sendRequest("sendMex"));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestHandler.postDelayed(requestRunnable, REQUEST_DELAY_MS);
    }

    @Override
    public void onPause() {
        super.onPause();
        requestHandler.removeCallbacks(requestRunnable);
    }


    public void send_message(){
        String text = messageToSend.getText().toString();
        Date date = new Date();
        Instant instant= date.toInstant();

        System.out.println("Current date and time: " + instant);
        System.out.println("Current mailRecipient: " + mailRecipient);
        System.out.println("Current mailSender: " + mailSender);
        System.out.println("Current message: " + text);

        if(text.length() > 0 ) {
            Message mess = new Message(mailRecipient, mailSender, text, date);
            requestMessage.sendMessageTo(mess, token);
            messageToSend.getText().clear();
        }
    }


    public FragMessages2() {
    }

    public static FragMessages2 newInstance(String mailRecipient, String token) {
        FragMessages2 fragment = new FragMessages2();
        Bundle args = new Bundle();
        args.putString("mailRecipient", mailRecipient);
        args.putString("token",token);
        fragment.setArguments(args);
        return fragment;
    }

    private void sendRequest(String name){
        this.request = name;
        switch(name){
            case "sendMex":
                send_message();
                Log.d("TEST", "SendMessage()");
                break;
            case "getMex":
                requestMessage.retrieveMessages(token, mailSender, mailRecipient);
                Log.d("TEST", "getMessage()");
                break;

            case "recMex":
                Log.d("TEST","token: "+token+" idMex: "+idLastMex+" mailRep "+mailRecipient);
                requestMessage.recMex(token,idLastMex,mailRecipient);
            default: Log.d("TEST","No request Found");
        }
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {

        if (items.getJSONObject(0).has("code_retour")) {
            Log.d("TEST","code_retour: " + items.getJSONObject(0).get("code_retour"));
            return;
        }
        Log.d("TEST", items.getJSONObject(0).toString());
        switch(request){
            case "getMex" :
                Log.d("TEST","traitement getMessage()");
                index.clear();
                Log.d("TEST","fini10");
                messageAdapter.delete();
                Log.d("TEST","fini11");
                JSONObject mailJson = items.getJSONObject(0);
                Log.d("TEST","fini12");
                JSONArray messages = mailJson.getJSONArray("messages");
                for(int i = 0; i < messages.length(); i++){

                }
                String[] receivers = mailJson.getString("mailRecipients").replaceAll("[\\[\\]\"\\{\\}]", "").split(",");
                Log.d("TEST","fini13");
                String[] senders = mailJson.getString("mailSenders").replaceAll("[\\[\\]\"\\{\\}]", "").split(",");
                Log.d("TEST","fini14");
                //String[] id = mailJson.getString("ids").replaceAll("[\\[\\]\"\\{\\}]", "").split(",");
                Log.d("TEST","fini15");
                String mex = mailJson.getString("messages").replaceAll("[\\[\\]\"\\{\\}]", "");
                String[] messages = mex.split(",");
                ArrayList<String> messageClear = new ArrayList<>();
                updateIndex(receivers, senders,messageClear, messages);
                updateScreenMessenger(messageClear);
                messagesView.setSelection(messagesView.getCount() - 1);
                Log.d("TEST","fini");
                //sendRequest("recMex");
                break;
            case "sendMex":
                sendRequest("getMex");
                break;

            case "recMex":
                break;
        }
    }

    private void updateIndex(String[] receivers, String [] senders,ArrayList<String> messageClear ,String[] messages){
        for (int i = 0; i < receivers.length; i++) {
            if (receivers[i].equals(mailRecipient) && senders[i].equals(mailSender)) {
                index.add(0);
                messageClear.add(messages[i]);
            } else if(receivers[i].equals(mailSender) && senders[i].equals(mailRecipient)) {
                index.add(1);
                messageClear.add(messages[i]);
            }
        }
       // idLastMex= id[receivers.length-1];
    }

    private void updateScreenMessenger(ArrayList<String> messages){
        MessageLayout message;
        boolean isUser;
        Log.d("TEST", "size message : " + messages.size());
        for (int i = 0; i < index.size(); i++) {
            int pair = index.get(i);
            isUser = pair == 0;
            message = new MessageLayout(messages.get(i), isUser, mailRecipient);
            messageAdapter.add(message);
        }
    }

}