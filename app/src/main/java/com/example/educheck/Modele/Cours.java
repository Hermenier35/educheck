package com.example.educheck.Modele;

import java.util.List;

public class Cours {
    String name,profName;
    int credit;
    private List<Double> marks;

    public Cours(String name,String profName, int credit) {
        this.name = name;
        this.profName = profName;
        this.credit = credit;
        this.marks= marks;
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
    public List<Double> getMarks() { return marks;  }
    public void setMarks(double mark) { marks.add(mark); return;}
}

