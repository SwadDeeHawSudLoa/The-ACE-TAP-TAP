package com.example.mytermproject;

import static android.provider.BaseColumns._ID;

import static com.example.mytermproject.Constants.DATE;
import static com.example.mytermproject.Constants.LEVEL;
import static com.example.mytermproject.Constants.TABLE_NAME;
import static com.example.mytermproject.Constants.scoreTap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventData extends SQLiteOpenHelper {
    public EventData(Context ctx){super(ctx,"event.db",null,1);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + _ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " TEXT, "
                + scoreTap + " INTEGER, "
                + LEVEL + " INTETER )"  );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS events");
        onCreate(db);
    }
}
