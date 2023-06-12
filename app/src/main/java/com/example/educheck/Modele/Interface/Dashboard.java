package com.example.educheck.Modele.Interface;

import com.example.educheck.Modele.AcademicBackground;
import com.example.educheck.Modele.Cours;
import com.example.educheck.Modele.Marks;
import com.example.educheck.Modele.Message;
import com.example.educheck.Modele.University;

import java.util.ArrayList;

public interface Dashboard {
    public void sendMessageTo(Message message, String token);
    public void sendMexTo(String token);

    void getAllJust(String token);

    public void retrieveMessages(String token);
    public void getCourses(String token);
    public void postUniversity(String token, University university);
    public void changePassword(String token, String password, String newPassword);
    public void getUniversity(String token);
    public void addAcademicBackground(String token, String typePath, String namePath, String uniName, String referentMail);
    public void getAllAcademicBackgrounds(String suffixe);
    public void editAcademicBackground(String token, AcademicBackground academicBackground);
    public void editUniversity(String token, University university);
    public void postCoursesStudent(String token, ArrayList<String> mailStudent, String idCourse, String idPath);
    public void deleteAcademicBackground(String token, String id);
    public void getSchedule(String url);
    public void addCourse(String token, Cours cours, String _id);
    public void getMarks(String token, String _idCour);
    public void addMark(String token, String email, Marks mark, String courseName );
    public void getUsers(String token);
}
