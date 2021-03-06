package com.example.mobliesoftware9;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobliesoftware9.Image.ImageLoader;
import com.example.mobliesoftware9.Image.LoadedImage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private Button btnGoToWritePost;
    private ImageView loadedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 오늘의 사진이나 설정, 글조희 등 기능들로 이동
        btnGoToWritePost = (Button) findViewById(R.id.btnGoToWritePost);
        loadedImage = (ImageView) findViewById(R.id.loadedImage);

        // 오늘의 사진 불러오기
        // 현재
        ImageLoader imageLoader  = new ImageLoader();
        LoadedImage img = imageLoader.LoadRandomImage(500, 500);
        loadedImage.setImageBitmap(img.mBitmap);

        btnGoToWritePost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CreatePostActivity.class);
                intent.putExtra("imgUrl", img.mImageURL);
                startActivity(intent);
            }
        });


        //navbar variable
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navbar);

        //default selected activity
        bottomNav.setSelectedItemId(R.id.nav_home);

        //activity간의 이동
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_readPost:
                        startActivity(new Intent(getApplicationContext(), ReadPostActivity.class));
                        overridePendingTransition(0,0);
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