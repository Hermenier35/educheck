package com.example.educheck.Modele.Interface;

import org.json.JSONArray;
import org.json.JSONException;

public interface AsyncTaskcallback {
    public void onTaskCompleted(JSONArray items) throws JSONException;

    boolean onQueryTextChange(String newText);
}
