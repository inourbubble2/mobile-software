package com.example.mobliesoftware9;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobliesoftware9.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;


//
public class SettingActivity extends AppCompatActivity {

    TextView username;
    User currentUser;

    ShapeableImageView imgUserProfile;
    Button btnUserPicEdit;

    private static final int PICK_IMAGE = 50;
    private void EditCurrentUserProfileImage()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(resultCode, resultCode, data);


        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                currentUser.mProfileImage.mBitmap = selectedImage;
                currentUser.UpdateToDB();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 세팅과 관련된 액티비티
        // 알림 설정, 닉네임 변경 등 진행
        imgUserProfile = (ShapeableImageView) findViewById(R.id.imgUserProfile);
        username = (TextView) findViewById(R.id.username);
        //password = (EditText) findViewById(R.id.password);
        //btnSaveProfile = (Button) findViewById(R.id.btnSaveProfile);
        btnUserPicEdit = (Button) findViewById(R.id.btnUserPicEdit);
        currentUser = User.getInstance();

        username.setText(currentUser.username);

        // 프로필 사진 보여주기

        if (currentUser.mProfileImage.mBitmap != null)
            imgUserProfile.setImageBitmap(currentUser.mProfileImage.mBitmap);

        btnUserPicEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCurrentUserProfileImage();
                imgUserProfile.setImageBitmap(currentUser.mProfileImage.mBitmap);
                Toast.makeText(getApplicationContext(), "프로필 사진 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
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