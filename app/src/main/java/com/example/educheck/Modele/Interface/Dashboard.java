package com.example.educheck.Modele.Interface;

import com.example.educheck.Modele.Message;
import com.example.educheck.Modele.University;

public interface Dashboard {
    public void sendMessageTo(Message message, String token);
    public void retrieveMessages(String token);
    public void getCourses(String token);
    public void postUniversity(String token, University university);
    public void changePassword(String token, String password, String newPassword);
    public void getUniversity(String token);
}
