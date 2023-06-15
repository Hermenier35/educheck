package com.example.educheck.Controleur.Dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Controleur.Dashboard.FragMessages1;
import com.example.educheck.Controleur.Dashboard.Pair;
import com.example.educheck.R;

import java.util.ArrayList;


public class MailAdapterCard extends RecyclerView.Adapter<MailAdapterCard.MailHolder> {

    private int pos;
    private ArrayList<Pair> mailPairs;

    public MailAdapterCard(ArrayList<Pair> mailPairs) {
        this.mailPairs = mailPairs;
        this.pos = 0;
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
        TextView textViewMail_Name = holder.Mail_Name;
        textViewMail_Name.setText(mailPairs.get(position).getMail());
        // You can also access the int value like this:
        String received = mailPairs.get(position).getReceived();
        // Use the intValue as needed in your code
        ImageView circle = holder.circle;
        if (received.equals("true")) {
            circle.setVisibility(View.VISIBLE);
        } else {
            circle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mailPairs.size();
    }

    class MailHolder extends RecyclerView.ViewHolder {
        private TextView Mail_Name;

        private ImageView circle;

        MailHolder(View itemView) {
            super(itemView);
            Mail_Name = itemView.findViewById(R.id.Mail_Name);
            circle=itemView.findViewById(R.id.circle);
            itemView.setVerticalScrollbarPosition(pos++);
        }
    }

}
