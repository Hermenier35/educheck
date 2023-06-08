package com.example.educheck.Modele;

public class Marks {
    String type;
    float mark;


    public void marks(String type,float mark){
        this.type=type;
        this.mark=mark;
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
    public float getMark(){
        return mark;
    }
}