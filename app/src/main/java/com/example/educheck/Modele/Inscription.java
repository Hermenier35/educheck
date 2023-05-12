package com.example.educheck.Modele;

import java.util.List;

public interface Inscription {
    public List<University> getAllUniversities();
    public List<AcademicBackground> getAllAcademicBackgrounds();
    public boolean registerUniversity(University university);
    public boolean registerAcademicBackground(AcademicBackground academicBackground);
}
