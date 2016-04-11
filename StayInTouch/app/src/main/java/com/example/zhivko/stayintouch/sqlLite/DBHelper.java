package com.example.zhivko.stayintouch.sqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Panche on 4/10/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    /*
    creating table in database
     */

    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;

    private static DBHelper instance;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (instance == null)
            instance = new DBHelper(context);
        return instance;
    }

    public static final String TABLE_NAME_NEWS = "NEWS";
    public static final String NEWS_ID = "ID";
    public static final String NEWS_TITLE = "TITLE";
    public static final String NEWS_DESCRIPTION = "DESCRIPTION";
    public static final String NEWS_LINK = "LINK";
    public static final String NEWS_PUBLISH_DATE = "PUBLISH_DATE";
    public static final String NEWS_THUMBNAIL = "THUMBNAIL";

    private static final String CREATE_TABLE_NEWS = "CREATE TABLE " + TABLE_NAME_NEWS +
            "(" + NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NEWS_TITLE + " TEXT NOT NULL, "
                + NEWS_DESCRIPTION + " TEXT, "
                + NEWS_LINK + " TEXT UNIQUE NOT NULL, "
                + NEWS_PUBLISH_DATE + " TEXT, "
                + NEWS_THUMBNAIL + " TEXT);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NEWS);
        onCreate(db);
    }

}
