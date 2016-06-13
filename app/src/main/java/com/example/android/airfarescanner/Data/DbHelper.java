package com.example.android.airfarescanner.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anisha on 5/29/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "movieReview.db";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database1) {
        final String SQL_CREATE_AIRPORTS_TABLE = "CREATE TABLE " + Contract.AirportsList.TABLE_NAME + " ( " +
                Contract.AirportsList._id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.AirportsList.Airport_code + " TEXT NOT NULL, " +
                Contract.AirportsList.City + " TEXT NOT NULL, " +
                Contract.AirportsList.Name + " TEXT NOT NULL, " +
                Contract.AirportsList.Latitude + " TEXT NOT NULL, " +
                Contract.AirportsList.Longitude + " TEXT NOT NULL" + ");";

        database1.execSQL(SQL_CREATE_AIRPORTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database1, int oldVersion, int newVersion) {
        database1.execSQL("DROP TABLE IF EXISTS " + Contract.AirportsList.TABLE_NAME);
        onCreate(database1);
    }
}

