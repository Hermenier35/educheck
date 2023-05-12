package com.example.educheck.Modele;

import com.example.educheck.Utils.HttpUrl;

import org.json.JSONArray;

public class LoginImplementation implements Login, AsyncTaskcallback {
    private JSONArray items;

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
        this.items = items;

    }

    public JSONArray getItems() {
        return items;
    }
}
