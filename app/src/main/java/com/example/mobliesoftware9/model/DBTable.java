package com.example.mobliesoftware9.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.mobliesoftware9.DB.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.Vector;

abstract  public class DBTable
{
    public int mPrimaryKey;
    DatabaseManager.ColumnContainer mPrimaryKeyColumnContainer = new DatabaseManager.ColumnContainer("mPrimaryKey", "interger", true);



    public abstract String GetTableName();

    public abstract Vector<DatabaseManager.ColumnContainer> GetCreate();

    //GetCurrentContentValue에서는 절대 PrimaryKey 넣으면 안된다.
    public abstract ContentValues GetCurrentContentValue();

    public void NewlyInsertToDB()
    {
        DatabaseManager.GetInstance().InsertNewData(this.GetTableName(), this.GetCurrentContentValue());
    }

    //Update CurrentData with PrimaryKey
    public void UpdateToDB()
    {
        DatabaseManager.GetInstance().UpdateData(
                this.GetTableName(),
                this.GetCurrentContentValue(),
                new String[]{"mPrimaryKey"},
                new String[]{Integer.toString(this.mPrimaryKey)}
                );
    }

    public abstract void LoadFromDB();
    public abstract void DeleteFromDB();




}
