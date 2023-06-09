package com.example.educheck.Controleur.Dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.R;

import java.util.ArrayList;


public class CoursesAdapterCard extends RecyclerView.Adapter<CoursesAdapterCard.CoursHolder>{

    private int pos;

    private ArrayList<String> courses;

    public CoursesAdapterCard(ArrayList<String> courses){
        this.courses = courses;
        this.pos=0;
    }
    @NonNull
    @Override
    public CoursHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_courses, parent, false);
        item.setOnClickListener(FragGetCourses.myOnClickListener);
        return new CoursHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursHolder holder, int position) {

        TextView textViewCours_Name= holder.Cours_Name;
        textViewCours_Name.setText(courses.get(position));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    class CoursHolder extends RecyclerView.ViewHolder{
        private TextView Cours_Name;


        CoursHolder(View itemView){
            super(itemView);
            Cours_Name = itemView.findViewById(R.id.Cours_Name);
            itemView.setVerticalScrollbarPosition(pos++);
        }

    }
}
