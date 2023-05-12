package com.example.educheck.Modele;

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
        Request request = new Request(this);
        request.execute(HttpUrl.UrlForgetPassword + "/" + mail);
    }

    @Override
    public void connexion(String mail, String password) {
        Request request = new Request(this);
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
