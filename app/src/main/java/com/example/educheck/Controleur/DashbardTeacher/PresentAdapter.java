package com.example.educheck.Controleur.DashbardTeacher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Student;
import com.example.educheck.R;

import java.util.List;

public class PresentAdapter extends RecyclerView.Adapter<PresentAdapter.StudentViewHolder> {

    private List<Student> students;

    public PresentAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_precence, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            //textViewName = itemView.findViewById(R.id.textViewName);
        }

        public void bind(Student student) {
            //textViewName.setText(Student.getName());
        }
    }
}
