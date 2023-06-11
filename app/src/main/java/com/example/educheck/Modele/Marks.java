package com.example.educheck.Modele;

import java.util.Date;

public class Marks {

    String nameProf;
    String type;
    float mark;

    private Date date;


    public  Marks(String nameProf,String type,float mark){
        this.type=type;
        this.mark=mark;
        this.nameProf=nameProf;
    }

    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }

    public void setMark(float mark){
        this.mark=mark;
    }

    public void setNameProf(String nameProf){
        this.nameProf=nameProf;
    }

    public String getNameProf(){
        return nameProf;
    }



    public float getMark(){
        return mark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}