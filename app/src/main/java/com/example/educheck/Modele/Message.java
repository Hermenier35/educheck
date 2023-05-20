package com.example.educheck.Modele;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String mailRecipient;
    private String mailSender;
    private String message;
    private Date date;

    public Message(String mailRecipient, String mailSender, String message, Date date) {
        this.mailRecipient = mailRecipient;
        this.mailSender = mailSender;
        this.message = message;
        this.date = date;
    }

    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mailRecipient", getMailRecipient());
            jsonObject.put("mailSender", getMailSender());
            jsonObject.put("message", getMessage());
            jsonObject.put("date", getDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getMailRecipient() {
        return mailRecipient;
    }

    public String getMailSender() {
        return mailSender;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
