package com.example.educheck.Modele;

import android.graphics.Color;

import org.json.JSONException;
import org.json.JSONObject;

public class Cellule {
    private String summary;
    private String location;
    private String startHour;
    private String endHour;
    private int color;
    private Date2 date;

    public Cellule(String summary, String location, String startHour, String endHour, Date2 date, int color) {
        this.summary = summary;
        this.location = location;
        this.startHour = startHour;
        this.endHour = endHour;
        this.date = date;
        this.color = color;
    }

    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("summary", getSummary());
            jsonObject.put("location", getLocation());
            jsonObject.put("startHour", getStartHour());
            jsonObject.put("endHour", getEndHour());
            jsonObject.put("date", getDate().convertToJSONObject());
            jsonObject.put("color", getColor());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void setAverageHour(int add){
        this.startHour = setAverage(add, this.startHour);
        this.endHour = setAverage(add, this.endHour);
        this.date.setHours(this.date.getHours()+add);
    }

    private String setAverage(int add, String hour){
        String data[] = hour.split(" : ");
        int h = Integer.parseInt(data[0]) + add;
        return h + " : " + data[1];
    }
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public Date2 getDate() {
        return date;
    }

    public void setDate(Date2 date) {
        this.date = date;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
