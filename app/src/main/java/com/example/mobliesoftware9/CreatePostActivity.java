package com.example.mobliesoftware9;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePostActivity extends AppCompatActivity {

    Button btnSetPost;
    Button btnCancelPost;

    EditText txtUserPost;

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
        txtUserPost = (EditText) findViewById(R.id.txtUserPost);

        btnSetPost.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

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