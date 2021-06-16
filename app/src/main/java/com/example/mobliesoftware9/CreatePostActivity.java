package com.example.mobliesoftware9;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.PointerIcon;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobliesoftware9.model.Post;
import com.example.mobliesoftware9.model.User;

public class CreatePostActivity extends AppCompatActivity {

    Button btnSetPost;
    Button btnCancelPost;

    EditText txtUserPostTitle;
    EditText txtUserPostContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_popup);

        // 글 작성
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //getWindow().setLayout((int)(width*0.8), (int)(height*0.6));
        getWindow().setLayout((int)(width), (int)(height));

        btnSetPost = (Button) findViewById(R.id.btnSetPost);
        btnCancelPost = (Button) findViewById(R.id.btnCancelPost);
        txtUserPostTitle = (EditText) findViewById(R.id.txtUserPostTitle);
        txtUserPostContent = (EditText) findViewById(R.id.txtUserPostContent);

        btnSetPost.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String title = txtUserPostTitle.getText().toString();
                String content = txtUserPostContent.getText().toString();

                if (title.equals("") || content.equals("")) {
                    Toast.makeText(getApplicationContext(), "입력을 모두 해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    User currentUser = User.getInstance();
                    Post post = new Post();
                    post.writerID = currentUser.username;
                    post.title = title;
                    post.content = content;

                    post.CreateTable();
                    post.NewlyInsertToDB();

                    Toast.makeText(getApplicationContext(), "작성이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    Log.d("DB Test", post.mPrimaryKey + " : " + post.title + " by " + post.writerID);
                    finish();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnCancelPost.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}