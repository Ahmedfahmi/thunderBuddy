package com.ahmedfahmi.thunderBuddy.managers;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ahmedfahmi.thunderBuddy.model.Weather;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Ahmed Fahmi on 6/14/2016.
 */
public class LoadingTask extends AsyncTask<String, Void, ArrayList<Weather>> {

    private static LoadingTask loadingTask;
    private final String API_URL = "http://api.wunderground.com/api/838ed9367e8876bf%20/forecast/q/EG/Cairo.json";
    private final String TOAST_MESSAGE = "you need to turn on Internet connection wile running " +
            "Thunder Buddy for the first time";
    private ProcessingManager processingManager;
    private DataManager dataManager;
    private Context context;

    private ArrayList<Weather> weatherList;


    private boolean isOfflineMode;
    private LoadingTaskFinishedListener finishedListener = null;

    private LoadingTask(Context context, LoadingTaskFinishedListener loadingTaskFinishedListener) {
        isOfflineMode = false;
        processingManager = ProcessingManager.instance();
        this.context = context;
        this.finishedListener = loadingTaskFinishedListener;
    }


    private LoadingTask() {

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

    public ArrayList<Weather> getWeatherList() {
        return weatherList;
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

            Toast.makeText(context, TOAST_MESSAGE, Toast.LENGTH_LONG).show();
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

    public interface LoadingTaskFinishedListener {
        void onTaskFinished();
    }


}
