package com.example.educheck.Controleur.Dashboard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.educheck.Modele.Cellule;
import com.example.educheck.Modele.Date2;
import com.example.educheck.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleAdapter extends BaseAdapter {
    private Context context;
    private List<String> joursSemaineList;
    private List<String> horairesList;
    private List<Cellule> schedule;

    public ScheduleAdapter(Context context, List<String> joursSemaineList, List<String> horairesList, List<Cellule> schedule) {
        this.context = context;
        this.joursSemaineList = joursSemaineList;
        this.horairesList = horairesList;
        this.schedule = schedule;
    }

    @Override
    public int getCount() {
        return (joursSemaineList.size() + 1) * horairesList.size();
    }

    @Override
    public Object getItem(int position) {
        int jourIndex = position % (joursSemaineList.size() + 1);
        int horaireIndex = position / (joursSemaineList.size() + 1);

        if (jourIndex == 0)
            return horairesList.get(horaireIndex);
        else if (horaireIndex==0)
            return joursSemaineList.get(jourIndex - 1);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        int jourIndex = position % (joursSemaineList.size() + 1); // Index du jour de la semaine (modulo)
        int horaireIndex = position / (horairesList.size() + 1);

        if (convertView == null) {
            // Crée une nouvelle vue si elle n'existe pas déjà
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.summary = convertView.findViewById(R.id.summary);
            viewHolder.location = convertView.findViewById(R.id.location);
            viewHolder.description = convertView.findViewById(R.id.description);
            convertView.setTag(viewHolder);
        } else {
            // Réutilise la vue existante
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Récupère l'élément à la position donnée
        Object item = getItem(position);
        //System.out.println("position : " + jourIndex);
        for(int i = 0; i<schedule.size(); i++ ) {
            Date day = getDateFromDate2(schedule.get(i).getDate());
            if (horaireIndex!=0 && jourIndex != 0 && joursSemaineList.get(jourIndex - 1).equals(getDayOfWeekFromDate(day))) {
                System.out.println(getDayOfWeekFromDate(day));
                viewHolder.summary.setText(schedule.get(i).getSummary());
                System.out.println(horairesList.get(horaireIndex - 1));
                viewHolder.location.setText(schedule.get(i).getLocation());
                viewHolder.description.setText(schedule.get(i).getDescription());
                convertView.setBackgroundColor(Color.GREEN);
            }
        }

        // Met à jour le texte de la cellule pour afficher heure et les jours
        if (item instanceof String) {
            viewHolder.summary.setText((String) item);
        } else {
            viewHolder.summary.setText("");
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView summary;
        TextView location;
        TextView description;
    }

    private boolean betweenHour(String hour, Cellule cellule){
        System.out.println("hour : " + hour + " cellule : " + cellule.getEndHour());
        return cellule.getStartHour().equals(hour) || cellule.getEndHour().equals(hour);
    }

    private Date getDateFromDate2(Date2 date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
        return calendar.getTime();
    }

    private int getWeekNumberDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    private String getDayOfWeekFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(date).substring(0,3);
    }
}
