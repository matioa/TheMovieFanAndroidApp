package com.jataka.themoviefan.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Martin on 11/01/2016.
 */
public class DbInit extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "theMovieFan.db";
    protected SQLiteDatabase db;

    public DbInit(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAN_NOTES_TABLE =
                "CREATE TABLE if not exists " + DbContract.FanNotesEntry.TABLE_NAME + " (" +
                        DbContract.FanNotesEntry._ID + " INTEGER PRIMARY KEY," +
                        DbContract.FanNotesEntry.COLUMN_NAME_MOVIE_ID + " TEXT," +
                        DbContract.FanNotesEntry.COLUMN_NAME_USER + " TEXT," +
                        DbContract.FanNotesEntry.COLUMN_NAME_DATE_ADDED + " TEXT," +
                        DbContract.FanNotesEntry.COLUMN_NAME_RATING + " TEXT," +
                        DbContract.FanNotesEntry.COLUMN_NAME_IMG_URL + " INTEGER" +
                        " )";

        db.execSQL(SQL_CREATE_FAN_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DbContract.FanNotesEntry.TABLE_NAME;

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void open() throws SQLException{
        db = getWritableDatabase();
    }

    public void close(){
        db.close();
    }
}
