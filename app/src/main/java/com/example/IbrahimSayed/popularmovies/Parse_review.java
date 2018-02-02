package com.example.IbrahimSayed.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ibrahim on 10/7/2015.
 */
public class Parse_review
{
    private final String LOG_TAG = ParseJSONstr.class.getSimpleName();
    private String JSONFile;

    public ArrayList<review> ParseJSON_To_Review(String JSONFile) {
        Log.v(LOG_TAG, JSONFile);
        this.JSONFile = JSONFile;
        try {
            return getDataFromJson(JSONFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<review> getDataFromJson(String forecastJsonStr)
            throws JSONException {
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray DataArray = forecastJson.getJSONArray("results");
        ArrayList<review> userData = new ArrayList<review>();
        for (int i = 0; i < DataArray.length(); i++) {
            // Get the JSON object representing the day
            JSONObject Forecast = DataArray.getJSONObject(i);
            String id = Forecast.getString("id");
            String author= Forecast.getString("author");
            String content= Forecast.getString("conten");
            review temp = new review(id,author,content);
            userData.add(temp);
        }
        return userData;
    }
}
