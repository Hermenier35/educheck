package com.example.educheck.Modele;

import java.util.List;

public class Cours {
    String name,profName;
    int credit;
    public Cours(String name,String profName, int credit) {
        this.name = name;
        this.profName = profName;
        this.credit = credit;
    }

    public String getName() {
        return name;
    }
    public String getProfName() {
        return profName;
    }
    public int getCredit() {
        return credit;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setProfName(String profName) {
        this.profName = profName;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }
    @Override
    public String toString(){
        return "Name: "+ name +"\nprofName: "+profName+"\ncredit: "+ credit;
    }
}

