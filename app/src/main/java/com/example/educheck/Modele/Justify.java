package com.example.educheck.Modele;

import java.io.Serializable;

public class Justify implements Serializable {

    private String id;
    private String mailStudent;
    private String date;
    private String nameCours;
    private String justifie;

    public Justify(String id, String mailStudent, String date, String nameCours, String justifie) {
        this.id = id;
        this.mailStudent = mailStudent;
        this.date = date;
        this.nameCours = nameCours;
        this.justifie = justifie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMailStudent() {
        return mailStudent;
    }

    public void setMailProf(String mailStudent) {
        this.mailStudent = mailStudent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameCours() {
        return nameCours;
    }

    public void setNameCours(String nameCours) {
        this.nameCours = nameCours;
    }

    public String getJustifie() {
        return justifie;
    }

    public void setJustifie(String justifie) {
        this.justifie = justifie;
    }
}
