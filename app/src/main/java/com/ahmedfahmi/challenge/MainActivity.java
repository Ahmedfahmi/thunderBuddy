package com.ahmedfahmi.challenge;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.ahmedfahmi.challenge.Assets.DividerItemDecoration;
import com.ahmedfahmi.challenge.Assets.LoadingTask;
import com.ahmedfahmi.challenge.Assets.Weather;
import com.ahmedfahmi.challenge.adapter.WeatherAdapter;


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
        setContentView(R.layout.activity_main);


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
