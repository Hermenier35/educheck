package com.example.educheck.Modele;

import android.graphics.Color;

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

    public void setAverageHour(int add){

    }

    private String setAverage(int add){
        String data[] = startHour.split(" : ");
        int hour = Integer.parseInt(data[0]) + add;
        return hour + " : " + data[1];
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
