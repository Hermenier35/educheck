package com.example.educheck.Controleur.Dashboard;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Controleur.Registration.UnivAdapterCard;
import com.example.educheck.Controleur.Registration.UniversityInscription;
import com.example.educheck.R;

import java.util.ArrayList;


public class MailAdapterCard extends RecyclerView.Adapter<MailAdapterCard.MailHolder>{

    private int pos;

    private ArrayList<String> mails;

    public MailAdapterCard(ArrayList<String> mails){
        this.mails=mails;
        this.pos=0;
    }
    @NonNull
    @Override
    public MailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_mail, parent, false);
        item.setOnClickListener(FragMessages1.myOnClickListener);
        return new MailAdapterCard.MailHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MailHolder holder, int position) {

        TextView textViewMail_Name= holder.Mail_Name;
        textViewMail_Name.setText(mails.get(position));
     }

    @Override
    public int getItemCount() {
        return mails.size();
    }


    class MailHolder extends RecyclerView.ViewHolder{
        private TextView Mail_Name;


        MailHolder(View itemView){
            super(itemView);
            Mail_Name = itemView.findViewById(R.id.Mail_Name);
            itemView.setVerticalScrollbarPosition(pos++);
        }

    }
}
