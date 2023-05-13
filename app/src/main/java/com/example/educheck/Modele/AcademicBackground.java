package com.example.educheck.Modele;

import org.json.JSONException;
import org.json.JSONObject;

public class AcademicBackground {
    private String name;

    public AcademicBackground(String name) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
