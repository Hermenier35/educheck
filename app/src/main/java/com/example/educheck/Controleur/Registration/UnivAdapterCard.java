package com.example.educheck.Controleur.Registration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educheck.Modele.University;
import com.example.educheck.R;

import java.util.ArrayList;
import android.graphics.BitmapFactory;

public class UnivAdapterCard extends RecyclerView.Adapter<UnivAdapterCard.UniversityHolder> {

    private ArrayList<University> universities;
    private int pos;

    public UnivAdapterCard(ArrayList<University> universities) {
        this.universities = universities;
        this.pos=0;
    }

    @NonNull
    @Override
    public UniversityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_univ, parent, false);
        item.setOnClickListener(UniversityInscription.myOnClickListener);
        return new UniversityHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityHolder holder, int position) {
        TextView textViewUniv_Name= holder.Univ_Name;
        ImageView imageView = holder.imageView;
        textViewUniv_Name.setText(universities.get(position).getUniName());
        byte[] image = universities.get(position).getImage();
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
    }

    @Override
    public int getItemCount() {
        return universities.size();
    }


    class UniversityHolder extends RecyclerView.ViewHolder{
        private TextView Univ_Name;
        private ImageView imageView;


        UniversityHolder(View itemView){
            super(itemView);
            Univ_Name = itemView.findViewById(R.id.Univ_Name);
            imageView = itemView.findViewById(R.id.idIVCourseImage);
            itemView.setVerticalScrollbarPosition(pos++);
        }

    }



}
