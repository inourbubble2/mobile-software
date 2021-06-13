package com.example.mobliesoftware9.model;

import android.content.ContentValues;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.DB.DateHelper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Vector;

public class Comment extends DBTable
{
    //int commentID; // Primary Key , replaced by DBTable.mPrimaryKey

    public int postID; // Primary Key, mPrimaryKey of Post!!!!!!

    //public int parentID; // 답댓글 기능도 구현할 것인지?
    public String writerID;
    public String mContent;
    public LocalDateTime createdAt = DateHelper.GetCurrentDate();
    public int likedCount = 0;

    @Override
    public String GetTableName() {
        return "Comment";
    }

    @Override
    public Vector<DatabaseManager.ColumnContainer> GetColumnContainersInternal()
    {
        Vector<DatabaseManager.ColumnContainer> column = new Vector<DatabaseManager.ColumnContainer>();

        column.add(new DatabaseManager.ColumnContainer("postID", "integer", true));
        column.add(new DatabaseManager.ColumnContainer("writerID", "text"));
        column.add(new DatabaseManager.ColumnContainer("mContent", "text"));
        column.add(new DatabaseManager.ColumnContainer("createdAt", "text"));
        column.add(new DatabaseManager.ColumnContainer("likedCount", "integer"));

        return column;
    }

    @Override
    public ContentValues GetCurrentContentValue()
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("postID", this.postID);
        contentValues.put("writerID", this.writerID);
        contentValues.put("mContent", this.mContent);
        contentValues.put("createdAt", DateHelper.DateToString(this.createdAt));
        contentValues.put("likedCount", this.likedCount);

        return contentValues;
    }

    @Override
    protected boolean DataValidChecker()
    {
        if(writerID.isEmpty() || writerID == null)
        {
            return false;
        }
        return true;
    }

    @Override
    public void LoadFromDB(CursorWrapper cursor) {
        this.mPrimaryKey = cursor.GetIntegerData(DBTable.mPrimaryKeyColumnName);
        this.postID = cursor.GetIntegerData("postID");
        this.writerID = cursor.GetStringData("writerID");
        this.mContent = cursor.GetStringData("mContent");
        this.createdAt = cursor.GetDateData("createdAt");
        this.likedCount = cursor.GetIntegerData("likedCount");
    }


    void SetPostID(Post post)
    {
        this.postID = post.mPrimaryKey;
    }



}
