package com.ahmedfahmi.challenge.managers;


import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.ahmedfahmi.challenge.model.Weather;
import com.ahmedfahmi.challenge.ui.MainActivity;

import org.json.JSONException;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Ahmed Fahmi on 6/14/2016.
 */
public class LoadingTask extends AsyncTask<String, Void, ArrayList<Weather>> {
    private final String API_URL = "http://api.wunderground.com/api/838ed9367e8876bf%20/forecast/q/EG/Cairo.json";

    private ProcessingManager processingManager;
    private ArrayList<Weather> weatherList;
    private static LoadingTask loadingTask;
    private Context context;
    private DataManager dataManager;
    private boolean isOfflineMode = false;


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
        processingManager = ProcessingManager.instance();
        this.context = context;
        this.finishedListener = loadingTaskFinishedListener;
    }

    private LoadingTask() {

    }


    @Override
    protected ArrayList<Weather> doInBackground(String... urls) {

        weatherList = new ArrayList<>();
        dataManager = DataManager.instance();


        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(API_URL).build();
//getting data
        optimizer(client, request);


        return weatherList;
    }


    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        super.onPostExecute(weathers);
        if (!dataManager.isSuccessful() && isOfflineMode) {

            Toast.makeText(context, "you need to turn on Internet connection wile running " +
                    "Thunder Buddy for the first time", Toast.LENGTH_LONG).show();
        }
        finishedListener.onTaskFinished();
    }

    /**
     * trying to access internet for data and in case of failure it's activate the database backup
     *
     * @param client
     * @param request
     */
    private void optimizer(OkHttpClient client, Request request) {
        try {

            Response response = client.newCall(request).execute();
            weatherList = processingManager.processJSON(response.body().string());
            Log.d("E_", "network ");


        } catch (JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
            isOfflineMode = true;
            weatherList = dataManager.getWeatherList();
            Log.d("E_", "database ");
        }
    }


}
