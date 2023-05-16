package com.example.educheck.Controleur.Registration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.University;
import com.example.educheck.R;

import java.util.ArrayList;

public class univ_adapter_card extends RecyclerView.Adapter<univ_adapter_card.UniversityHolder> {

    private Context context;
    private ArrayList<University> universities;

    public univ_adapter_card(Context context, ArrayList<University> universities) {
        this.context = context;
        this.universities = universities;
    }

    @NonNull
    @Override
    public UniversityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.activity_card_univ,parent,false);
        return new UniversityHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityHolder holder, int position) {
        University university = universities.get(position);
        holder.setUniv_Name(university.getName());

    }

    @Override
    public int getItemCount() {
        return universities.size();
    }


    class UniversityHolder extends RecyclerView.ViewHolder{
        private TextView Univ_Name;



        UniversityHolder(View itemView){
            super(itemView);
            Univ_Name = itemView.findViewById(R.id.Univ_Name);


        }
        void setUniv_Name(String name){
            Univ_Name.setText(name);
        }

    }



}
