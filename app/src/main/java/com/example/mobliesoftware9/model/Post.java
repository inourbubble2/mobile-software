package com.example.mobliesoftware9.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.DB.DateHelper;
import com.example.mobliesoftware9.Image.LoadedImage;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Vector;

public class Post  extends DBTable
{

    //int postID; // Primary Key , replaced by DBTable.mPrimaryKey

    public String writerID; // Primary Key, username of User
    public String title;
    public String content;
    public LocalDateTime createdAt = DateHelper.GetCurrentDate();
    public LoadedImage attachedImg = new LoadedImage();;
    public int viewCount = 0;
    public int likedCount = 0;

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

        contentValues.put(DBTable.mPrimaryKeyColumnName, this.mPrimaryKey);
        contentValues.put("writerID", this.writerID);
        contentValues.put("title", this.title);
        contentValues.put("content", this.content);
        contentValues.put("createdAt", DateHelper.DateToString(this.createdAt));
        contentValues.put("attachedImageURL", attachedImg.mImageURL);
        contentValues.put("viewCount", this.viewCount);
        contentValues.put("likedCount", this.likedCount);
        contentValues.put("hashtag", this.hashtag);

        return contentValues;
    }

    @Override
    public void LoadFromCursor(CursorWrapper cursor) {
        this.mPrimaryKey = cursor.GetIntegerData(DBTable.mPrimaryKeyColumnName);
        this.title = cursor.GetStringData("title");
        this.content = cursor.GetStringData("content");
        this.createdAt = cursor.GetDateData("createdAt");
        this.attachedImg.mImageURL = cursor.GetStringData("attachedImageURL");
        this.viewCount = cursor.GetIntegerData("viewCount");
        this.likedCount = cursor.GetIntegerData("likedCount");
        this.hashtag = cursor.GetStringData("hashtag");
    }

    protected boolean DataValidChecker()
    {
        if(writerID.isEmpty() || writerID == null)
        {
            return false;
        }
        if(title.isEmpty() || title == null)
        {
            return false;
        }

        return true;
    }

    public Vector<Comment> GetCommentsOfThisPost()
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
                comment.LoadFromCursor(commentsCursor);
                comments.add(comment);
            }
            commentsCursor.Close();
            return comments;
        }
    }

    private void AddComment(Comment newComment)
    {
        newComment.SetPostID(this);
        newComment.NewlyInsertToDB();
    }

    public void AddComment(String writerID, String commentStr)
    {
        Comment newComment = new Comment();

        newComment.writerID = writerID;
        newComment.mContent = commentStr;
        this.AddComment(newComment);
    }



    public void SharePost(Context context)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        this.attachedImg.GetBitmap().compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),  this.attachedImg.GetBitmap(), this.title, this.content);

        Uri uri = Uri.parse(path);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT, this.content);
        context.startActivity(Intent.createChooser(share, "내 감정 공유하기!"));
    }

    private boolean localUserLiked = false;
    public boolean OnClickLikeButton()
    {
        if(localUserLiked == true)
        {
            this.likedCount--;
        }
        else
        {
            this.likedCount++;
        }
        localUserLiked = !localUserLiked;
        this.UpdateToDB();

        return localUserLiked;
    }



}
