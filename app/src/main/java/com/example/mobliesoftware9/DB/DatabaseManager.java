package com.example.mobliesoftware9.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class DatabaseManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    private static DatabaseManager mInstance = null;
    //싱글톤 패턴으로 구현
    public static DatabaseManager getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new DatabaseManager();
        }

        return mInstance;
    }

    private SQLiteDatabase mDatabase = null;

    public DatabaseManager()
    {
        this.OpenDatabase();
    }

    public void OpenDatabase()
    {
        SQLiteEasyHelper helper = new SQLiteEasyHelper(this); //헬퍼를 생성함
        // DatabaseHelper helper = new DatabaseHelper(this , databaseName, null, 4); //위에거 실행후 이거 실행했을 경우 (이미 해당 디비가있으므로 헬퍼의 update가 호출될것이다.)
        mDatabase = helper.getWritableDatabase();
    }



}
