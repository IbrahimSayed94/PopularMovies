package com.example.IbrahimSayed.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ibrahim on 10/7/2015.
 */
public class Parse_tailer
{
    private static final String LOG_TAG = ParseJSONstr.class.getSimpleName();
    private String JSONFile;
   public String id;
    public ArrayList<tailer> ParseJSON_To_Tailer(String JSONFile) {
        Log.v(LOG_TAG, JSONFile);
        this.JSONFile = JSONFile;
        try {
            return getDataFromJson(JSONFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<tailer> getDataFromJson(String forecastJsonStr)
            throws JSONException {
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray DataArray = forecastJson.getJSONArray("results");
        ArrayList<tailer> userData = new ArrayList<tailer>();
        for (int i = 0; i < DataArray.length(); i++) {
            // Get the JSON object representing the day
            JSONObject Forecast = DataArray.getJSONObject(i);
            id = Forecast.getString("id");
            String name= Forecast.getString("name");
            String site= Forecast.getString("site");
            String size = Forecast.getString("size");




            tailer temp = new tailer(id, name,site, size);
            userData.add(temp);
        }
        return userData;
    }
}
