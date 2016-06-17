package com.ahmedfahmi.thunderBuddy.refactorToActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ahmedfahmi.thunderBuddy.R;
import com.ahmedfahmi.thunderBuddy.managers.DataManager;
import com.ahmedfahmi.thunderBuddy.managers.LoadingTask;
import com.ahmedfahmi.thunderBuddy.model.Weather;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity implements LoadingTask.LoadingTaskFinishedListener {
    private LoadingTask loadingTask;
    private DataManager dataManager;
    private ArrayList<Weather> weatherList;

    private boolean splashScreenIsActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        initiate();
        loading();
    }

    private void initiate() {
        ImageView splashIcon = (ImageView) findViewById(R.id.spalshIcon);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.splashRelativeLayout);
        splashScreenIsActive = true;
        animateSun(splashIcon, relativeLayout);
        dataManager = DataManager.instance(getApplicationContext());
        weatherList = new ArrayList<>();
        loadingTask = LoadingTask.instance(getApplicationContext(), this);
    }

    /**
     loading data either from internet connection or  database
     */
    private void loading() {
        loadingTask.execute();
    }

    /**
     * animate image along the width of the screen
     * @param splashIcon imageView to be translatedByX
     * @param relativeLayout the parent view of @param splashIcon
     */
    private void animateSun(final ImageView splashIcon, RelativeLayout relativeLayout) {
        final int middle = relativeLayout.getWidth() / 2;

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (splashScreenIsActive) {
                    splashIcon.setTranslationX(middle);
                    splashIcon.animate().translationXBy(1000f).setDuration(2000);
                    handler.postDelayed(this, 1500);//run after 1.5 second
                }
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onTaskFinished() {
        splashScreenIsActive = false;
        moveToForecastActivity();
    }

    private void moveToForecastActivity() {
        Intent intentToForecast = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentToForecast);
        finish();
    }


}
