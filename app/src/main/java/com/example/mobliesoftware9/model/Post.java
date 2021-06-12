package com.example.mobliesoftware9.model;

import android.content.ContentValues;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.DB.DateHelper;
import com.example.mobliesoftware9.Image.LoadedImage;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Vector;

public class Post  extends DBTable
{

    //int postID; // Primary Key , replaced by DBTable.mPrimaryKey

    public String writerID; // Primary Key, username of User
    public String title;
    public String content;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public LocalDateTime deletedAt;
    public LoadedImage attachedImg = new LoadedImage();;
    public int viewCount;
    public int likedCount;

    //HashTag는 하나만 저장 ( 여러개 하려니 DB에 저장하기 까다로움 )
    public String hashtag;

    @Override
    public String GetTableName() {
        return "Post";
    }

    @Override
    public Vector<DatabaseManager.ColumnContainer> GetColumnContainersInternal()
    {
        Vector<DatabaseManager.ColumnContainer> column = new Vector<DatabaseManager.ColumnContainer>();

        column.add(new DatabaseManager.ColumnContainer("writerID", "text", true));
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
        contentValues.put("createdAt", DateHelper.DateToString(this.createdAt));
        contentValues.put("updatedAt", DateHelper.DateToString(this.updatedAt));
        contentValues.put("deletedAt", DateHelper.DateToString(this.deletedAt));
        contentValues.put("attachedImageURL", attachedImg.mImageURL);
        contentValues.put("viewCount", this.viewCount);
        contentValues.put("likedCount", this.likedCount);
        contentValues.put("hashtag", this.hashtag);

        return contentValues;
    }

    @Override
    public void LoadFromDB(CursorWrapper cursor) {
        this.mPrimaryKey = cursor.GetIntegerData(DBTable.mPrimaryKeyColumnName);
        this.title = cursor.GetStringData("title");
        this.content = cursor.GetStringData("content");
        this.createdAt = cursor.GetDateData("createdAt");
        this.updatedAt = cursor.GetDateData("updatedAt");
        this.deletedAt = cursor.GetDateData("deletedAt");
        this.attachedImg.mImageURL = cursor.GetStringData("attachedImageURL");
        this.viewCount = cursor.GetIntegerData("viewCount");
        this.likedCount = cursor.GetIntegerData("likedCount");
        this.hashtag = cursor.GetStringData("hashtag");
    }

    Vector<Comment> GetCommentsOfThisPost()
    {
        CursorWrapper commentsCursor = DatabaseManager.GetInstance().SelectRows("Comment", null, new String[]{"postID"}, new String[]{Integer.toString(this.mPrimaryKey)}, null, null);

        if(commentsCursor.mCursor.getCount() == 0)
        {
            return null;
        }
        else
        {
            Vector<Comment> comments = new Vector<Comment>();
            for(int i = 0 ; i < commentsCursor.GetCount() ; i++)
            {
                commentsCursor.MoveToNext();
                Comment comment = new Comment();
                comment.LoadFromDB(commentsCursor);
                comments.add(comment);
            }
            commentsCursor.Close();
            return comments;
        }



    }


}
