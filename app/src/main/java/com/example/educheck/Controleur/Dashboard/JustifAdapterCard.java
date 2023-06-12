package com.example.educheck.Controleur.Dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Marks;
import com.example.educheck.R;

import java.util.ArrayList;

public class JustifAdapterCard extends RecyclerView.Adapter<JustifAdapterCard.JustHolder>{
    private int pos;

    private ArrayList<Justificatif> jfs;
    public JustifAdapterCard(ArrayList<Justificatif> jfs){
        this.jfs=jfs;
        this.pos=0;
    }

    @NonNull
    @Override
    public JustifAdapterCard.JustHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_justif, parent, false); //a cambiare
        item.setOnClickListener(FragJustification1.myOnClickListener);
        return new JustifAdapterCard.JustHolder(item);
    }

    public void onBindViewHolder(@NonNull JustifAdapterCard.JustHolder holder, int position) {

        TextView textViewSubject_Name= holder.Subject_Name;
        textViewSubject_Name.setText(jfs.get(position).getNameCours());
        TextView textViewDate_Name= holder.Date_Name;
        textViewDate_Name.setText(jfs.get(position).getDate());
        TextView textViewTeacher_Name= holder.Teacher_Name;
        textViewTeacher_Name.setText(jfs.get(position).getProfName());
    }

    @Override
    public int getItemCount() {
        return jfs.size();
    }

    class JustHolder extends RecyclerView.ViewHolder{

        private TextView Subject_Name;

        private TextView Date_Name;

        private TextView Teacher_Name;


        JustHolder(View itemView){
            super(itemView);
            Subject_Name=itemView.findViewById(R.id.textSubject);
            Date_Name =itemView.findViewById(R.id.textDate);
            Teacher_Name =itemView.findViewById(R.id.textTeacher);

            itemView.setVerticalScrollbarPosition(pos++);
        }

    }

}
