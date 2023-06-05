package com.example.educheck.Controleur.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

    private String request;

    private MessageAdapter messageAdapter;

    private ListView messagesView;

    private ArrayList<MessageLayout> users_messages;

    private ArrayList<int[]> index;

    Calendar calendar = Calendar.getInstance();
    DashboardImplementation model_message;
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



        model_message = new DashboardImplementation(this);
        users_messages = new ArrayList<>();
        index=new ArrayList<>();
        sendRequest();
        buttonSend.setOnClickListener(v -> send_message());

        return view;
    }

    public void send_message(){

        String text = messageToSend.getText().toString();
       Date date=new Date();


       Instant instant= date.toInstant();

        System.out.println("Current date and time: " + instant);
        System.out.println("Current mailRecipient: " + mailRecipient);
        System.out.println("Current mailSender: " + mailSender);
        System.out.println("Current message: " + text);


        Message mess=new Message(mailRecipient,mailSender,text,date);
        model_message.sendMessageTo(mess,token);
        messageToSend.getText().clear();
        sendRequest();
    }

    public static FragMessages2 newInstance(String mailRecipient,String token) {
        FragMessages2 fragment = new FragMessages2();
        Bundle args = new Bundle();
        args.putString("mailRecipient", mailRecipient);
        args.putString("token",token);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        if (items.getJSONObject(0).has("code_retour"))
            System.out.println("code_retour: " + items.getJSONObject(0).get("code_retour"));
        JSONObject mailJson = items.getJSONObject(0);
        String mailRe = mailJson.getString("mailRecipients").replaceAll("[\\[\\]\"\\{\\}]" , "");
        String mailSe = mailJson.getString("mailSenders").replaceAll("[\\[\\]\"\\{\\}]" , "");
        System.out.println(mailRe);
        String[] receivers=mailRe.split(",");
        String[] senders= mailSe.split(",");
        for(int i=0;i<receivers.length;i++){
            if(receivers[i].equals(mailRecipient)&&senders[i].equals(mailSender)) {
                index.add(new int[]{i,0});
            }else if(receivers[i].equals(mailSender)&&senders[i].equals(mailRecipient)){

                index.add(new int[]{i,1});
            }
        }
        String mex = mailJson.getString("messages").replaceAll("[\\[\\]\"\\{\\}]" , "");
        String[] messages= mex.split(",");

        MessageLayout message;
        boolean isUser;
        messageAdapter.delete();
        for (int j=0;j<index.size();j++) {
            int[] pair= index.get(j);
            isUser= pair[1]==0;
            message=new MessageLayout(messages[pair[0]],isUser,mailSender);
            messageAdapter.add(message);
        }
        messagesView.setSelection(messagesView.getCount() - 1);

    }

    private void sendRequest(){

            model_message.retrieveMessages(token);
    }

    }