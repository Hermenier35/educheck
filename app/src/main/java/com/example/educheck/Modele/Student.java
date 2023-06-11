package com.example.educheck.Modele;

import java.util.ArrayList;

public class Student extends Users {
    private ArrayList<String> paths;
    private ArrayList<String> cours;
    public Student(String firstName, String lastName, String mail, String ine, String status) {
        super(firstName, lastName, mail, ine, status);
        this.paths = new ArrayList<>();
        this.cours = new ArrayList<>();
    }

    public void addPath(String id){
        this.paths.add(id);
    }

    public void addCour(String id){
        this.cours.add(id);
    }

    public ArrayList<String> getPaths() {
        return paths;
    }

    public ArrayList<String> getCours() {
        return cours;
    }
}
