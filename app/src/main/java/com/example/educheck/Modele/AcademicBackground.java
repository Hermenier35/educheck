package com.example.educheck.Modele;

import android.media.Image;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class AcademicBackground implements Serializable {
    private String name;
    private String type;
    private ImageView image;
    private String _id;
    private String referent;

    public AcademicBackground(String name, String type, ImageView image, String _id, String referent) {
        this.name = name;
        this.type = type;
        this.image = image;
        this._id = _id;
        this.referent = referent;
    }


    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pathName", getName());
            jsonObject.put("type", getType());
            jsonObject.put("_idPath", get_id());
            jsonObject.put("referant", getReferent());
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
    public ImageView getImage() {
        return image;
    }
    public void setImage(ImageView image) {
        this.image = image;
    }
    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }
    public String getReferent() { return referent; }
    public void setReferent(String referent) { this.referent = referent; }
}
