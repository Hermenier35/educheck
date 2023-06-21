package com.example.educheck.Modele;

import org.json.JSONException;
import org.json.JSONObject;

public class Date2 {
    private int hours;
    private int minutes;
    private int date;
    private int during;

    public Date2(int hours, int minutes, int date, int during) {
        this.hours = hours;
        this.minutes = minutes;
        this.date = date;
        this.during = during;
    }

    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("hours", getHours());
            jsonObject.put("minutes", getMinutes());
            jsonObject.put("date", getDate());
            jsonObject.put("during", getDuring());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public int getYear(){
        return date/10000;
    }

    public int getMonth(){
        switch(String.valueOf(date).substring(4,6)){
            case"01" : return 0;
            case"02" : return 1;
            case"03" : return 2;
            case"04" : return 3;
            case"05" : return 4;
            case"06" : return 5;
            case"07" : return 6;
            case"08" : return 7;
            case"09" : return 8;
            default: return Integer.parseInt(String.valueOf(date).substring(4,6)) - 1;
        }
    }

    public int getDay(){
        String day = String.valueOf(date).substring(6);
        switch(day){
            case"01" : return 1;
            case"02" : return 2;
            case"03" : return 3;
            case"04" : return 4;
            case"05" : return 5;
            case"06" : return 6;
            case"07" : return 7;
            case"08" : return 8;
            case"09" : return 9;
            default: return Integer.parseInt(day);
        }
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getDuring() {
        return during;
    }

    public void setDuring(int during) {
        this.during = during;
    }
}
