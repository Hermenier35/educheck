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

import com.example.educheck.Modele.Student;
import com.example.educheck.R;

import java.util.ArrayList;
import java.util.List;

public class PresentAdapter extends RecyclerView.Adapter<PresentAdapter.StudentViewHolder> {

    private List<Student> students;
    private int position;
    private ArrayList<StudentViewHolder> viewHolders;

    public PresentAdapter(List<Student> students) {
        this.students = students;
        this.position =0;
        this.viewHolders = new ArrayList<>();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_present, parent, false);
        StudentViewHolder studentViewHolder = new StudentViewHolder(view);
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student);
        viewHolders.add(holder);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName, textViewMail;
        public Button detail;
        public ScrollView scrollView;
        public CheckBox checkBox;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewMail = itemView.findViewById(R.id.textViewMail);
            detail = itemView.findViewById(R.id.btnDetail);
            scrollView = itemView.findViewById(R.id.scrollViewDetail);
            checkBox = itemView.findViewById(R.id.checkBox1);
        }

        public void bind(Student student) {
            textViewName.setText(student.getFirstName() + " " + student.getLastName());
            textViewMail.setText(student.getMail());
            checkBox.setChecked(checkBox.isChecked());
            itemView.setVerticalScrollbarPosition(position++);
            initListenerButtonDetail();
        }

        private void initListenerButtonDetail(){
                detail.setOnClickListener(v -> showOrHideDetail());
        }

        private void showOrHideDetail(){
            if (this.scrollView.getVisibility()==View.GONE) {
                this.scrollView.setVisibility(View.VISIBLE);
                this.detail.setText("Hide");
            }
            else {
                this.scrollView.setVisibility(View.GONE);
                this.detail.setText("Details");
            }
        }
    }

    public ArrayList<StudentViewHolder> getViewHolders() {
        return this.viewHolders;
    }
}
