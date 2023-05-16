package com.example.educheck.Modele;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public abstract class Users implements Serializable {
    private String firstName;
    private String lastName;
    private String mail;
    private String ine;
    private String status;
    private String token;

    public Users(String firstName, String lastName, String mail, String ine, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.ine = ine;
        this.status = status;
    }

    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstName", getFirstName());
            jsonObject.put("lastName", getLastName());
            jsonObject.put("mail", getMail());
            jsonObject.put("ine", getIne());
            jsonObject.put("status", getStatus());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIne() {
        return ine;
    }

    public void setIne(String ine) {
        this.ine = ine;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
