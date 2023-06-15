package com.example.educheck.Modele.Implementation;

import android.util.Log;

import com.example.educheck.Modele.AcademicBackground;
import com.example.educheck.Modele.Cours;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.Dashboard;
import com.example.educheck.Modele.Marks;
import com.example.educheck.Modele.Message;
import com.example.educheck.Modele.Request;
import com.example.educheck.Modele.University;
import com.example.educheck.Utils.HttpUrl;
import com.example.educheck.Utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

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
    public void justify(String id_j,String token,String mail,String profMail,byte[] img){
        Request request = new Request(this, "POST");
        JSONObject body = new JSONObject();
        try {

            body.put("id_j",id_j);
            body.put("studentEmail", mail);
            body.put("professorEmail", profMail);
            body.put("imagePath", Base64.getEncoder().encodeToString(img));
            request.setBody(body);
            request.execute(HttpUrl.UrlJustify + "/" + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void justifyProf(String id_j, String token, String studentMail) {
        Request request = new Request(this, "POST");
        JSONObject body = new JSONObject();
        try {

            body.put("id_j",id_j);
            body.put("studentEmail", studentMail);
            request.setBody(body);
            request.execute(HttpUrl.UrljustifyProf + "/" + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllJust(String token){
        Request request=new Request(this,"GET");
        request.execute(HttpUrl.UrlGetAllJust+"/"+token);
    }
    @Override
    public void retrieveMessages(String token, String mail, String mailDest) {
        Request request = new Request(this, "GET");
        request.execute(HttpUrl.UrlRetrieveMessages + "/" + token + "/" + mail + "/" + mailDest);
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
    public void editUniversity(String token, University university) {
        Request request = new Request(this, "PUT");
        JSONObject body = university.convertToJSONObject();
        request.setBody(body);
        request.execute(HttpUrl.UrlEditUniversity + "/" + token);

    }

    @Override
    public void postCoursesStudent(String token, ArrayList<String> mailStudent, String idCourse, String idPath) {
        Request request = new Request(this, "POST");
        JSONObject body = new JSONObject();
        try{
            body.put("mail", JsonUtils.arrayListToJson(mailStudent));
            body.put("_idCourse", idCourse);
            body.put("_idPath", idPath);
            request.setBody(body);
            Log.d("TEST",JsonUtils.arrayListToJson(mailStudent).toString());
            request.execute(HttpUrl.UrlPostCourseStudent + "/" + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAcademicBackground(String token, String id) {
        Request request = new Request(this, "DELETE");
        JSONObject body = new JSONObject();
        try {
            body.put("_idPath", id);
            request.setBody(body);
            request.execute(HttpUrl.UrlDeleteAcademicBackground + "/" + token);
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
    public void addCourse(String token, Cours cours, String _id) {
        Request request = new Request(this, "POST");
        JSONObject body = cours.convertToJSONObject();
        try {
            body.put("_id", _id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setBody(body);
        request.execute(HttpUrl.UrlAddCourse + "/" + token);
    }

    @Override
    public void getMarks(String token, String nameCours) {
        Request request= new Request(this,"GET");
        JSONObject body=new JSONObject();
        try {
            body.put("nameCours", nameCours);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.execute(HttpUrl.UrlGetMarks+"/"+token);
    }

    @Override
    public void addMark(String token, String email, Marks mark, String courseName) {
        Request request = new Request(this, "POST");
        JSONObject body = mark.convertToJSONObject();
        try {
            body.put("mailEtudiant", email);
            body.put("nameCours", courseName);
            request.setBody(body);
            request.execute(HttpUrl.UrlAddNotes + "/" + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUsers(String token) {
        Request request = new Request(this, "GET");
        request.execute(HttpUrl.UrlGetUsers + "/" + token);
    }

    @Override
    public void addAbs(String token, ArrayList<String> mailStudent, String nameCourse, String date) {
        Request request = new Request(this, "POST");
        JSONObject body = new JSONObject();
        JSONArray array = JsonUtils.arrayListToJson(mailStudent);
        try {
            body.put("mailStudents", array);
            body.put("nameCours", nameCourse);
            body.put("date", date);
            request.setBody(body);
            request.execute(HttpUrl.UrlAddAbs + "/" + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public void recMex(String token,String mailSender) {
        Request request = new Request(this, "POST");
        JSONObject body = new JSONObject();
        try {
            body.put("mailSender",mailSender);
            request.setBody(body);
            request.execute(HttpUrl.UrlRecMex + "/" + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
