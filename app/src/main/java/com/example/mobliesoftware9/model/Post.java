package com.example.mobliesoftware9.model;

import android.content.ContentValues;

import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.Image.LoadedImage;

import java.util.Date;
import java.util.Vector;

public class Post  extends DBTable
{

    //int postID; // Primary Key , replaced by DBTable.mPrimaryKey

    String writerID;
    String title;
    String content;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    LoadedImage attachedImg;
    int viewCount;
    int likedCount;

    //HashTag는 하나만 저장 ( 여러개 하려니 DB에 저장하기 까다로움 )
    String hashtag;

    @Override
    public String GetTableName() {
        return "Post";
    }

    @Override
    public Vector<DatabaseManager.ColumnContainer> GetCreate()
    {
        Vector<DatabaseManager.ColumnContainer> column = new Vector<DatabaseManager.ColumnContainer>();

        column.add(new DatabaseManager.ColumnContainer("writerID", "text"));
        column.add(new DatabaseManager.ColumnContainer("title", "text"));
        column.add(new DatabaseManager.ColumnContainer("content", "text"));
        column.add(new DatabaseManager.ColumnContainer("createdAt", "text"));
        column.add(new DatabaseManager.ColumnContainer("updatedAt", "text"));
        column.add(new DatabaseManager.ColumnContainer("deletedAt", "text"));
        column.add(new DatabaseManager.ColumnContainer("attachedImageURL", "text"));
        column.add(new DatabaseManager.ColumnContainer("viewCount", "integer"));
        column.add(new DatabaseManager.ColumnContainer("likedCount", "integer"));
        column.add(new DatabaseManager.ColumnContainer("hashtag", "text"));

        return column;
    }

    @Override
    public ContentValues GetCurrentContentValue()
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("writerID", this.writerID);
        contentValues.put("title", this.title);
        contentValues.put("content", this.content);
        contentValues.put("createdAt", mDateFormat.format(this.createdAt));
        contentValues.put("updatedAt", mDateFormat.format(this.updatedAt));
        contentValues.put("deletedAt", mDateFormat.format(this.deletedAt));
        contentValues.put("attachedImageURL", attachedImg.mImageURL);
        contentValues.put("viewCount", this.viewCount);
        contentValues.put("likedCount", this.likedCount);
        contentValues.put("hashtag", this.hashtag);

        return contentValues;
    }

    @Override
    public void InsertOrUpdateToDB()
    {

    }



    @Override
    public void DeleteFromDB() {

    }
}
