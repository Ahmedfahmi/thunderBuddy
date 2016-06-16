package com.ahmedfahmi.challenge.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.ahmedfahmi.challenge.model.Weather;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ahmed Fahmi on 6/15/2016.
 */
public class DataManager {


    private Context context;
    private ArrayList<Weather> weatherList;
    private SQLiteDatabase weatherDB;
    private final String DATABASE_NAME = "store";
    private final String TABLE_NAME = "weather";
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (weekday TEXT,temp TEXT, date TEXT)";
    private final String SQL_STATEMENT = "INSERT INTO " + TABLE_NAME + " (weekday,temp,date) VALUES (?,?,?)";
    private final String DELETE_QUERY = "DELETE FROM " + TABLE_NAME;
    private final String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;
    private final String DATE_COLUMN = "date";
    private final String TEMPERATURE_COLUMN = "temp";
    private final String WEEKDAY_COLUMN = "weekday";

    public static DataManager dataManager;
    private boolean successful = false;

    public boolean isSuccessful() {
        return successful;
    }

    private DataManager(Context context) {

        this.context = context;
        weatherDB = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);
        weatherDB.execSQL(CREATE_TABLE);
        weatherList = new ArrayList<>();

    }

    private DataManager() {

    }

    public static DataManager instance(Context context) {

        if (dataManager == null) {
            dataManager = new DataManager(context);
        }

        return dataManager;
    }

    public static DataManager instance() {


        return dataManager;
    }


    public SQLiteDatabase getWeatherDB() {
        return weatherDB;
    }

    public ArrayList<Weather> getWeatherList() {
        callOfflineBackup();

        return weatherList;
    }

    protected void updateDataCenter(String weekday, String temperature, String date) {

        SQLiteStatement statement = weatherDB.compileStatement(SQL_STATEMENT);
        statement.bindString(1, weekday);
        statement.bindString(2, temperature);
        statement.bindString(3, date);
        statement.execute();

    }

    protected void cleanDataCenter() {
        weatherDB.execSQL(DELETE_QUERY);
    }

    /**
     * extract weather data from DataBase
     */
    private void callOfflineBackup() {
        Cursor c = weatherDB.rawQuery(SELECT_ALL_QUERY, null);
        int dateId = c.getColumnIndex(DATE_COLUMN);
        int weekdayId = c.getColumnIndex(WEEKDAY_COLUMN);
        int temperatureId = c.getColumnIndex(TEMPERATURE_COLUMN);
        int size = c.getCount();
        if (size > 0) {
            successful = true;
            c.moveToFirst();
            for (int i = 0; i < 4; i++) {

                String date = c.getString(dateId);
                String temperature = c.getString(temperatureId);
                String weekday = c.getString(weekdayId);

                weatherList.add(new Weather(weekday, temperature, date));

                c.moveToNext();
            }
        }

    }


}
