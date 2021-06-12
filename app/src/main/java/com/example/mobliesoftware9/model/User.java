package com.example.mobliesoftware9.model;

import android.content.ContentValues;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.DB.DateHelper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Vector;

public class User extends DBTable {

    public String username;
    public String password;
    public String email;
    public LocalDateTime createdAt;

    private static User instance = null;

    public static synchronized void setInstance(User user) {
        instance = user;
    }

    public static synchronized User getInstance() {
        return instance;
    }

    @Override
    public String GetTableName() { return "User"; }

    @Override
    protected Vector<DatabaseManager.ColumnContainer> GetColumnContainersInternal() {
        Vector<DatabaseManager.ColumnContainer> column = new Vector<>();

        column.add(new DatabaseManager.ColumnContainer("username", "text"));
        column.add(new DatabaseManager.ColumnContainer("password", "text"));
        column.add(new DatabaseManager.ColumnContainer("email", "text"));
        column.add(new DatabaseManager.ColumnContainer("createdAt", "text"));

        return column;
    }

    @Override
    public ContentValues GetCurrentContentValue() {
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", this.username);
        contentValues.put("password", this.password);
        contentValues.put("email", this.email);
        contentValues.put("createdAt", DateHelper.DateToString(this.createdAt));

        return contentValues;
    }

    @Override
    public void LoadFromDB(CursorWrapper cursor) {
        this.mPrimaryKey = cursor.GetIntegerData(DBTable.mPrimaryKeyColumnName);
        this.username = cursor.GetStringData("username");
        this.password = cursor.GetStringData("password");
        this.email = cursor.GetStringData("email");
        this.createdAt = cursor.GetDateData("createdAt");
    }
}
