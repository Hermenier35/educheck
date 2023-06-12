package com.example.educheck.Controleur.Dashboard;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

public class Justificatif {

    private String id_justificatif;
    private String date;
    private byte[] image;

    private String profName;

    private String nameCours;

    public Justificatif(String id_justificatif, String date, byte[] image,String profName,String nameCours) {
        this.id_justificatif = id_justificatif;
        this.date = date;
        this.image = image;
        this.profName=profName;
        this.nameCours=nameCours;
    }


    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_justificatif", getId_justificatif());
            jsonObject.put("date", getDate());
            jsonObject.put("profName",getProfName());
            jsonObject.put("image", Base64.getEncoder().encodeToString(getImage()));
            jsonObject.put("nameCours", getNameCours());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getId_justificatif() {
        return id_justificatif;
    }

    public void setId_justificatif(String id_justificatif) {
        this.id_justificatif = id_justificatif;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public String getNameCours() {
        return nameCours;
    }

    public void setNameCours(String nameCours) {
        this.nameCours = nameCours;
    }
}
