package com.example.educheck.Modele;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Marks {

    String nameProf;
    String type;
    String mark;

    private Date date;


    public  Marks(String nameProf,String type,String mark){
        this.type=type;
        this.mark=mark;
        this.nameProf=nameProf;
    }

    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nameProf", getNameProf());
            jsonObject.put("type", getType());
            jsonObject.put("note", getMark());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }

    public void setMark(String mark){
        this.mark=mark;
    }

    public void setNameProf(String nameProf){
        this.nameProf=nameProf;
    }

    public String getNameProf(){
        return nameProf;
    }



    public String getMark(){
        return mark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}