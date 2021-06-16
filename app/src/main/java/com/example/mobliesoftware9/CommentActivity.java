
package com.example.mobliesoftware9;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.Image.ImageLoader;
import com.example.mobliesoftware9.Image.LoadedImage;
import com.example.mobliesoftware9.model.Comment;
import com.example.mobliesoftware9.model.Post;
import com.example.mobliesoftware9.model.User;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    CommentScrollAdapter adapter;

    private EditText inputComment;
    private Button btnPostComment;

    Comment[] dataSet;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_popup);

        // 게시글 내용 불러오기
        Intent intent = getIntent();
        int postPrimaryKey = intent.getIntExtra("mPrimaryKey", 0);
        CursorWrapper postCursor = DatabaseManager.GetInstance().SelectRows("Post", null, new String[]{"mPrimaryKey"}, new String[]{Integer.toString(postPrimaryKey)},null, null);
        postCursor.mCursor.moveToNext();
        post = new Post();
        post.mPrimaryKey = postPrimaryKey;
        ImageLoader imageLoader = new ImageLoader();
        post.attachedImg = imageLoader.LoadImageFromURL(postCursor.GetStringData("attachedImageURL"));

        // 댓글 불러오기
        try {
            User currentUser = User.getInstance();
            CursorWrapper commentCursor = DatabaseManager.GetInstance().SelectRows("Comment", null, new String[]{"postID"}, new String[]{Integer.toString(postPrimaryKey)}, null, "createdAt");
            commentCursor.mCursor.moveToNext();

            int cnt = commentCursor.mCursor.getCount();
            dataSet = new Comment[cnt];
            for (int i = 0; i < cnt; i++) {
                Comment comment = new Comment();
                comment.LoadFromCursor(commentCursor);
                dataSet[i] = comment;

                commentCursor.mCursor.moveToNext();
            }
        } catch (SQLiteException e) {
            Log.e("CommentActivity", "Failed to load comments");
        }

        inputComment = (EditText) findViewById(R.id.inputComment);
        btnPostComment = (Button) findViewById(R.id.btnPostComment);
        btnPostComment.setOnClickListener(view -> {
            User currentUser = User.getInstance();
            Comment comment = new Comment();
            comment.postID = postPrimaryKey;
            comment.writerID = currentUser.username;
            comment.mContent = inputComment.getText().toString();

            comment.CreateTable();
            comment.NewlyInsertToDB();

            inputComment.setText("");
            Log.d("DB Test", comment.mPrimaryKey + " 작성 완료");
            Toast.makeText(getApplicationContext(), "작성이 완료되었습니다.", Toast.LENGTH_SHORT);
        });

        recyclerView = (RecyclerView) findViewById(R.id.commentRecycler);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CommentScrollAdapter(this, dataSet, post);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}