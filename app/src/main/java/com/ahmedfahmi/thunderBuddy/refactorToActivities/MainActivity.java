package com.ahmedfahmi.thunderBuddy.refactorToActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ahmedfahmi.thunderBuddy.R;
import com.ahmedfahmi.thunderBuddy.adapter.WeatherAdapter;
import com.ahmedfahmi.thunderBuddy.extra.DividerItemDecoration;
import com.ahmedfahmi.thunderBuddy.managers.LoadingTask;
import com.ahmedfahmi.thunderBuddy.model.Weather;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Weather> weatherList;
    private WeatherAdapter weatherAdapter;
    private LoadingTask loadingTask;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initiate();
        weatherAdapter = new WeatherAdapter(weatherList);
        recyclerView.setAdapter(weatherAdapter);
    }

    private void initiate() {

        weatherList = new ArrayList<>();
        loadingTask = LoadingTask.instance();
        recyclerView = (RecyclerView) findViewById(R.id.rec_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        weatherList = loadingTask.getWeatherList();

    }


}