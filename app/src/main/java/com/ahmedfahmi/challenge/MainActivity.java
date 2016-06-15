package com.ahmedfahmi.challenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ahmedfahmi.challenge.Assets.LoadingTask;
import com.ahmedfahmi.challenge.Assets.Weather;
import com.ahmedfahmi.challenge.Assets.WeatherAdapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> dayData = new ArrayList<>();
    ArrayList<String> tempData = new ArrayList<>();
    ArrayList<String> dateData = new ArrayList<>();
    ArrayList<Weather> weatherData = new ArrayList<>();
    WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("W_","Hello");


        LoadingTask loadingTask = new LoadingTask();
        try {
            String me =loadingTask.execute("http://api.wunderground.com/api/838ed9367e8876bf%20/forecast/q/EG/Cairo.json").get();
            Log.i("S_",me);
            dayData = loadingTask.getDateData();
            dateData = loadingTask.getDayData();
            tempData = loadingTask.getTempData();
            weatherData = loadingTask.getWeatherData();


            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
//        weatherData.add(new Weather("sd","ds","dsds"));
//        weatherData.add(new Weather("sd","ds","dsds"));

            weatherAdapter = new WeatherAdapter(weatherData);
            //prepareMovieData();
            recyclerView.setAdapter(weatherAdapter);

            for (Weather x : weatherData){
                Log.i("W_",x.getDate());
            }

            Log.i("W_","Hello2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }








    }
    private void prepareMovieData() {
        Weather movie = new Weather("Mad Max: Fury Road", "Action & Adventure", "2015");
        weatherData.add(movie);

        movie = new Weather("Inside Out", "Animation, Kids & Family", "2015");
        weatherData.add(movie);;

        movie = new Weather("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        weatherData.add(movie);



        weatherAdapter.notifyDataSetChanged();
    }


}
