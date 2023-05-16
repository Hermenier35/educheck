package com.example.educheck.Controleur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.University;
import com.example.educheck.R;

import java.util.List;

public class UnivAdapter extends RecyclerView.Adapter<UnivAdapter.ViewHolder> {

    private List<University> univs;

    public UnivAdapter(List<University> univs) {
        univs = univs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_univ, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*
        String name = univs.get(position);
        holder.mNameTextView.setText(name);
    */
    }

    @Override
    public int getItemCount() {
        return univs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.Univ_Name);
        }
    }
}
