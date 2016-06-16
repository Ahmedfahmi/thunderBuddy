package com.ahmedfahmi.challenge.Assets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmed Fahmi on 6/15/2016.
 */
public class Processor {
    public static Processor processor;
    private final String TEMPERATURE_HIGH = "high";
    private final String TEMPERATURE_CELSIUS = "celsius";
    private final String FORECAST_DATE = "date";
    private final String FORECAST_DAY = "day";
    private final String DATE_WEEKDAY_SHORT = "weekday_short";
    private final String FORECAST = "forecast";
    private final String FORECAST_SIMPLE = "simpleforecast";
    private final String FORECASTDAY = "forecastday";
    private final String DEGREE = "\u00b0";
    private ArrayList<Weather> weatherList;
    private DataCenter dataCenter;

    private Processor() {
        weatherList = new ArrayList<>();
        dataCenter = DataCenter.instance();
    }

    public static Processor instance() {
        if (processor == null) {
            processor = new Processor();
        }
        return processor;
    }

    /**
     * extract data from json object and update DataBase
     * @param String
     * @return ArrayList of weather data
     * @throws JSONException
     */
    public ArrayList<Weather> processJSON(String String) throws JSONException {
        String temperatureInCelsius;
        dataCenter.cleanDataCenter();

        JSONObject weatherObject = new JSONObject(String);

        JSONObject weatherForecastObject = weatherObject.getJSONObject(FORECAST);

        JSONObject simpleForecastObject = weatherForecastObject.getJSONObject(FORECAST_SIMPLE);

        JSONArray forecastDayArray = simpleForecastObject.getJSONArray(FORECASTDAY);

        for (int i = 0; i < 4; i++) {
            JSONObject forecastDayItem = forecastDayArray.getJSONObject(i);

            JSONObject highTemperatureObject = forecastDayItem.getJSONObject(TEMPERATURE_HIGH);

            temperatureInCelsius = highTemperatureObject.getString(TEMPERATURE_CELSIUS) + DEGREE;

            JSONObject dateObject = forecastDayItem.getJSONObject(FORECAST_DATE);

            String weekdayShort = dateObject.getString(DATE_WEEKDAY_SHORT);

            String date = dateObject.getString(FORECAST_DAY);


            //getting real time data
            weatherList.add(new Weather(weekdayShort, temperatureInCelsius, date));


            //update DataBase
            dataCenter.updateDataCenter(weekdayShort, temperatureInCelsius, date);


        }

        return weatherList;
    }
}
