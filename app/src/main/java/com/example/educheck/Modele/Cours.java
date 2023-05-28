package com.example.educheck.Modele;

public class Cours {
    String name,uniName,pathName,pathType,profName;
    int credit;

    public Cours(String name, String uniName, String pathName, String pathType, String profName, int credit) {
        this.name = name;
        this.uniName = uniName;
        this.pathName = pathName;
        this.pathType = pathType;
        this.profName = profName;
        this.credit = credit;
    }


    public String getName() {
        return name;
    }

    public String getUniName() {
        return uniName;
    }

    public String getPathName() {
        return pathName;
    }

    public String getPathType() {
        return pathType;
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

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public void setPathType(String pathType) {
        this.pathType = pathType;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}

