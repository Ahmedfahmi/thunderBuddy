package com.ahmedfahmi.challenge.model;

/**
 * Created by Ahmed Fahmi on 6/14/2016.
 */
public class Weather {

    private String day;
    private String date;
    private String temperature;

    public Weather(String weekday, String temperature, String date) {
        this.day = weekday;
        this.temperature = temperature;
        this.date = date;
    }

    public String getDay() {
        return day;
    }


    public String getDate() {
        return date;
    }


    public String getTemperature() {
        return temperature;
    }


}
