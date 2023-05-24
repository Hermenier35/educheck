package com.example.educheck.Modele.Implementation;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.Dashboard;
import com.example.educheck.Modele.Message;
import com.example.educheck.Modele.Request;
import com.example.educheck.Modele.University;
import com.example.educheck.Utils.HttpUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashboardImplementation implements Dashboard, AsyncTaskcallback {
    private AsyncTaskcallback callBack;

    public DashboardImplementation(AsyncTaskcallback callback) {
        this.callBack = callBack;
    }

    @Override
    public void sendMessageTo(Message message, String token) {
        Request request = new Request(this, "POST");
        JSONObject body = message.convertToJSONObject();
        request.setBody(body);
        request.execute(HttpUrl.UrlSendMessageTo + "/" + token);
    }

    @Override
    public void retrieveMessages(String token) {
        Request request = new Request(this, "GET");
        request.execute(HttpUrl.UrlRetrieveMessages + "/" + token);
    }

    @Override
    public void getCourses(String token) {
        Request request = new Request(this, "GET");
        request.execute(HttpUrl.UrlGetCourses + "/" + token);
    }

    @Override
    public void postUniversity(String token, University university) {
        Request request = new Request(this, "POST");
        JSONObject body = university.convertToJSONObject();
        request.setBody(body);
        request.execute(HttpUrl.UrlPostUniversity + "/" + token);
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        try {
            callBack.onTaskCompleted(items);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
