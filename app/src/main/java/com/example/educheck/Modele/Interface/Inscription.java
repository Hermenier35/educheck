package com.example.educheck.Modele.Interface;

import com.example.educheck.Modele.AcademicBackground;
import com.example.educheck.Modele.Student;
import com.example.educheck.Modele.University;

import org.json.JSONArray;

import java.util.List;

public interface Inscription {
    public void getAllUniversities();
    public void getAllAcademicBackgrounds(String suffixe);
    public void registerOnUniversity(University university, Student student);
    public void registerAcademicBackground(AcademicBackground academicBackground);
    public void onTaskCompleted(JSONArray items);
}
