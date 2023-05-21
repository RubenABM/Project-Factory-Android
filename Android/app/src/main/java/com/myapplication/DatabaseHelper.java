package com.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SmartHelmet";
    public static final String TRIP_TABLE_NAME = "TripDetails";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + TRIP_TABLE_NAME + "(id INTEGER primary key, lat REAL, long REAL, date TEXT, bpm INTEGER)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE_NAME);
        onCreate(db);
    }


}
