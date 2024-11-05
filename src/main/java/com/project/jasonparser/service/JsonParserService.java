package com.project.jasonparser.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class JsonParserService {
    public String parseJson(String jsonString){
        if (!isValidJson(jsonString)) {
            return "Invalid JSON format";
        }
        JSONObject jsonObject = new JSONObject(jsonString);

        StringBuilder responseBuilder = new StringBuilder();
        formatJson(responseBuilder, jsonObject, "");
        return responseBuilder.toString();
    }

    private String formatJson(StringBuilder responseBuilder, JSONObject jsonObject, String prefix){

        Map<String, Object> jsonMap = jsonObject.toMap();

        for (Map.Entry<String, Object> entry : jsonMap.entrySet()){
           String key = entry.getKey();
            Object value = jsonObject.get(key);
           String newPrefix = key;
            if (value instanceof String || value instanceof Integer || value instanceof Boolean) {
                responseBuilder.append(newPrefix).append(" : " + value+"\n");
            }
           if(value instanceof JSONObject){
               responseBuilder.append(newPrefix).append(" : {\n");
               formatJson(responseBuilder, (JSONObject) value, newPrefix); // Recursively format the nested object
               responseBuilder.append("}\n");
           }else if (value instanceof JSONArray){
               JSONArray jsonArray = (JSONArray) value;
               responseBuilder.append(newPrefix).append(" : [\n");

               // Iterate through array elements
               boolean first = true;
               for (int i = 0; i < jsonArray.length(); i++) {
                   if (!first) {
                       //responseBuilder.append(",");
                   }
                   Object arrayElement = jsonArray.get(i);
                   if (arrayElement instanceof JSONObject) {
                       formatJson(responseBuilder, (JSONObject) arrayElement, newPrefix + "[" + i + "]");
                   } else {
                       responseBuilder.append("    ").append(arrayElement);
                   }
                   first = false;
               }
               responseBuilder.append("]\n");
           }
        }

  return responseBuilder.toString();

    }

    public static boolean isValidJson(String jsonString) {
        try {
            new JSONObject(jsonString); // Attempt to create a JSONObject
            return true; // If successful, it's valid JSON
        } catch (JSONException e) {
            return false; // If an exception is thrown, it's invalid JSON
        }
    }
}
