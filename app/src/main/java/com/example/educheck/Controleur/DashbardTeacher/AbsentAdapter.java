package com.example.educheck.Controleur.DashbardTeacher;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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
        public LinearLayout linearLayout;

        public AbsentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewJustify = itemView.findViewById(R.id.textViewBoolJustify);
            linearLayout = itemView.findViewById(R.id.linearabsent);
        }

        public void bind(Justify justify) {
            textViewDate.setText(justify.getDate() + " : " + justify.getNameCours());
            String justif = justify.getJustifie();
            if(justif.equals("False")) {
                linearLayout.setBackgroundColor(Color.parseColor("#FDE1B7"));
                textViewJustify.setText("Unjustify");
            }
            else {
                linearLayout.setBackgroundColor(Color.parseColor("#DAFEA5"));
                textViewJustify.setText("Justify");
            }
        }
    }
}
