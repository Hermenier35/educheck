package com.example.educheck.Modele.Implementation;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.Login;
import com.example.educheck.Modele.Request;
import com.example.educheck.Utils.HttpUrl;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginImplementation implements Login, AsyncTaskcallback {
    private AsyncTaskcallback itemsReady;

    public LoginImplementation(AsyncTaskcallback itemsReady) {
        this.itemsReady = itemsReady;
    }

    @Override
    public void forgetPassword(String mail) {
        Request request = new Request(this, "GET");
        request.execute(HttpUrl.UrlForgetPassword + "/" + mail);
    }

    @Override
    public void connexion(String mail, String password) {
        Request request = new Request(this, "GET");
        request.execute(HttpUrl.UrlConnexion + "/" + mail + "/" + password);
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
