package com.example.educheck.Controleur.Dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Controleur.Registration.RecyclerAdapter;
import com.example.educheck.Modele.AcademicBackground;
import com.example.educheck.Modele.Cellule;
import com.example.educheck.Modele.Date2;
import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Schedule extends Fragment implements AsyncTaskcallback {
    public static final String[] joursSemaine = {"Day:", "Mon", "Tue", "Wed", "Thu", "Fri"};
    public static final String[] horaires = {
            "08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00",
            "18:00", "19:00", "20:00"
    };
    public static String GREEN = "2FFC4B", YELLOW = "E9FC2F", RED = "FC4D44", BLUE = "44F6FC", PINK = "FE75D0", ORANGE = "FABD42",
                CIEL="72FBF1", PURPLE= "C685F9";
    private GridLayout gridLayout;
    private ArrayList<Cellule> schedule;
    private ArrayList<Cellule> filter;
    private View view;

    private Spinner spinYears, avg;
    private ArrayList<String> dataParcours;
    private LinearLayout LinearLayoutView;
    private int weekNumber;
    private int year;
    private Button seek, go;
    private DashboardImplementation dashboardImplementation;
    private TextView week, date;
    private EditText ade;
    private String request;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        year=0;
        weekNumber=0;
        view = inflater.inflate(R.layout.fragment_schedule, container, false);

        gridLayout = view.findViewById(R.id.grid_layout);
        week = view.findViewById(R.id.week);
        date = view.findViewById(R.id.date);
        go = view.findViewById(R.id.btnAde);
        ade = view.findViewById(R.id.adeUrl);
        seek = view.findViewById(R.id.seek);
        avg = view.findViewById(R.id.avg);

        ade.setText("https://planning.univ-rennes1.fr/jsp/custom/modules/plannings/rY6dlyWz.shu");
        LinearLayoutView = view.findViewById(R.id.spinner_container);
        spinYears = view.findViewById(R.id.spinnerYears);
        schedule = new ArrayList<>();
        filter = new ArrayList<>();
        dashboardImplementation = new DashboardImplementation(this);
        miseAJourDeLaPendule();
        for(int i=0;i<51;i++){
            TextView textView = new TextView(getContext());
            textView.setText("week " + (i+1) + "  ");
            textView.setOnClickListener(v -> saveParamSchedule(textView.getText().toString()));
            LinearLayoutView.addView(textView);
        }
        List<String> joursSemaineList = new ArrayList<>(Arrays.asList(joursSemaine));
        List<String> horairesList = new ArrayList<>(Arrays.asList(horaires));

        System.out.println(Calendar.getInstance().getTime());
        System.out.println(Calendar.getInstance().get(Calendar.YEAR) + Calendar.getInstance().get(Calendar.MONTH) +
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        System.out.println(Calendar.getInstance().get(Calendar.DATE));
        dataParcours = new ArrayList<>();
        dataParcours.add(Calendar.getInstance().get(Calendar.YEAR)+"");
        dataParcours.add(Calendar.getInstance().get(Calendar.YEAR)-1+"");
        dataParcours.add(Calendar.getInstance().get(Calendar.YEAR)+1+"");
        ArrayAdapter<String> adapterDataParcour = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataParcours);
        adapterDataParcour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinYears.setAdapter(adapterDataParcour);
        seek.setOnClickListener(v -> updateSchedule());
        go.setOnClickListener(v -> sendRequest("getSchedule"));

        createPlanningCells();
        return view;

    }
    private void saveParamSchedule(String week){
        String dataWeek[] = week.split(" ");
        this.weekNumber = Integer.parseInt(dataWeek[1]);
        this.week.setText("week " + weekNumber);
        this.year = Integer.parseInt(spinYears.getSelectedItem().toString());
    }

    private void updateSchedule(){
        filter.clear();
        updateAvg();
        filter.addAll(schedule);
        if( year!=0 && weekNumber !=0 ){
            List<String> joursSemaineList = new ArrayList<>(Arrays.asList(joursSemaine));
            List<String> horairesList = new ArrayList<>(Arrays.asList(horaires));
            filter.removeIf(cellule-> {
                return cellule.getDate().getYear()!=this.year ||
                        getWeekNumberDate(getDateFromDate2(cellule.getDate()))!= this.weekNumber;});
            createPlanningCells();
        }
        avg.setSelection(0);
    }

    private void updateAvg(){
        for(Cellule cellule : schedule){
            cellule.setAverageHour(Integer.parseInt(avg.getSelectedItem().toString()));
        }
    }

    private void createPlanningCells() {
        int rowCount = horaires.length * 4;
        int columnCount = joursSemaine.length;
        gridLayout.removeAllViews();
        gridLayout.setRowCount(rowCount);
        gridLayout.setColumnCount(columnCount);
       for(int row = 0; row < rowCount; row++){
           for (int column = 0; column < columnCount; column++){
               if (row==0){
                   gridLayout.addView(createCellTextView(joursSemaine[column]));
               }else if(row>0 && column==0 && (row-1)%4==0){
                   gridLayout.addView(createCellTextView(horaires[(row)/4]));
               }else if(row> 0 && column==0) {
                   gridLayout.addView(createCellTextView(""));
               }else{
                   Cellule c = addCellule(row, column, columnCount);
                   if(c!=null)
                       addGridItemView(textToWrite(c,row, column), c.getColor());
                   else
                       gridLayout.addView(createCellTextView(""));
               }

           }
       }
    }

    private TextView createCellTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(12);
        textView.setHeight(50);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.WHITE);
        textView.setPadding(50, 0, 50, 0);
        return textView;
    }

    private void addGridItemView(String text, int color) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setWidth(200);
        textView.setHeight(70);
        textView.setTextSize(12);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(color);
        textView.setPadding(10, 15, 15, 10);
        gridLayout.addView(textView);
    }

    private Cellule addCellule(int row, int column, int columnCount){
        int position = row * columnCount + column;
        int jourIndex = column;
        int horaireIndex = position / (horaires.length + 1);
        for (Cellule cellule : filter) {
            Date day = getDateFromDate2(cellule.getDate());
            if (horaireIndex != 0 && jourIndex != 0 && joursSemaine[jourIndex].equals(getDayOfWeekFromDate(day)) &&
                    betweenTimeSlot(row,column, cellule)) {
                System.out.println(cellule.getSummary());
                return cellule;
            }
        }
        return null;
    }

    public static Date getDateFromDate2(Date2 date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
        return calendar.getTime();
    }

    private String getDayOfWeekFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        return sdf.format(date).substring(0, 3);
    }

    private boolean betweenTimeSlot(int row, int column, Cellule cellule) {
        int position = row * 6 + column;
        //calcul du creneau horaire
        int heure = (position-6) / 24;
        int h = heure+8;
        int minute =((row-1) % 4) * 15 ;
        Date2 cel = cellule.getDate();
        int HeureFin = cel.getHours() + cel.getDuring()/60;
        int minuteFin = ((cel.getDuring() % 60) + cel.getMinutes()) % 60;
        HeureFin+= ((cel.getDuring() % 60) + cel.getMinutes())/60;
        return (h>cel.getHours() || h==cel.getHours() && minute>=cel.getMinutes()) &&
               HeureFin>h || HeureFin==h && minuteFin>= minute ;
    }

    private String textToWrite(Cellule cellule, int row, int column){
        int nbrCellules = cellule.getDate().getDuring()/15;
        int espaceDebut = (nbrCellules - 4) / 2;
        int position = row * 6 + column;
        int totalMinutes = ((position-6) / 24 + 8) * 60 + (((row-1) % 4) * 15);
        int totalMinutesCel = cellule.getDate().getHours() * 60 + cellule.getDate().getMinutes();
        int diffMinutes = totalMinutes - totalMinutesCel;
        int numeroCel = diffMinutes/15;
        switch (numeroCel - espaceDebut){
            case 1: return cellule.getSummary();
            case 2: return cellule.getLocation();
            case 3: return cellule.getStartHour();
            case 4: return cellule.getEndHour();
            default:return "";
        }
    }

    @Override
    public void onTaskCompleted(JSONArray items){

        switch (request){
            case "getSchedule":
                schedule.clear();
                for(int i=0; i< items.length(); i++){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = items.getJSONObject(i);
                        String dtStart = jsonObject.getString("DTSTART");
                        String dtEnd = jsonObject.getString("DTEND");
                        Cellule cellule = new Cellule(jsonObject.getString("SUMMARY"), jsonObject.getString("LOCATION"), cleanHour(dtStart),
                                cleanHour(dtEnd),getDateFromData(dtStart, getDuringFromData(dtStart,dtEnd)), Color.parseColor("#"+randomColor()) );
                        schedule.add(cellule);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getContext(), "Schedule update !", Toast.LENGTH_SHORT).show();
                break;
            case "update":
                updateSchedule();
                break;
        }
    }

    private void sendRequest(String name ){
        request = name;
        switch (request){
            case "getSchedule":
                dashboardImplementation.getSchedule(ade.getText().toString());
                break;
            case "update":
                onTaskCompleted(null);
                break;
        }
    }

    private String cleanHour(String eventHour){
        return eventHour.substring(eventHour.length()-7, eventHour.length()-5) + " : " +
                eventHour.substring(eventHour.length()-5, eventHour.length()-3);
    }

    private Date2 getDateFromData(String eventDate, int during){
        int hour = Integer.parseInt(eventDate.substring(eventDate.length()-7, eventDate.length()-5));
        int minute = Integer.parseInt(eventDate.substring(eventDate.length()-5, eventDate.length()-3));
        int d = Integer.parseInt(eventDate.substring(0, 8));
        return new Date2(hour, minute,d,during);
    }

    private int getDuringFromData(String dtStart, String dtEnd){
        int sHour = Integer.parseInt(dtStart.substring(dtStart.length()-7, dtStart.length()-5));
        int sMinute = Integer.parseInt(dtStart.substring(dtStart.length()-5, dtStart.length()-3));
        int eHour = Integer.parseInt(dtEnd.substring(dtEnd.length()-7, dtEnd.length()-5));
        int eMinute = Integer.parseInt(dtEnd.substring(dtEnd.length()-5, dtEnd.length()-3));
        return (eHour - sHour) * 60 + (eMinute - sMinute);
    }

    private String randomColor(){
        String colors[] = {GREEN,YELLOW,RED, BLUE, PINK, ORANGE, CIEL, PURPLE};
        Random r = new Random();
        return colors[r.nextInt(8)];
    }

    private void miseAJourDeLaPendule(){
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  'week :' ww");
        String formattedDate = sdf.format(instance.getTime());

        this.date.setText(formattedDate);
    }
    private int getWeekNumberDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
}
