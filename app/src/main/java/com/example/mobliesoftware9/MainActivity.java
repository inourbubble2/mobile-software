package com.example.mobliesoftware9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.Image.ImageLoader;
import com.example.mobliesoftware9.Image.LoadedImage;
import com.example.mobliesoftware9.model.Post;
import com.example.mobliesoftware9.model.User;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    Button btnGoToLogin;
    Button btnGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 홈 화면에서 오늘의 이미지 불러오기
        // 탭이나 토글로 글 조회나 세팅 부분으로 이동 가능

        btnGoToLogin = (Button) findViewById(R.id.btnGoToLogin);
        btnGoToRegister = (Button) findViewById(R.id.btnGoToRegister);

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 된 계정 가져오는 예제
                User loginedUser = User.getInstance();
                
                if (loginedUser == null) { // 로그인 안 된 상태
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    // 로그인 페이지로 이동함
                } else { // 로그인 된 상태
                    Log.d("TEST", "현재 로그인 된 유저 정보 : " + loginedUser.username + ", " + loginedUser.email);
                }
            }
        });

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        TestDB();
    }

    void TestDB() {
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

        //Logcat 보시면 테스트 성공적으로 된걸 보실 수 있습니다.
        Log.d("DB Test", "Primary Key1 : " + post.mPrimaryKey);
        Log.d("DB Test", "Primary Key2 : " + post1.mPrimaryKey);
        Log.d("DB Test", "Primary Key3 : " + post2.mPrimaryKey);

        //cursorWrapper은 조건문에 일치하는 모든 row 들고 있다.
        CursorWrapper cursorWrapper = DatabaseManager.GetInstance().SelectRows(post.GetTableName(), null, null, null, null, null);

        for (int i = 0; i < cursorWrapper.mCursor.getCount(); i++) {
            cursorWrapper.mCursor.moveToNext();//이걸 해줘야 다음 레코드로 넘어가게된다.
            Log.d("DB Test", cursorWrapper.GetStringData("writerID"));

        }
    }
}