package com.example.educheck.Controleur.DashbardTeacher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Justify;
import com.example.educheck.Modele.Student;
import com.example.educheck.R;

import java.util.ArrayList;
import java.util.List;

public class AbsentAdapter extends RecyclerView.Adapter<AbsentAdapter.AbsentViewHolder>{

    private ArrayList<Justify> absences;
    private int position;
    private ArrayList<AbsentViewHolder> viewHolders;

    public AbsentAdapter(ArrayList<Justify> absences) {
        this.absences = absences;
        this.position =0;
        this.viewHolders = new ArrayList<>();
    }

    @NonNull
    @Override
    public AbsentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_absent, parent, false);
        AbsentViewHolder absentViewHolder = new AbsentViewHolder(view);
        return absentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AbsentViewHolder holder, int position) {
        Justify justify = absences.get(position);
        holder.bind(justify);
        viewHolders.add(holder);
    }

    @Override
    public int getItemCount() {
        return absences.size();
    }

    public class AbsentViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDate, textViewJustify;

        public AbsentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewJustify = itemView.findViewById(R.id.textViewBoolJustify);
        }

        public void bind(Justify justify) {
            textViewDate.setText(justify.getDate() + " : " + justify.getNameCours());
            textViewJustify.setText(justify.getJustifie());
        }
    }
}
