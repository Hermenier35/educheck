package com.example.educheck.Controleur.Dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Cours;
import com.example.educheck.R;

import java.util.List;

public class CoursAdapter extends RecyclerView.Adapter<CoursAdapter.ViewHolder> {

    private List<Cours> coursList;

    public CoursAdapter(List<Cours> coursList) {
        this.coursList = coursList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_get_cours, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cours cours = coursList.get(position);
        holder.bind(cours);
    }

    @Override
    public int getItemCount() {
        return coursList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView courseNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
        }

        public void bind(Cours course) {
            courseNameTextView.setText(course.getName());
        }
    }
}
