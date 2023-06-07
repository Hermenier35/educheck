package com.example.educheck.Controleur.DashbardTeacher;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Controleur.Registration.UniversityInscription;
import com.example.educheck.Modele.Student;
import com.example.educheck.R;
import java.util.ArrayList;


public class ConfStudentAdapterCard extends RecyclerView.Adapter<ConfStudentAdapterCard.ConfiStudentHolder> {

    private ArrayList<Student> confiStudent;
    private int pos;


    public ConfStudentAdapterCard(ArrayList<Student> confiStudent) {
        this.confiStudent = confiStudent;
        this.pos=0;
    }

    @NonNull
    @Override
    public ConfiStudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_confi_student, parent, false);
        item.setOnClickListener(FragConfirmationStudent.myOnClickListener);
        return new ConfiStudentHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfStudentAdapterCard.ConfiStudentHolder holder, int position) {
        TextView textViewStudent_Name= holder.studentName;
        textViewStudent_Name.setText(confiStudent.get(position).getLastName() + " " + confiStudent.get(position).getFirstName());
       
    }
    @Override
    public int getItemCount() {
        return confiStudent.size();
    }

    class ConfiStudentHolder extends RecyclerView.ViewHolder{
        private TextView studentName;
        private ImageView imageView;
        
        
        ConfiStudentHolder(View itemView){
            super(itemView);
            studentName = itemView.findViewById(R.id.Student_Name);
            itemView.setVerticalScrollbarPosition(pos++);
        }
    }
    
}

