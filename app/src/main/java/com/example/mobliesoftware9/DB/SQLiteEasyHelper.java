package com.example.mobliesoftware9.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteEasyHelper extends SQLiteOpenHelper
{

    static final String DB_NAME = "mobileSW9.db";   //DB이름
    static final String TABLE_NAME = "Image"; //Table 이름
    static final int DB_VERSION = 1;

    Context myContext = null;




    public SQLiteEasyHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME +
                "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "IMAGE_URL TEXT" +
                // "imageUrl TEXT," +
                //"grade TEXT" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //useless
    }
}
