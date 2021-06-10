package com.example.mobliesoftware9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobliesoftware9.Image.ImageLoader;
import com.example.mobliesoftware9.Image.LoadedImage;

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


        LoadedImage image = new ImageLoader().LoadRandomImage(200, 300);
    }
}