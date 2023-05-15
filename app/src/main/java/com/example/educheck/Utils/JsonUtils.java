package com.example.educheck.Utils;

import org.json.JSONException;
import org.json.JSONObject;

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
}
