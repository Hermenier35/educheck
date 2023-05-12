package com.example.educheck.Modele;
import com.example.educheck.Utils.HttpUrl;

import org.json.JSONArray;
import org.json.JSONException;


public class InscriptionImplementation implements Inscription, AsyncTaskcallback{
    private AsyncTaskcallback itemsReady;

    public InscriptionImplementation(AsyncTaskcallback itemsReady) {
        this.itemsReady = itemsReady;
    }

    @Override
    public void getAllUniversities() {
        Request request = new Request(this);
        request.execute(HttpUrl.UrlGetUniversity);
    }

    @Override
    public void getAllAcademicBackgrounds() {
        Request request = new Request(this);
        request.execute(HttpUrl.UrlGetAcademicBackground);
    }

    @Override
    public void registerUniversity(University university) {
        Request request = new Request(this);
        request.execute(HttpUrl.UrlPostUniversity + "/" + university);
    }

    @Override
    public void registerAcademicBackground(AcademicBackground academicBackground) {
        Request request = new Request(this);
        request.execute(HttpUrl.UrlPostAcademicBackground + "/" + academicBackground);
    }

    @Override
    public void onTaskCompleted(JSONArray items) {
        try {
            itemsReady.onTaskCompleted(items);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}