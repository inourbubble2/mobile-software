package com.example.mobliesoftware9;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseManager extends SQLiteOpenHelper
{
    static final String DB_NAME = "mobileSW9.db";   //DB이름
    static final String TABLE_NAME = "Image"; //Table 이름
    static final int DB_VERSION = 1;			//DB 버전

    Context myContext = null;

    private static DatabaseManager singletonInstance = null;
    private SQLiteDatabase sqLiteDatabase = null;

    //MovieDatabaseManager 싱글톤 패턴으로 구현
    public static DatabaseManager getInstance(Context context)
    {
        if(singletonInstance == null)
        {
            singletonInstance = new DatabaseManager(context);
        }

        return singletonInstance;
    }

    public DatabaseManager(@Nullable Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "imageUrl TEXT" + // "imageUrl TEXT," +
                //"grade TEXT" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //useless
    }
}
