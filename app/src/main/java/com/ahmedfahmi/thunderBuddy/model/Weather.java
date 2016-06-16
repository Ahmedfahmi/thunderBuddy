package com.ahmedfahmi.thunderBuddy.model;

import android.graphics.Bitmap;

/**
 * Created by Ahmed Fahmi on 6/14/2016.
 */
public class Weather {

    private String day;
    private String date;
    private String highTemperature;
    private String condition;
    private String lowTemperature;
    private Bitmap iconImage;

    public Weather(String weekday, String highTemperature, String lowTemperature, String date, String condition, Bitmap iconImage) {
        this.day = weekday;
        this.highTemperature = highTemperature;
        this.date = date;
        this.condition = condition;
        this.iconImage = iconImage;
        this.lowTemperature = lowTemperature;
    }

    public String getLowTemperature() {
        return lowTemperature;
    }

    public String getCondition() {
        return condition;
    }

    public String getDay() {
        return day;
    }


    public String getDate() {
        return date;
    }

    public Bitmap getIconImage() {
        return iconImage;
    }

    public String getHighTemperature() {
        return highTemperature;
    }


}
