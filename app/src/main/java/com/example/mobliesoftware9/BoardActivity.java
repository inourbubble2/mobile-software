package com.example.mobliesoftware9;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.Image.ImageLoader;
import com.example.mobliesoftware9.Image.LoadedImage;
import com.example.mobliesoftware9.model.Post;
import com.example.mobliesoftware9.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    ScrollAdapter adapter;
    Post[] dataSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        // 내가 쓴 글 목록 조회
        try {
            User currentUser = User.getInstance();
            CursorWrapper postCursor = DatabaseManager.GetInstance().SelectRows("Post", null, new String[]{"writerID"}, new String[]{currentUser.username}, null, null);
            postCursor.mCursor.moveToNext();

            dataSet = new Post[postCursor.mCursor.getCount()];
            for (int i = 0; i < postCursor.mCursor.getCount(); i++) {
                Post post = new Post();
                post.LoadFromCursor(postCursor);

                ImageLoader imageLoader = new ImageLoader();
                LoadedImage img = imageLoader.LoadImageFromURL(post.attachedImg.mImageURL);
                post.attachedImg = img;

                dataSet[i] = post;

                postCursor.mCursor.moveToNext();
            }
        } catch (SQLiteException e) {
            Log.e("BoardActivity", "Failed to load posts");
        }

        //user post recycler
        recyclerView = (RecyclerView) findViewById(R.id.postRecycler);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ScrollAdapter(getApplicationContext(), dataSet);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);


        //navbar variable
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navbar);

        //default selected activity
        bottomNav.setSelectedItemId(R.id.nav_board);

        //activity간의 이동
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_readPost:
                        startActivity(new Intent(getApplicationContext(), ReadPostActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_board:
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
