package com.ahmedfahmi.thunderBuddy.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;

import com.ahmedfahmi.thunderBuddy.model.Weather;

import java.util.ArrayList;

/**
 * Created by Ahmed Fahmi on 6/15/2016.
 */
public class DataManager {


    public static DataManager dataManager;
    private final String DATABASE_NAME = "STORE";
    private final String TABLE_NAME = "weather5";
    private final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS ";
    private final String TABLE_COLUMNS = " (weekday TEXT,highTemp TEXT, lowTemp TEXT, date TEXT, condition TEXT,icon TEXT)";
    private final int INDEX_WEEKDAY = 1;
    private final int INDEX_HIGH_TEMP = 2;
    private final int INDEX_LOW_TEMP = 3;
    private final int INDEX_DATE = 4;
    private final int INDEX_CONDITION = 5;
    private final int INDEX_ICON = 6;

    private final String SQL_STATEMENT = "INSERT INTO " + TABLE_NAME +
            " (weekday,highTemp,lowTemp, date,condition,icon) VALUES (?,?,?,?,?,?)";

    private final String CREATE_TABLE_QUERY = CREATE_QUERY + TABLE_NAME + TABLE_COLUMNS;
    private final String DELETE_QUERY = "DELETE FROM " + TABLE_NAME;
    private final String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;

    private final String COLUMN_DATE = "date";
    private final String COLUMN_HIGH_TEMPERATURE = "highTemp";
    private final String COLUMN_LOW_TEMPERATURE = "lowTemp";
    private final String COLUMN_WEEKDAY = "weekday";
    private final String COLUMN_CONDITION = "condition";
    private final String COLUMN_ICON = "icon";

    ImageManager imageManager;
    private Context context;
    private ArrayList<Weather> weatherList;
    private SQLiteDatabase weatherDB;
    private boolean successful = false;

    private DataManager(Context context) {

        this.context = context;
        weatherDB = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);
        weatherDB.execSQL(CREATE_TABLE_QUERY);
        weatherList = new ArrayList<>();
        imageManager = ImageManager.instance();

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

    public boolean isSuccessful() {
        return successful;
    }

    public SQLiteDatabase getWeatherDB() {
        return weatherDB;
    }

    public ArrayList<Weather> getWeatherList() {
        callOfflineBackup();

        return weatherList;
    }

    protected void updateDataCenter(String weekday, String highTemperature, String lowTemperature, String date, String condition, String icon) {

        SQLiteStatement statement = weatherDB.compileStatement(SQL_STATEMENT);
        statement.bindString(INDEX_WEEKDAY, weekday);
        statement.bindString(INDEX_HIGH_TEMP, highTemperature);
        statement.bindString(INDEX_LOW_TEMP, lowTemperature);
        statement.bindString(INDEX_DATE, date);
        statement.bindString(INDEX_CONDITION, condition);
        statement.bindString(INDEX_ICON, icon);
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
        int dateId = c.getColumnIndex(COLUMN_DATE);
        int weekdayId = c.getColumnIndex(COLUMN_WEEKDAY);
        int highTemperatureId = c.getColumnIndex(COLUMN_HIGH_TEMPERATURE);
        int lowTemperatureId = c.getColumnIndex(COLUMN_LOW_TEMPERATURE);
        int conditionId = c.getColumnIndex(COLUMN_CONDITION);
        int iconId = c.getColumnIndex(COLUMN_ICON);
        int size = c.getCount();
        if (size > 0) {
            successful = true;
            c.moveToFirst();
            for (int i = 0; i < 4; i++) {

                String date = c.getString(dateId);
                String highTemperature = c.getString(highTemperatureId);
                String lowTemperature = c.getString(lowTemperatureId);
                String weekday = c.getString(weekdayId);
                String condition = c.getString(conditionId);
                String icon = c.getString(iconId);
                Bitmap iconBitmap = imageManager.toBitmap(icon);


                weatherList.add(new Weather(weekday, highTemperature, lowTemperature, date, condition, iconBitmap));

                c.moveToNext();
            }
        }

    }


}
