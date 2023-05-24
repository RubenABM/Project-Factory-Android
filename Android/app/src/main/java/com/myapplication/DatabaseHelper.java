package com.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SmartHelmet";
    public static final String TRIP_TABLE_NAME = "TripDetails";
    public  static final String ID = "id";
    public  static final String GPSLAT = "gpslat";
    public  static final String GPSLONG = "gpslong";
    public  static final String DATETIME = "datetime";
    public  static final String BPM = "bpm";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE " + TRIP_TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GPSLAT + " TEXT,"
                + GPSLONG + " TEXT,"
                + DATETIME + " INTEGER,"
                + BPM + " INTEGER)";

        db.execSQL(query);
    }

    public void addNewCoords(String gpslat, String gpslong){

        SQLiteDatabase db = this.getWritableDatabase();
        long currentTimeMillis = System.currentTimeMillis();
        int timeSinceEpoch = (int) (currentTimeMillis / 1000);

        ContentValues values = new ContentValues();
        values.put(GPSLAT, gpslat);
        values.put(GPSLONG, gpslong);
        values.put(DATETIME, timeSinceEpoch);

        db.insert(TRIP_TABLE_NAME, null, values);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE_NAME);
        onCreate(db);
    }

    public void resetDatabase(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE_NAME);
    }
}