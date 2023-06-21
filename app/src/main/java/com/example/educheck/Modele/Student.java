package com.example.educheck.Modele;

import com.example.educheck.Utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Student extends Users {
    private ArrayList<String> paths;
    private ArrayList<String> cours;
    private ArrayList<Justify> justifies;

    public Student(String firstName, String lastName, String mail, String ine, String status) {
        super(firstName, lastName, mail, ine, status);
        this.paths = new ArrayList<>();
        this.cours = new ArrayList<>();
        this.justifies = new ArrayList<>();
    }


    public JSONObject convertToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("paths", arrayListToJSONArray(getPaths()));
            jsonObject.put("cours", arrayListToJSONArray(getCours()));
            jsonObject.put("justifies", arrayListTJSONArray(getJustifies()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonUtils.mergeJSONObjects(jsonObject, super.convertToJSONObject());
    }


    private JSONArray arrayListToJSONArray(ArrayList<String> object){
        JSONArray array = new JSONArray();
        object.forEach(s -> array.put(s));
        return array;
    }

    private JSONArray arrayListTJSONArray(ArrayList<Justify> object){
        JSONArray array = new JSONArray();
        object.forEach(justify -> array.put(justify.convertToJSONObject()));
        return array;
    }
    public void addPath(String id){
        this.paths.add(id);
    }

    public void addCour(String id){
        this.cours.add(id);
    }

    public ArrayList<String> getPaths() {
        return paths;
    }

    public ArrayList<String> getCours() {
        return cours;
    }

    public ArrayList<Justify> getJustifies() {
        return justifies;
    }
}
