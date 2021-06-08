package com.example.mobliesoftware9.model;

import java.util.Date;

public class Comment {
    int commentID;
    int postID;
    int parentID; // 답댓글 기능도 구현할 것인지?
    String writerID;
    Date deletedAt;
    int likedCount;
}
