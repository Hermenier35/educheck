package com.example.educheck.Modele;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Base64;

public class University implements Serializable {

    private String uniName;
    private String suffixeStudent;
    private String suffixeTeacher;
    private byte[] image;
    private String _id;

    public University(String name, String suffixeStudent, String suffixeTeacher, byte[] image){
        this.uniName = name;
        this.suffixeStudent = suffixeStudent;
        this.suffixeTeacher = suffixeTeacher;
        this.image = image;
        this._id = "";
    }

    public University(String name, String suffixeStudent, String suffixeTeacher, byte[] image, String _id){
        this.uniName = name;
        this.suffixeStudent = suffixeStudent;
        this.suffixeTeacher = suffixeTeacher;
        this.image = image;
        this._id = _id;
    }

    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uniName", getUniName());
            jsonObject.put("suffixe_student", getSuffixeStudent());
            jsonObject.put("suffixe_teacher", getSuffixeTeacher());
            jsonObject.put("image", Base64.getEncoder().encodeToString(getImage()));
            jsonObject.put("_idUni", get_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getSuffixeStudent() {
        return suffixeStudent;
    }

    public String getSuffixeTeacher() {
        return suffixeTeacher;
    }

    public void setSuffixeTeacher(String suffixeTeacher) {
        this.suffixeTeacher = suffixeTeacher;
    }

    public void setUniName(String UniName) {
        this.uniName = UniName;
    }

    public String getUniName() {
        return uniName;
    }

    public void setSuffixeStudent(String suffixeStudent) {
        this.suffixeStudent = suffixeStudent;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
