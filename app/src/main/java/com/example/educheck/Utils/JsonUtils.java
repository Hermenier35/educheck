package com.example.educheck.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class JsonUtils {
    public static JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2) {
        JSONObject mergedJSON = new JSONObject();

        try {
            mergedJSON = new JSONObject(json1.toString()); // Copie le contenu de json1 dans mergedJSON

            Iterator<String> keys = json2.keys();
            while(keys.hasNext()){
                String key = keys.next();
                mergedJSON.put(key, json2.get(key)); // Ajoute les clés et valeurs de json2 à mergedJSON
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mergedJSON;
    }

    public static String[] jsonObjectToArrayString(JSONObject jsonObject) throws JSONException {
        Iterator<String> iterator = jsonObject.keys();
        String[] myArray;
        ArrayList<String> array = new ArrayList<>();
        while (iterator.hasNext()) {
            array.add(jsonObject.getString(iterator.next()));
        }
        myArray = (String[]) array.toArray();
        return myArray;
    }

    public static JSONObject eventClean(String doc){
        JSONObject jsonResult = new JSONObject();
        JSONArray eventsArray = new JSONArray();
        final String keyWord[] = {"BEGIN","DTSTAMP","DTSTART","DTEND","SUMMARY","LOCATION","DESCRIPTION","UID", "CREATED",
        "LAST-MODIFIED", "SEQUENCE", "END"};

        String[] events = doc.split("END:VEVENT");

        for (String event : events) {
            if(event.indexOf("PRODID")>0)
                event = event.substring(30);
            if(event.length()>30)
                try {
                    JSONObject eventObject = new JSONObject();
                    eventObject.put(keyWord[2], event.substring(event.indexOf(keyWord[2]) + keyWord[2].length()+1, event.indexOf(keyWord[3])));
                    eventObject.put(keyWord[3], event.substring(event.indexOf(keyWord[3]) + keyWord[3].length()+1, event.indexOf(keyWord[4])));
                    eventObject.put(keyWord[4], event.substring(event.indexOf(keyWord[4]) + keyWord[4].length()+1, event.indexOf(keyWord[5])));
                    eventObject.put(keyWord[5], event.substring(event.indexOf(keyWord[5]) + keyWord[5].length()+1, event.indexOf(keyWord[6])));
                    eventObject.put(keyWord[6], event.substring(event.indexOf(keyWord[6]) + keyWord[6].length()+1, event.indexOf(keyWord[7])));
                    eventsArray.put(eventObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }

        try {
            jsonResult.put("items", eventsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }
}
