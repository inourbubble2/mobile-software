package com.example.mobliesoftware9.model;

import android.content.ContentValues;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;

import java.util.Vector;

abstract  public class DBTable
{
    public int mPrimaryKey;
    public static final String mPrimaryKeyColumnName = new String("mPrimaryKey");
    DatabaseManager.ColumnContainer mPrimaryKeyColumnContainer = new DatabaseManager.ColumnContainer(mPrimaryKeyColumnName, "interger", true);



    public abstract String GetTableName();

    public abstract Vector<DatabaseManager.ColumnContainer> GetCreate();

    //GetCurrentContentValue에서는 절대 PrimaryKey 넣으면 안된다.
    public abstract ContentValues GetCurrentContentValue();

    // DB에 새로운 Data를 넣음
    public void NewlyInsertToDB()
    {
        long row = DatabaseManager.GetInstance().InsertNewRow(this.GetTableName(), this.GetCurrentContentValue());
        CursorWrapper cursorHelper = DatabaseManager.GetInstance().SelectRowWithRowID(this.GetTableName(), row);
        this.mPrimaryKey = cursorHelper.GetIntegerData(DBTable.mPrimaryKeyColumnName);
    }

    //Update CurrentData with PrimaryKey
    public void UpdateToDB()
    {
        DatabaseManager.GetInstance().UpdateRows(
                this.GetTableName(),
                this.GetCurrentContentValue(),
                new String[]{DBTable.mPrimaryKeyColumnName},
                new String[]{Integer.toString(this.mPrimaryKey)}
                );
    }

    public abstract void LoadFromDB(CursorWrapper cursor);
    public void LoadFromDB(int primaryKey)
    {
        CursorWrapper cursorHelper = DatabaseManager.GetInstance().SelectRows(this.GetTableName(), null,
                DBTable.mPrimaryKeyColumnName, Integer.toString(primaryKey), null, null);

        this.mPrimaryKey = cursorHelper.GetIntegerData(DBTable.mPrimaryKeyColumnName);
        this.LoadFromDB(cursorHelper);
    }


    public void DeleteFromDBWithPrimaryKey()
    {
        DatabaseManager.GetInstance().DeleteRow(this.GetTableName(), mPrimaryKeyColumnName, this.mPrimaryKey);

    }





}
