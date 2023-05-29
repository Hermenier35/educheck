package com.example.educheck.Controleur.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.educheck.Modele.Cellule;
import com.example.educheck.Modele.Date2;
import com.example.educheck.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Schedule extends Fragment {
    private static final String[] joursSemaine = {"Mon", "Tue", "Wed", "Thu", "Fri"};
    private static final String[] horaires = {"Day:",
            "08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00",
            "18:00", "19:00", "20:00"
    };
    private GridView gridView;
    private ScheduleAdapter agendaAdapter;
    private ArrayList<Cellule> schedule;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        gridView = view.findViewById(R.id.grid_view);
        schedule = new ArrayList<>();

        List<String> joursSemaineList = new ArrayList<>(Arrays.asList(joursSemaine));
        List<String> horairesList = new ArrayList<>(Arrays.asList(horaires));
        Date2 date = new Date2(14,15,20230529, 120);
        Cellule day1 = new Cellule("ALG", "B02B - E103", "DERBEZ Patrick", "13:00", "15:00", date);
        schedule.add(day1);
        System.out.println(Calendar.getInstance().getTime());
        System.out.println(Calendar.getInstance().get(Calendar.YEAR) + Calendar.getInstance().get(Calendar.MONTH) +
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        System.out.println(Calendar.getInstance().get(Calendar.DATE));

        agendaAdapter = new ScheduleAdapter(getContext(), joursSemaineList, horairesList, schedule);
        gridView.setAdapter(agendaAdapter);
        return view;
    }
}
