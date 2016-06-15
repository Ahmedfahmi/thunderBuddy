package com.ahmedfahmi.challenge.Assets;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Ahmed Fahmi on 6/14/2016.
 */
public class LoadingTask extends AsyncTask<String, Void, String> {

    String result = "";
    ArrayList<String> dayData = new ArrayList<>();
    ArrayList<String> tempData = new ArrayList<>();
    ArrayList<String> dateData = new ArrayList<>();
    ArrayList<Weather> weatherData = new ArrayList<>();

    public ArrayList<Weather> getWeatherData() {
        return weatherData;
    }

    @Override
    protected String doInBackground(String... urls) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urls[0])

                .build();

        try {
            Response response = client.newCall(request).execute();
           result= response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    public ArrayList<String> getDayData() {
        return dayData;
    }

    public ArrayList<String> getTempData() {
        return tempData;
    }

    public ArrayList<String> getDateData() {
        return dateData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String celsius="";

        try {
            JSONObject jsonWeather = new JSONObject(s);
            JSONObject weatherForecast = jsonWeather
                    .getJSONObject("forecast");
            JSONObject simpleForecast = weatherForecast
                    .getJSONObject("simpleforecast");
            JSONArray forecastArray = simpleForecast
                    .getJSONArray("forecastday");
            for (int i = 0; i <4; i++) {
                JSONObject fa = forecastArray.getJSONObject(i);
                JSONObject highArray = fa.getJSONObject("high");
                for (int h = 0; h < 3; h++) {
                    celsius = highArray.getString("celsius");
                    Log.i("E_", celsius);
                    tempData.add(celsius);


                }
                JSONObject dateArray = fa.getJSONObject("date");
                for (int h = 0; h < 1; h++) {
                    String weekday = dateArray.getString("weekday");
                    String day = dateArray.getString("day");
                    String month = dateArray.getString("month");
                    String year = dateArray.getString("year");
                    Log.i("E_", weekday);
                    Log.i("E_", day);
                    Log.i("E_", month);
                    Log.i("E_", year);
                    String fullDate = day +" "+ year +" "+ month;
                    dateData.add(fullDate);
                    dayData.add(weekday);
                    weatherData.add(new Weather(weekday,celsius,fullDate));


                }


            }


        } catch (JSONException e) {
            Log.i("E_", "Failed");
            e.printStackTrace();
        }



    }

}
