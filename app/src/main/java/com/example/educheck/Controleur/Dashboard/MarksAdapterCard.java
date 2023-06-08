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

public class MarksAdapterCard extends RecyclerView.Adapter<MarksAdapterCard.MarksHolder>{

    private int pos;

    private ArrayList<Marks> marks;

    public MarksAdapterCard(ArrayList<Marks> marks){
        this.marks=marks;
        this.pos=0;
    }

    @NonNull
    @Override
    public MarksAdapterCard.MarksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_marks, parent, false); //a cambiare
        item.setOnClickListener(FragMarks.myOnClickListener);
        return new MarksAdapterCard.MarksHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MarksAdapterCard.MarksHolder holder, int position) {

        TextView textViewMarks_Name= holder.Marks_Name;
        textViewMarks_Name.setText(""+marks.get(position).getMark());
        TextView textViewSubject_Name= holder.Subject_Name;
        textViewSubject_Name.setText(marks.get(position).getType());
        //TextView textViewDate_Name= holder.Date_Name;
        //textViewDate_Name.setText(marks.get(position).getDate().toString());
        TextView textViewTeacher_Name= holder.Teacher_Name;
        textViewTeacher_Name.setText(marks.get(position).getNameProf());
    }

    @Override
    public int getItemCount() {
        return marks.size();
    }

    class MarksHolder extends RecyclerView.ViewHolder{
        private TextView Marks_Name;

        private TextView Subject_Name;

        private TextView Date_Name;

        private TextView Teacher_Name;


        MarksHolder(View itemView){
            super(itemView);
            Marks_Name = itemView.findViewById(R.id.textMark); // a cambiare
            Subject_Name=itemView.findViewById(R.id.textSubject);
            Date_Name =itemView.findViewById(R.id.textDate);
            Teacher_Name =itemView.findViewById(R.id.textTeacher);

            itemView.setVerticalScrollbarPosition(pos++);
        }

    }


}
