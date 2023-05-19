package com.example.educheck.Modele.Implementation;
import com.example.educheck.Modele.AcademicBackground;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.Inscription;
import com.example.educheck.Modele.Request;
import com.example.educheck.Modele.Student;
import com.example.educheck.Modele.University;
import com.example.educheck.Utils.HttpUrl;
import com.example.educheck.Utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class InscriptionImplementation implements Inscription, AsyncTaskcallback {
    private AsyncTaskcallback callBack;

    public InscriptionImplementation(AsyncTaskcallback callBack) {
        this.callBack = callBack;
    }

    @Override
    public void getAllUniversities() {
        Request request = new Request(this, "GET");
        request.execute(HttpUrl.UrlGetUniversities);
    }

    @Override
    public void getAllAcademicBackgrounds(String suffixe) {
        Request request = new Request(this, "GET");
        request.execute(HttpUrl.UrlGetAcademicBackground + "/" + suffixe);
    }

    @Override
    public void registerOnUniversity(University university, Student student) {
        Request request = new Request(this, "POST");
        JSONObject body = JsonUtils.mergeJSONObjects(university.convertToJSONObject(), student.convertToJSONObject());
        request.setBody(body);
        request.execute(HttpUrl.UrlPostOnUniversity);
    }

    @Override
    public void registerAcademicBackground(AcademicBackground academicBackground) {
        Request request = new Request(this, "POST");
        request.execute(HttpUrl.UrlPostAcademicBackground + "/" + academicBackground);
    }

    @Override
    public void onTaskCompleted(JSONArray items) {
        try {
            callBack.onTaskCompleted(items);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
