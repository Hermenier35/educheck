package com.example.educheck.Controleur.DashbardTeacher;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.educheck.Controleur.Dashboard.FragMessages1;
import com.example.educheck.Controleur.Dashboard.FragMessages2;
import com.example.educheck.R;

public class FragConfirmationStudent extends Fragment {

    public static View.OnClickListener myOnClickListener;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myOnClickListener = new FragConfirmationStudent.MyOnClickListener(getContext(), recyclerView);

        return inflater.inflate(R.layout.fragment_confirmation_student, container, false);
    }

    private class MyOnClickListener implements View.OnClickListener {
        private Context context;
        private RecyclerView recyclerView;

        public MyOnClickListener(Context context, RecyclerView recyclerView) {
            this.context = context;
            this.recyclerView = recyclerView;
        }

        @Override
        public void onClick(View v) {

        }
    }
}