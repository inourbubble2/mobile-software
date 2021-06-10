package com.example.mobliesoftware9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.Image.ImageLoader;
import com.example.mobliesoftware9.Image.LoadedImage;
import com.example.mobliesoftware9.model.Post;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        
        // 홈 화면에서 오늘의 이미지 불러오기
        // 탭이나 토글로 글 조회나 세팅 부분으로 이동 가능

        Button btnGoToRegister = (Button) findViewById(R.id.btnGoToRegister);

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_register);
            }
        });

        TestDB();
    }

    void TestDB()
    {
        //Test Code
        //어플 킬 때 마다 기존 DB 초기화함
        this.getApplicationContext().deleteDatabase(DatabaseManager.DB_NAME);

        DatabaseManager.GetInstance().OpenDatabase(this.getApplicationContext());

        //LoadedImage image = new ImageLoader().LoadRandomImage(200, 300);

        Post post = new Post();
        post.writerID = "John";
        post.likedCount = 10;

        Post post1 = new Post();
        post1.writerID = "Max";
        post1.likedCount = 1;

        Post post2 = new Post();
        post2.writerID = "Boy";
        post2.likedCount = 55;

        post.CreateTable();

        post.NewlyInsertToDB();
        post1.NewlyInsertToDB();
        post2.NewlyInsertToDB();

        Log.d("DB Test", "Primary Key1 : " + post.mPrimaryKey);
        Log.d("DB Test", "Primary Key2 : " + post1.mPrimaryKey);
        Log.d("DB Test", "Primary Key3 : " + post2.mPrimaryKey);
    }

}