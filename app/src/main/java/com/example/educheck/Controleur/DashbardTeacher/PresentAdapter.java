package com.example.educheck.Controleur.DashbardTeacher;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Interface.ButtonListenerCallBack;
import com.example.educheck.Modele.Student;
import com.example.educheck.R;

import java.util.ArrayList;
import java.util.List;

public class PresentAdapter extends RecyclerView.Adapter<PresentAdapter.StudentViewHolder> {

    private List<Student> students;
    private int position;
    private ArrayList<StudentViewHolder> viewHolders;
    private Context context;
    private ButtonListenerCallBack buttonListenerCallBack;

    public PresentAdapter(List<Student> students, Context context, ButtonListenerCallBack buttonListenerCallBack) {
        this.students = students;
        this.position =0;
        this.viewHolders = new ArrayList<>();
        this.context = context;
        this.buttonListenerCallBack = buttonListenerCallBack;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_present, parent, false);
        StudentViewHolder studentViewHolder = new StudentViewHolder(view, this.context, this.buttonListenerCallBack);
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

    public class StudentViewHolder extends RecyclerView.ViewHolder implements ButtonListenerCallBack{

        public TextView textViewName, textViewMail;
        public ImageView imageView;
        public ImageView detail;
        public ScrollView scrollView;
        public CheckBox checkBox;
        private RecyclerView recyclerView;
        private AbsentAdapter absentAdapter;
        private RecyclerView.LayoutManager layoutManager;
        private Context context;
        private ButtonListenerCallBack buttonListenerCallBack;
        private Student student;

        public StudentViewHolder(@NonNull View itemView, Context context, ButtonListenerCallBack buttonListenerCallBack) {
            super(itemView);
            this.context = context;
            this.buttonListenerCallBack = buttonListenerCallBack;
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewMail = itemView.findViewById(R.id.textViewMail);
            detail = itemView.findViewById(R.id.btnDetail);
            scrollView = itemView.findViewById(R.id.scrollViewDetail);
            checkBox = itemView.findViewById(R.id.checkBox1);
            recyclerView = itemView.findViewById(R.id.listViewAbs);
            imageView = itemView.findViewById(R.id.circleStatus);
        }

        public void bind(Student student) {
            this.student = student;
            textViewName.setText(student.getFirstName() + " " + student.getLastName());
            textViewMail.setText(student.getMail());
            checkBox.setChecked(checkBox.isChecked());
            itemView.setVerticalScrollbarPosition(position++);
            this.imageView.setVisibility(View.GONE);
            initListenerButtonDetail();
            initViewAbs(student);
        }

        private void initListenerButtonDetail(){
                detail.setOnClickListener(v -> showOrHideDetail());
        }

        private void initViewAbs(Student student){
            layoutManager = new LinearLayoutManager(this.context);
            absentAdapter = new AbsentAdapter(student.getJustifies(), this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(absentAdapter);
        }

        private void showOrHideDetail(){
            if (this.scrollView.getVisibility()==View.GONE) {
                ViewGroup.LayoutParams params = scrollView.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                this.scrollView.setVisibility(View.VISIBLE);
                this.detail.setImageResource(R.drawable.arrow_drop_up);
            }
            else {
                this.scrollView.setVisibility(View.GONE);
                this.detail.setImageResource(R.drawable.arrow_drop_down);
                this.detail.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void callBackListener(String request) {
            if(request.startsWith("accept")) {
                request = request + " " + student.getMail();
                this.buttonListenerCallBack.callBackListener(request);
            }
            else if(request.startsWith("openFile")){
                this.buttonListenerCallBack.callBackListener(request);
            }
            else if(request.startsWith("circleStatus")){
                String data[] = request.split(" ");
                String value = data[1];
                initSatus(value);
            }
        }

        private void initSatus(String value){
            if(value.equals("True"))
                this.imageView.setVisibility(View.VISIBLE);

            this.scrollView.setVisibility(View.GONE);
        }
    }

    public ArrayList<StudentViewHolder> getViewHolders() {
        return this.viewHolders;
    }
}
