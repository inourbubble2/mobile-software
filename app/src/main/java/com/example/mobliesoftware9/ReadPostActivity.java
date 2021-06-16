package com.example.mobliesoftware9;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.Image.ImageLoader;
import com.example.mobliesoftware9.Image.LoadedImage;
import com.example.mobliesoftware9.model.Post;
import com.example.mobliesoftware9.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReadPostActivity  extends AppCompatActivity {

    private ViewPager viewPager;
    private SlideAdapter myAdapter;
    Post[] dataSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        // 전체 글 중 최대 최근 10개만 조회
        try {
            User currentUser = User.getInstance();
            CursorWrapper postCursor = DatabaseManager.GetInstance().SelectRows("Post", null, null, null, null, "createdAt");
            postCursor.mCursor.moveToNext();

            int cnt = Integer.min(postCursor.mCursor.getCount(), 10);
            dataSet = new Post[cnt];
            for (int i = 0; i < cnt; i++) {
                Post post = new Post();
                post.mPrimaryKey = postCursor.GetIntegerData("mPrimaryKey");
                post.writerID = postCursor.GetStringData("writerID");
                post.title = postCursor.GetStringData("title");
                post.createdAt = postCursor.GetDateData("createdAt");

                ImageLoader imageLoader = new ImageLoader();
                LoadedImage img = imageLoader.LoadImageFromURL(postCursor.GetStringData("attachedImageURL"));
                post.attachedImg = img;

                dataSet[i] = post;

                postCursor.mCursor.moveToNext();
            }
        } catch (SQLiteException e) {
            Log.e("BoardActivity", "Failed to load posts");
        }

        //post slider
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        myAdapter = new SlideAdapter(this, dataSet);
        viewPager.setAdapter(myAdapter);

        //navbar variable
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navbar);

        //default selected activity
        bottomNav.setSelectedItemId(R.id.nav_readPost);

        //activity간의 이동
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_readPost:
                        return true;
                    case R.id.nav_board:
                        startActivity(new Intent(getApplicationContext(), BoardActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }

}
