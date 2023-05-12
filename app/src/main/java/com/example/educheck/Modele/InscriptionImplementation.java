package com.example.educheck.Modele;
import com.example.educheck.Utils.HttpUrl;

import org.json.JSONArray;


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

    }

    @Override
    public void registerUniversity(University university) {

    }

    @Override
    public void registerAcademicBackground(AcademicBackground academicBackground) {

    }

    @Override
    public void onTaskCompleted(JSONArray items) {
        itemsReady.onTaskCompleted(items);
    }
}
