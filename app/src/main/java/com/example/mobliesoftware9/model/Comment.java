package com.example.mobliesoftware9.model;

import android.content.ContentValues;

import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.DB.DateHelper;

import java.util.Date;
import java.util.Vector;

public class Comment extends DBTable
{
    //int commentID; // Primary Key , replaced by DBTable.mPrimaryKey

    int postID; // Foreign Key
    int parentID; // 답댓글 기능도 구현할 것인지?
    String writerID;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    int likedCount;

    @Override
    public String GetTableName() {
        return "Comment";
    }

    @Override
    public Vector<DatabaseManager.ColumnContainer> GetCreate()
    {
        Vector<DatabaseManager.ColumnContainer> column = new Vector<DatabaseManager.ColumnContainer>();

        column.add(new DatabaseManager.ColumnContainer("postID", "integer"));
        column.add(new DatabaseManager.ColumnContainer("parentID", "integer"));
        column.add(new DatabaseManager.ColumnContainer("writerID", "text"));
        column.add(new DatabaseManager.ColumnContainer("createdAt", "text"));
        column.add(new DatabaseManager.ColumnContainer("updatedAt", "text"));
        column.add(new DatabaseManager.ColumnContainer("deletedAt", "text"));
        column.add(new DatabaseManager.ColumnContainer("likedCount", "integer"));

        return column;
    }

    @Override
    public ContentValues GetCurrentContentValue()
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("postID", this.postID);
        contentValues.put("parentID", this.parentID);
        contentValues.put("writerID", this.writerID);
        contentValues.put("createdAt", DateHelper.DateToString(this.createdAt));
        contentValues.put("updatedAt", DateHelper.DateToString(this.updatedAt));
        contentValues.put("deletedAt", DateHelper.DateToString(this.deletedAt));
        contentValues.put("likedCount", this.likedCount);

        return contentValues;
    }

    @Override
    public void NewlyInsertToDB()
    {

    }



    @Override
    public void DeleteFromDB() {

    }
}
