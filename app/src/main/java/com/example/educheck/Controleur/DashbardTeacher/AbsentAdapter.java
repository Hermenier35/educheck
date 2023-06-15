package com.example.educheck.Controleur.DashbardTeacher;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.ButtonListenerCallBack;
import com.example.educheck.Modele.Justify;
import com.example.educheck.Modele.Student;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AbsentAdapter extends RecyclerView.Adapter<AbsentAdapter.AbsentViewHolder> {

    private ArrayList<Justify> absences;
    private int position;
    private ArrayList<AbsentViewHolder> viewHolders;

    private ButtonListenerCallBack buttonListenerCallBack;

    public AbsentAdapter(ArrayList<Justify> absences, ButtonListenerCallBack buttonListenerCallBack) {
        this.absences = absences;
        this.position =0;
        this.viewHolders = new ArrayList<>();
        this.buttonListenerCallBack = buttonListenerCallBack;
    }

    @NonNull
    @Override
    public AbsentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_absent, parent, false);
        AbsentViewHolder absentViewHolder = new AbsentViewHolder(view, this.buttonListenerCallBack);
        return absentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AbsentViewHolder holder, int position) {
        Justify justify = absences.get(position);
        holder.bind(justify);
        viewHolders.add(holder);
    }

    @Override
    public int getItemCount() {
        return absences.size();
    }

    public class AbsentViewHolder extends RecyclerView.ViewHolder implements ButtonListenerCallBack {

        public TextView textViewDate, textViewJustify;
        public LinearLayout linearLayout, linearLayoutButtons;
        public Button accept, watch;
        private ButtonListenerCallBack buttonListenerCallBack;
        private Justify justify;

        public AbsentViewHolder(@NonNull View itemView, ButtonListenerCallBack buttonListenerCallBack) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewJustify = itemView.findViewById(R.id.textViewBoolJustify);
            linearLayout = itemView.findViewById(R.id.linearabsent);
            linearLayoutButtons = itemView.findViewById(R.id.linearLayoutButtons);
            accept = itemView.findViewById(R.id.btnAccept);
            watch = itemView.findViewById(R.id.btnWatch);
            this.buttonListenerCallBack = buttonListenerCallBack;
        }

        public void bind(Justify justify) {
            this.justify = justify;
            textViewDate.setText(justify.getDate() + " : " + justify.getNameCours());
            String justif = justify.getJustifie();
            initCardAbsenceInfo(justif);
            initListenerButton();
        }

        private void initCardAbsenceInfo(String info){
            if(info.equals("False")) {
                linearLayout.setBackgroundColor(Color.parseColor("#FEA79E"));
                watch.setVisibility(View.GONE);
                accept.setVisibility(View.GONE);
                textViewJustify.setText("Unjustify");
            }else if(info.equals("True")){
                linearLayout.setBackgroundColor(Color.parseColor("#FDE1B7"));
                textViewJustify.setText("Justify");
            }
            else {
                linearLayout.setBackgroundColor(Color.parseColor("#DAFEA5"));
                accept.setVisibility(View.GONE);
            }
        }

        private void initListenerButton(){
            accept.setOnClickListener(v -> callBackListener("accept"));
            watch.setOnClickListener(v -> callBackListener("openFile"));
        }

        @Override
        public void callBackListener(String request) {
            Log.d("TEST","Absent : test callBack");
            switch (request){
                case "accept":
                    request = request + " " + justify.getId();
                    buttonListenerCallBack.callBackListener(request);
                    break;
                case "openFile":
                    request = request + " " + justify.getPdf();
                    buttonListenerCallBack.callBackListener(request);
                    break;
            }
        }
    }
}
