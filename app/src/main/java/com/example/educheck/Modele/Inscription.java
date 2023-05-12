package com.example.educheck.Modele;

import org.json.JSONArray;

import java.util.List;

public interface Inscription {
    public void getAllUniversities();
    public void getAllAcademicBackgrounds();
    public void registerUniversity(University university);
    public void registerAcademicBackground(AcademicBackground academicBackground);
    public void onTaskCompleted(JSONArray items);
}
