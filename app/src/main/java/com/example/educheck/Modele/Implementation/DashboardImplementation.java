package com.example.educheck.Modele.Implementation;

import com.example.educheck.Modele.AcademicBackground;
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
        this.callBack = callback;
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
    public void changePassword(String token, String password, String newPassword) {
        Request request = new Request(this, "PUT");
        JSONObject body = new JSONObject();
        try {
            body.put("password", password);
            body.put("newPassword", newPassword);
            request.setBody(body);
            request.execute(HttpUrl.UrlChangePassword + "/" + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUniversity(String token) {
        Request request = new Request(this, "GET");
        request.execute(HttpUrl.UrlGetUniversity + "/" + token);
    }

    @Override
    public void addAcademicBackground(String token, String typePath, String namePath, String uniName, String referentMail) {
        Request request = new Request(this, "PUT");
        JSONObject body = new JSONObject();
        try{
            body.put("type", typePath);
            body.put("pathName", namePath);
            body.put("uniName", uniName);
            body.put("referant", referentMail);
            request.setBody(body);
            request.execute(HttpUrl.UrlAddAcademicBackground + "/" + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllAcademicBackgrounds(String suffixe) {
        Request request = new Request(this, "GET");
        request.execute(HttpUrl.UrlGetAcademicBackground + "/" + suffixe);
    }

    @Override
    public void editAcademicBackground(String token, AcademicBackground academicBackground) {
        Request request = new Request(this, "PUT");
        JSONObject body = academicBackground.convertToJSONObject();
        request.setBody(body);
        request.execute(HttpUrl.UrlEditAcademicBackground + "/" + token);
    }

    @Override
    public void getPersonalCourses(String token) {
        Request request= new Request(this,"GET");
        request.execute(HttpUrl.UrlPersonalCourses+ "/" + token);
    }

    @Override
    public void postCourses(String token, String mailStudent, String idCourse) {
        Request request = new Request(this, "POST");
        request.execute();
    }

    @Override
    public void deleteAcademicBackground(String token, String id) {
        Request request = new Request(this, "DELETE");
        JSONObject body = new JSONObject();
        try {
            body.put("_id", id);
            request.setBody(body);
            //request.execute(HttpUrl.UrlDeleteAcademicBackground + "/" + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSchedule(String url) {
        Request request = new Request(this, "PLANNING");
        request.execute(url);
    }

    @Override
    public void sendMexTo(String token){ // pour avoir tous les gens à qui on peux envoyer les messages.
        Request request= new Request(this,"GET");
        request.execute(HttpUrl.UrlSendMexTo+ "/" + token);
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
