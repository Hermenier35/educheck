package com.example.educheck.Modele.Interface;

import com.example.educheck.Modele.Message;

public interface Dashboard {
    public void sendMessageTo(Message message, String token);
    public void retrieveMessages(String token);
    public void getCourses(String token);
}
