package com.ahmedfahmi.challenge.Assets;


import android.content.Context;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONException;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Ahmed Fahmi on 6/14/2016.
 */
public class LoadingTask extends AsyncTask<String, Void, ArrayList<Weather>> {
    private final String API_URL = "http://api.wunderground.com/api/838ed9367e8876bf%20/forecast/q/EG/Cairo.json";

    private Processor processor;
    private ArrayList<Weather> weatherList;
    private static LoadingTask loadingTask;
    private Context context;
    private DataCenter dataCenter;

    public interface LoadingTaskFinishedListener {
        void onTaskFinished();
    }

    private LoadingTaskFinishedListener finishedListener = null;


    public ArrayList<Weather> getWeatherList() {
        return weatherList;
    }


    public static LoadingTask instance(Context context, LoadingTaskFinishedListener loadingTaskFinishedListener) {

        if (loadingTask == null) {
            loadingTask = new LoadingTask(context, loadingTaskFinishedListener);
        }
        return loadingTask;
    }

    public static LoadingTask instance() {
        return loadingTask;
    }

    private LoadingTask(Context context, LoadingTaskFinishedListener loadingTaskFinishedListener) {
        processor = Processor.instance();
        this.context = context;
        this.finishedListener = loadingTaskFinishedListener;
    }

    private LoadingTask() {

    }




    @Override
    protected ArrayList<Weather> doInBackground(String... urls) {

        weatherList = new ArrayList<>();
        dataCenter = DataCenter.instance();

        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(API_URL).build();

        try {


            if (5 > 1) {
                Response response = client.newCall(request).execute();
                weatherList = processor.processJSON(response.body().string());
                Log.d("E_", "network ");
            } else {
                weatherList = dataCenter.getWeatherList();
                Log.d("E_", "database ");
            }


        } catch (JSONException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }


        return weatherList;
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        super.onPostExecute(weathers);
        finishedListener.onTaskFinished();
    }


    public boolean networkDisabled() {
        final String GOOGLE_WEB_SITE = "https://www.google.co.eg/";
        int responseCode = -1;
        boolean disabled = false;
        URLConnection connection = null;
        try {
            URL url = new URL(GOOGLE_WEB_SITE);
            connection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setConnectTimeout(3000);
        HttpURLConnection httpConnection = (HttpURLConnection) connection;

        try {
            responseCode = httpConnection.getResponseCode();
        } catch (Exception e1) {
            disabled = true;
        }
        if (responseCode == HttpURLConnection.HTTP_OK) {
        } else {
            disabled = false;
        }
        return disabled;
    }
}
