package com.example.mobliesoftware9.model;

import android.content.ContentValues;

import com.example.mobliesoftware9.DB.DatabaseManager;

import java.util.Vector;

abstract  public class DBTable
{
    public int mPrimaryKey;
    public static String mPrimaryKeyColumnName = new String("mPrimaryKey");
    DatabaseManager.ColumnContainer mPrimaryKeyColumnContainer = new DatabaseManager.ColumnContainer(mPrimaryKeyColumnName, "interger", true);



    public abstract String GetTableName();

    public abstract Vector<DatabaseManager.ColumnContainer> GetCreate();

    //GetCurrentContentValue에서는 절대 PrimaryKey 넣으면 안된다.
    public abstract ContentValues GetCurrentContentValue();

    public void NewlyInsertToDB()
    {
        DatabaseManager.GetInstance().InsertNewRow(this.GetTableName(), this.GetCurrentContentValue());
    }

    //Update CurrentData with PrimaryKey
    public void UpdateToDB()
    {
        DatabaseManager.GetInstance().UpdateRows(
                this.GetTableName(),
                this.GetCurrentContentValue(),
                new String[]{"mPrimaryKey"},
                new String[]{Integer.toString(this.mPrimaryKey)}
                );
    }

    public abstract void LoadFromDB();
    public void DeleteFromDB()
    {
        DatabaseManager.GetInstance().DeleteRow(this.GetTableName(), mPrimaryKeyColumnName, this.mPrimaryKey);

    }





}
