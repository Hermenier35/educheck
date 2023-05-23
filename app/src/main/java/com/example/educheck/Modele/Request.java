package com.example.educheck.Modele;

import android.os.AsyncTask;
import android.os.Debug;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Request extends AsyncTask<String, Void, JSONObject> {
    private JSONArray res;
    private AsyncTaskcallback asyncTaskcallback;
    private String type;
    private JSONObject body;
    private int code_retour;

    public Request(AsyncTaskcallback asyncTaskcallback, String type) {
        this.asyncTaskcallback = asyncTaskcallback;
        this.type = type;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        String result = null;

        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       if(type.equals("POST") || type.equals("PUT")){
                try {
                    urlConnection.setRequestMethod(type);
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    OutputStream outputStream = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    writer.write(body.toString());
                    writer.flush();
                    writer.close();
                    outputStream.close();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        try {
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK ||
                responseCode == 201) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream
                result = readStream(in); // Read stream
            }
        }
        catch (MalformedURLException e ) { e.printStackTrace(); }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        JSONObject json = null;
        try {
            if (urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK ||
                urlConnection.getResponseCode()==201)
                json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            code_retour = urlConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json; // returns the result
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        System.out.println("requete fini :" +  this.code_retour);
        try{
            if (jsonObject != null) {
                res = jsonObject.getJSONArray("items");
                asyncTaskcallback.onTaskCompleted(res);
            }else{
                System.out.println("code retour error :" + this.code_retour);
                JSONObject response_request = new JSONObject().put("code_retour", this.code_retour);
                res = (JSONArray) response_request.get("code_retour");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

    public JSONArray getRes() {
        return res;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }
}
