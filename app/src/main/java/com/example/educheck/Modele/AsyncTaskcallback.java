package com.example.educheck.Modele;

import org.json.JSONArray;
import org.json.JSONException;

public interface AsyncTaskcallback {
    public void onTaskCompleted(JSONArray items) throws JSONException;
}
