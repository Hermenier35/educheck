package com.example.educheck.Controleur.Dashboard;

import static java.util.Arrays.stream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Controleur.DashbardTeacher.DashBoardTeacher;
import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class FragMessages1 extends Fragment implements AsyncTaskcallback {
    private DashboardImplementation messageImplementation;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter_card;
    private ArrayList<Pair> users_mail;

    private ArrayList<String> mailRec;
    private String token;
    private Fragment parentFragment;
    private FragmentManager fm;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.getGlobal().info("Message intent init");
        View view = inflater.inflate(R.layout.fragment_messages1, container, false);
        layoutManager = new LinearLayoutManager(getContext());
        users_mail = new ArrayList<>();
        mailRec=new ArrayList<>();
        token = getActivity().getIntent().getStringExtra("token");
        myOnClickListener = new FragMessages1.MyOnClickListener(getContext(), recyclerView);

        recyclerView = view.findViewById(R.id.recycler_mex);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        messageImplementation = new DashboardImplementation(this);
        messageImplementation.sendMexTo(token);
        parentFragment = getActivity().getSupportFragmentManager().getFragments().get(0);
        System.out.println(parentFragment.getId());

        adapter_card = new MailAdapterCard(users_mail);
        recyclerView.setAdapter(adapter_card);
        System.out.println("INITIALISATION MESSENGER");
        return view;
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        if (items.getJSONObject(0).has("code_retour"))
            System.out.println("code_retour: " + items.getJSONObject(0).get("code_retour") + " retour users-mails");
        JSONObject mailJson = items.getJSONObject(0);
        JSONObject receiveJson= items.getJSONObject(1);
        String mail = mailJson.getString("mail").replaceAll("[\\[\\]\" ]" , "");
        String receive= receiveJson.getString("received").replaceAll("[\\[\\]\" ]" , "");
        String[] rec= receive.split(",");
        System.out.println(receive);
        String[] mails= mail.split(",");
        for(int i=0;i<rec.length;i++){
            mailRec.add(rec[i]);
        }

        for (int j=0;j<mails.length;j++) {
            if(!mails[j].equals(users_mail)&&!mails[j].equals("")) {
                System.out.println(mails[j]);
                if(mailRec.contains(mails[j])) {
                    users_mail.add(new Pair(mails[j], "true"));
                }else{
                    users_mail.add(new Pair(mails[j], "false"));
                }
            }
        }

        System.out.println(users_mail.size()+ " : " + users_mail.stream().map(s -> s.toString()));
        if(!users_mail.isEmpty()) {
            adapter_card = new MailAdapterCard(users_mail);
            recyclerView.setAdapter(adapter_card);
        }
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
            String mailRecipient = users_mail.get(v.getVerticalScrollbarPosition()).getMail();
            Fragment fragMessages2 = FragMessages2.newInstance(mailRecipient,token);
            if(parentFragment.getId()!=0)
                replaceFragment(fragMessages2);
            else
                DashBoardTeacher.replaceMessengerFragment(fragMessages2);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(parentFragment.getId(), fragment);
        fragmentTransaction.commit();
    }

}
