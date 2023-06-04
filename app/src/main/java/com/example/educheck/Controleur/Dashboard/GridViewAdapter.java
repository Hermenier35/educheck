package com.example.educheck.Controleur.Dashboard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Cellule;
import com.example.educheck.Modele.Date2;
import com.example.educheck.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mJoursSemaineList;
    private List<String> mHorairesList;
    private List<Cellule> mSchedule;


    public GridViewAdapter(Context context, List<String> joursSemaineList, List<String> horairesList, List<Cellule> schedule) {
        mContext = context;
        mJoursSemaineList = joursSemaineList;
        mHorairesList = horairesList;
        mSchedule = schedule;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewAdapter.ViewHolder holder, int position) {
        int jourIndex = position % (mJoursSemaineList.size() + 1); // Index du jour de la semaine (modulo)
        int horaireIndex = position / (mHorairesList.size() + 1);

        // Récupère l'élément à la position donnée
        Object item = getItem(position);

        // Met à jour le texte de la cellule pour afficher heure et les jours
        if (item instanceof String) {
            holder.summary.setText((String) item);
        } else {
            holder.summary.setText("");
        }

        // Réinitialise l'apparence visuelle de la cellule
        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        int horairePos = position / (mJoursSemaineList.size() + 1);
        // Vérifie s'il y a une cellule correspondante dans le planning
        for (Cellule cellule : mSchedule) {
            Date day = getDateFromDate2(cellule.getDate());
            if (horaireIndex != 0 && jourIndex != 0 && mJoursSemaineList.get(jourIndex - 1).equals(getDayOfWeekFromDate(day)) &&
                    betweenHour(mHorairesList.get(horairePos), cellule, holder.itemView)) {
                holder.summary.setText(cellule.getSummary());
                holder.location.setText(cellule.getLocation());
                holder.description.setText(cellule.getDescription());
                holder.itemView.setBackgroundColor(Color.GREEN);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
                if (cellule.getDate().getDuring()/60==2)
                    fusionnerMargesCellulesSuperposees(holder.itemView);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return (mJoursSemaineList.size() + 1) * mHorairesList.size();
    }

    private Object getItem(int position) {
        int jourIndex = position % (mJoursSemaineList.size() + 1);
        int horaireIndex = position / (mJoursSemaineList.size() + 1);

        if (jourIndex == 0)
            return mHorairesList.get(horaireIndex);
        else if (horaireIndex == 0)
            return mJoursSemaineList.get(jourIndex - 1);
        else
            return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView summary;
        private TextView location;
        private TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            summary = itemView.findViewById(R.id.summary);
            location = itemView.findViewById(R.id.location);
            description = itemView.findViewById(R.id.description);
        }
    }

    private boolean betweenHour(String hour, Cellule cellule, View view) {
        return cellule.getStartHour().equals(hour);
    }

    public static Date getDateFromDate2(Date2 date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
        return calendar.getTime();
    }

    private String getDayOfWeekFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(date).substring(0, 3);
    }

    private void fusionnerMargesCellulesSuperposees(View view) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.topMargin = 0; // Ajustez la marge supérieure selon vos besoins pour fusionner les cellules
        layoutParams.bottomMargin = 0;
        view.setLayoutParams(layoutParams);
    }

    private void reinitialiserMargesCellule(View view) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.topMargin = 4; // Ajustez la marge supérieure selon vos besoins pour une cellule individuelle
        layoutParams.bottomMargin = 4; // Ajustez la marge inférieure selon vos besoins pour une cellule individuelle
        view.setLayoutParams(layoutParams);
    }
}
