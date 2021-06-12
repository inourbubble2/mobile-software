package com.example.mobliesoftware9.model;

import android.content.ContentValues;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.Helper.RandomNumGenerator;

import java.util.Vector;

abstract  public class DBTable
{
    public int mPrimaryKey = RandomNumGenerator.GetRandomPositiveInteger();
    public static final String mPrimaryKeyColumnName = new String("mPrimaryKey");
    DatabaseManager.ColumnContainer mPrimaryKeyColumnContainer = new DatabaseManager.ColumnContainer(mPrimaryKeyColumnName, "interger", true);



    public abstract String GetTableName();

    public void CreateTable()
    {
        Vector<DatabaseManager.ColumnContainer> columnContainers = this.GetColumnContainers();
        DatabaseManager.GetInstance().CreateTable(this.GetTableName(), (columnContainers.toArray(new DatabaseManager.ColumnContainer[columnContainers.size()])));
    }

    public Vector<DatabaseManager.ColumnContainer> GetColumnContainers()
    {
        Vector<DatabaseManager.ColumnContainer> columnContainers = this.GetColumnContainersInternal();
        columnContainers.add(new DatabaseManager.ColumnContainer(mPrimaryKeyColumnName, "integer", true));
        return columnContainers;
    }

    protected abstract Vector<DatabaseManager.ColumnContainer> GetColumnContainersInternal();

    //GetCurrentContentValue에서는 절대 PrimaryKey 넣으면 안된다.
    public abstract ContentValues GetCurrentContentValue();

    protected abstract boolean DataValidChecker();

    // DB에 새로운 Data를 넣음
    public void NewlyInsertToDB()
    {
        if(DataValidChecker() == false)
        {
            new AssertionError("Data is not valid");
        }
        long row = DatabaseManager.GetInstance().InsertNewRow(this.GetTableName(), this.GetCurrentContentValue());
        CursorWrapper cursorHelper = DatabaseManager.GetInstance().SelectRowWithRowID(this.GetTableName(), row);
        cursorHelper.mCursor.moveToFirst();
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
                new String[]{DBTable.mPrimaryKeyColumnName}, new String[]{Integer.toString(primaryKey)}, null, null);

        this.mPrimaryKey = cursorHelper.GetIntegerData(DBTable.mPrimaryKeyColumnName);
        this.LoadFromDB(cursorHelper);
    }


    public void DeleteFromDBWithPrimaryKey()
    {
        DatabaseManager.GetInstance().DeleteRow(this.GetTableName(), mPrimaryKeyColumnName, this.mPrimaryKey);

    }





}
