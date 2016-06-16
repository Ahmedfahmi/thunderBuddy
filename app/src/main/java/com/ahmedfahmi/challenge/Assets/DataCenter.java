package com.ahmedfahmi.challenge.Assets;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

/**
 * Created by Ahmed Fahmi on 6/15/2016.
 */
public class DataCenter {


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

    public static DataCenter dataCenter;

    private DataCenter(Context context) {

        this.context = context;
        weatherDB = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);
        weatherDB.execSQL(CREATE_TABLE);
        weatherList = new ArrayList<>();

    }

    private DataCenter() {

    }

    public static DataCenter instance(Context context) {
        if (dataCenter == null) {
            dataCenter = new DataCenter(context);
        }

        return dataCenter;
    }

    public static DataCenter instance() {


        return dataCenter;
    }


    public SQLiteDatabase getWeatherDB() {
        return weatherDB;
    }

    public ArrayList<Weather> getWeatherList() {
        callOfflineBackup();
        return weatherList;
    }

    public void updateDataCenter(String weekday, String temperature, String date) {

        SQLiteStatement statement = weatherDB.compileStatement(SQL_STATEMENT);
        statement.bindString(1, weekday);
        statement.bindString(2, temperature);
        statement.bindString(3, date);
        statement.execute();

    }

    public void cleanDataCenter() {
        weatherDB.execSQL(DELETE_QUERY);
    }

    /**
     * extract weather data from DataBase
     */
    public void callOfflineBackup() {
        Cursor c = weatherDB.rawQuery(SELECT_ALL_QUERY, null);
        int dateId = c.getColumnIndex(DATE_COLUMN);
        int weekdayId = c.getColumnIndex(WEEKDAY_COLUMN);
        int temperatureId = c.getColumnIndex(TEMPERATURE_COLUMN);
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
