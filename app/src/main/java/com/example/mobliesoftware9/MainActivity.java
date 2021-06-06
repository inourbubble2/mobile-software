package com.example.mobliesoftware9;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 홈 화면에서 오늘의 이미지 불러오기
        // 탭이나 토글로 글 조회나 세팅 부분으로 이동 가능

        LoadedImage image = new ImageLoader().LoadImageFromPicsum(200, 300);
    }
}