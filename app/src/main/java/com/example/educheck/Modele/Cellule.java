package com.example.educheck.Modele;

public class Cellule {
    private String summary;
    private String location;
    private String description;
    private String startHour;
    private String endHour;
    private Date2 date;

    public Cellule(String summary, String location, String description, String startHour, String endHour, Date2 date) {
        this.summary = summary;
        this.location = location;
        this.description = description;
        this.startHour = startHour;
        this.endHour = endHour;
        this.date = date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
