package com.example.mobliesoftware9.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteEasyHelper extends SQLiteOpenHelper
{

      //DB이름
    static final int DB_VERSION = 1;


    public SQLiteEasyHelper(String dbName, @Nullable Context context)
    {
        super(context, dbName, null, DB_VERSION);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //useless
    }
}
