package com.ahmedfahmi.thunderBuddy.managers;

import android.graphics.Bitmap;

import com.ahmedfahmi.thunderBuddy.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmed Fahmi on 6/15/2016.
 */
public class ProcessingManager {
    public static ProcessingManager processingManager;
    private final String TEMPERATURE_HIGH = "high";
    private final String TEMPERATURE_LOW = "low";
    private final String TEMPERATURE_CELSIUS = "celsius";
    private final String FORECAST_DATE = "date";
    private final String FORECAST_DAY = "day";
    private final String FORECAST_MONTH = "monthname";
    private final String DATE_WEEKDAY_SHORT = "weekday_short";
    private final String FORECAST = "forecast";
    private final String FORECAST_SIMPLE = "simpleforecast";
    private final String FORECASTDAY = "forecastday";
    private final String CONDITIONS = "conditions";
    private final String ICON_URL = "icon_url";
    private final String DEGREE = "\u00b0C";
    private ArrayList<Weather> weatherList;
    private DataManager dataManager;
    private ImageManager imageManager;


    private ProcessingManager() {
        weatherList = new ArrayList<>();
        dataManager = DataManager.instance();
        imageManager = ImageManager.instance();

    }

    public static ProcessingManager instance() {
        if (processingManager == null) {
            processingManager = new ProcessingManager();
        }
        return processingManager;
    }

    /**
     * extract data from json object and update DataBase
     *
     * @param String
     * @return ArrayList of weather data
     * @throws JSONException
     */
    protected ArrayList<Weather> processJSON(String String) throws JSONException {
        String hightTemperatureInCelsius;
        String lowTemperatureInCelsius;
        dataManager.cleanDataCenter();

        JSONObject weatherObject = new JSONObject(String);

        JSONObject weatherForecastObject = weatherObject.getJSONObject(FORECAST);

        JSONObject simpleForecastObject = weatherForecastObject.getJSONObject(FORECAST_SIMPLE);

        JSONArray forecastDayArray = simpleForecastObject.getJSONArray(FORECASTDAY);

        for (int i = 0; i < 4; i++) {
            JSONObject forecastDayItem = forecastDayArray.getJSONObject(i);
            //highTemperature
            JSONObject highTemperatureObject = forecastDayItem.getJSONObject(TEMPERATURE_HIGH);
            JSONObject lowTemperatureObject = forecastDayItem.getJSONObject(TEMPERATURE_LOW);
            hightTemperatureInCelsius = highTemperatureObject.getString(TEMPERATURE_CELSIUS) + DEGREE;
            lowTemperatureInCelsius = lowTemperatureObject.getString(TEMPERATURE_CELSIUS) + DEGREE;

            //weather condition
            String condition = forecastDayItem.getString(CONDITIONS);

            //icon image
            String iconUrl = forecastDayItem.getString(ICON_URL);
            Bitmap iconBitmap = imageManager.downLoadBitmap(iconUrl);


            //date
            JSONObject dateObject = forecastDayItem.getJSONObject(FORECAST_DATE);
            String weekdayShort = dateObject.getString(DATE_WEEKDAY_SHORT);
            String date = dateObject.getString(FORECAST_DAY);
            String month = dateObject.getString(FORECAST_MONTH);

            String fullDate = date + " " + month;


            //getting real time data
            weatherList.add(new Weather(weekdayShort, hightTemperatureInCelsius, lowTemperatureInCelsius, fullDate, condition, iconBitmap));


            //update DataBase
            String iconString = imageManager.convertBitmapToString(iconBitmap);
            dataManager.updateDataCenter(weekdayShort, hightTemperatureInCelsius, lowTemperatureInCelsius, fullDate, condition, iconString);


        }

        return weatherList;
    }
}
