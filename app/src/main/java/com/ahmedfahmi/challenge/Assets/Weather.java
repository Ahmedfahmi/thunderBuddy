package com.ahmedfahmi.challenge.Assets;

/**
 * Created by Ahmed Fahmi on 6/14/2016.
 */
public class Weather {

    private String day;
    private String date;
    private String temperature;

    public Weather(String day, String temperature, String date) {
        this.day = day;
        this.temperature = temperature;
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
