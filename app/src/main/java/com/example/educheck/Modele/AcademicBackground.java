package com.example.educheck.Modele;

import android.media.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class AcademicBackground implements Serializable {
    private String name;
    private String type;
    private byte[] image;

    public AcademicBackground(String name, String type) {
        this.name = name;
        this.type = type;
        this.image=new byte[20];
    }


    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", getName());
            jsonObject.put("type", getType());
            jsonObject.put("image", getImage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
