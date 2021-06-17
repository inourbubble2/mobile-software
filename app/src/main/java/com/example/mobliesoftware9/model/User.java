package com.example.mobliesoftware9.model;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.DB.DateHelper;
import com.example.mobliesoftware9.Image.LoadedImage;

import java.time.LocalDateTime;
import java.util.Vector;

public class User extends DBTable {

    public String username;
    public String password;
    public String email;
    public LocalDateTime createdAt = DateHelper.GetCurrentDate();
    public LoadedImage mProfileImage = null;

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
        column.add(new DatabaseManager.ColumnContainer("mProfileImage", "BLOB"));

        return column;
    }

    @Override
    public ContentValues GetCurrentContentValue() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBTable.mPrimaryKeyColumnName, this.mPrimaryKey);
        contentValues.put("username", this.username);
        contentValues.put("password", this.password);
        contentValues.put("email", this.email);
        contentValues.put("createdAt", DateHelper.DateToString(this.createdAt));
        contentValues.put("mProfileImage", this.mProfileImage != null ?
                this.mProfileImage.GetBitmapAsByteArray() : null);

        return contentValues;
    }

    @Override
    public void LoadFromCursor(CursorWrapper cursor) {
        this.mPrimaryKey = cursor.GetIntegerData(DBTable.mPrimaryKeyColumnName);
        this.username = cursor.GetStringData("username");
        this.password = cursor.GetStringData("password");
        this.email = cursor.GetStringData("email");
        this.createdAt = cursor.GetDateData("createdAt");

        if(this.mProfileImage == null)
        {
            this.mProfileImage = new LoadedImage();
        }
        this.mProfileImage.SetImageFromByteArray(cursor.GetByteArrayData("mProfileImage"));
    }

    public Vector<Post> GetPostsWrittenByThisUser()
    {
        CursorWrapper postsCursor = DatabaseManager.GetInstance().SelectRows("Post", null, new String[]{"writerID"}, new String[]{this.username}, null, null);

        if(postsCursor.mCursor.getCount() == 0)
        {
            return null;
        }
        else
        {
            Vector<Post> posts = new Vector<Post>();
            for(int i = 0 ; i < postsCursor.GetCount() ; i++)
            {
                postsCursor.MoveToNext();
                Post post = new Post();
                post.LoadFromCursor(postsCursor);
                posts.add(post);
            }
            postsCursor.Close();
            return posts;
        }
    }

    protected boolean DataValidChecker()
    {
        if(username.isEmpty() || username == null)
        {
            return false;
        }
        if(password.isEmpty() || password == null)
        {
            return false;
        }

        return true;
    }

    public void EditUserImage(byte[] img)
    {
        this.mProfileImage.SetImageFromByteArray(img);
    }



}
