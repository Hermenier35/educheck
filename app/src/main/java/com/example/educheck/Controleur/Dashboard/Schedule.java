package com.example.educheck.Controleur.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Controleur.Registration.RecyclerAdapter;
import com.example.educheck.Modele.AcademicBackground;
import com.example.educheck.Modele.Cellule;
import com.example.educheck.Modele.Date2;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Schedule extends Fragment {
    public static final String[] joursSemaine = {"Mon", "Tue", "Wed", "Thu", "Fri"};
    public static final String[] horaires = {"Day:",
            "08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00",
            "18:00", "19:00", "20:00"
    };
    private GridView gridView;
    private ScheduleAdapter agendaAdapter;
    private ArrayList<Cellule> schedule;
    private ArrayList<Cellule> filter;
    private View view;

    private Spinner spinYears;
    private ArrayList<String> dataParcours;
    private LinearLayout LinearLayoutView;
    private int weekNumber;
    private int year;
    private Button seek;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        year=0;
        weekNumber=0;
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        gridView = view.findViewById(R.id.grid_view);
        seek = view.findViewById(R.id.seek);
        LinearLayoutView = view.findViewById(R.id.spinner_container);
        spinYears = view.findViewById(R.id.spinnerYears);
        schedule = new ArrayList<>();
        filter = new ArrayList<>();

        for(int i=0;i<51;i++){
            TextView textView = new TextView(getContext());
            textView.setText("week " + (i+1) + "  ");
            textView.setOnClickListener(v -> saveParamSchedule(textView.getText().toString()));
            LinearLayoutView.addView(textView);
        }

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

        dataParcours = new ArrayList<>();
        dataParcours.add(Calendar.getInstance().get(Calendar.YEAR)+"");
        dataParcours.add(Calendar.getInstance().get(Calendar.YEAR)-1+"");
        dataParcours.add(Calendar.getInstance().get(Calendar.YEAR)+1+"");
        ArrayAdapter<String> adapterDataParcour = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataParcours);
        adapterDataParcour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinYears.setAdapter(adapterDataParcour);
        seek.setOnClickListener(v -> updateSchedule());


        return view;

    }
    private void saveParamSchedule(String week){
        String dataWeek[] = week.split(" ");
        this.weekNumber = Integer.parseInt(dataWeek[1]);
        this.year = Integer.parseInt(spinYears.getSelectedItem().toString());
    }

    private void updateSchedule(){
        filter.clear();
        filter.addAll(schedule);
        if( year!=0 && weekNumber !=0 ){
            List<String> joursSemaineList = new ArrayList<>(Arrays.asList(joursSemaine));
            List<String> horairesList = new ArrayList<>(Arrays.asList(horaires));
            filter.removeIf(cellule-> {
                return cellule.getDate().getYear()!=this.year ||
                    ScheduleAdapter.getWeekNumberDate(ScheduleAdapter.getDateFromDate2(cellule.getDate()))!= this.weekNumber;});
            agendaAdapter = new ScheduleAdapter(getContext(), joursSemaineList, horairesList, filter);
            gridView.setAdapter(agendaAdapter);
        }

    }

}
