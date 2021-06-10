package com.example.mobliesoftware9.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.mobliesoftware9.DB.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.Vector;

abstract  public class DBTable
{
    public int mPrimaryKey;
    DatabaseManager.ColumnContainer mPrimaryKeyColumnContainer = new DatabaseManager.ColumnContainer("primaryKey", "interger", true);

    public static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public abstract String GetTableName();

    public abstract Vector<DatabaseManager.ColumnContainer> GetCreate();
    public abstract ContentValues GetCurrentContentValue();
    public abstract void InsertOrUpdateToDB();
    public abstract void LoadFromDB();
    public abstract void DeleteFromDB();




}
