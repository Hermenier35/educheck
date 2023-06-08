package com.example.educheck.Modele;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class Cours implements Serializable {
    String name,profName;
    int credit;

    public Cours(String name,String profName, int credit) {
        this.name = name;
        this.profName = profName;
        this.credit = credit;
    }

    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", getName());
            jsonObject.put("profName", getProfName());
            jsonObject.put("credit", getCredit());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
    @Override
    public String toString(){
        return "Name: "+ name +"\nprofName: "+profName+"\ncredit: "+ credit;
    }
}

