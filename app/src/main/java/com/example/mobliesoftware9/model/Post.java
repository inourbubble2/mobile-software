package com.example.mobliesoftware9.model;

import com.example.mobliesoftware9.LoadedImage;

import java.util.Date;

public class Post {
    int postID;
    String writerID;
    String title;
    String content;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    LoadedImage attachedImg;
    int viewCount;
    int likedCount;
    String[] hashtags;
}
