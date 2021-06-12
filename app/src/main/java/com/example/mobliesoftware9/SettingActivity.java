package com.example.mobliesoftware9;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobliesoftware9.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;


//
public class SettingActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btnSaveProfile;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 세팅과 관련된 액티비티
        // 알림 설정, 닉네임 변경 등 진행
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnSaveProfile = (Button) findViewById(R.id.btnSaveProfile);
        currentUser = User.getInstance();

        username.setText(currentUser.username);


        btnSaveProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String usernameStr = username.getText().toString();
                currentUser.username = usernameStr;
            }
        });



        //navbar variable
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navbar);

        //default selected activity
        bottomNav.setSelectedItemId(R.id.nav_settings);

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
                        startActivity(new Intent(getApplicationContext(), BoardActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_settings:
                        return true;
                }
                return false;
            }
        });
    }

}