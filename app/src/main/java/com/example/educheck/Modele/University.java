package com.example.educheck.Modele;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class University implements Serializable {

    private String name;
    private String suffixe;
    private byte[] image;

    public University(String name, String suffixe, byte[] image){
        this.name = name;
        this.suffixe = suffixe;
        this.image = image;
    }

    public String getName(){
        return this.name;
    }

    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getSuffixe() {
        return suffixe;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuffixe(String suffixe) {
        this.suffixe = suffixe;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
