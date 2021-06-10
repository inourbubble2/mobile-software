package com.example.mobliesoftware9;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.model.User;

public class LoginActivity extends AppCompatActivity {
    private EditText inUsername;
    private EditText inPassword;
    private Button btnLogin;
    private Button btnGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inUsername = (EditText) findViewById(R.id.inUsername);
        inPassword = (EditText) findViewById(R.id.inPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnGoToRegister = (Button) findViewById(R.id.btnGoToRegister);

        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inUsername.getText().toString();
                String password = inPassword.getText().toString();

                // Check whether the account is valid or not
                // if valid: Go to main
                try {
                    CursorWrapper userCursor = DatabaseManager.GetInstance().SelectRows("User", null, new String[]{"username", " password"}, new String[]{username, password}, null, null);

                    User user = new User();
                    userCursor.mCursor.moveToNext();
                    user.username = userCursor.GetStringData("username");
                    user.password = userCursor.GetStringData("password");
                    user.email = userCursor.GetStringData("email");
                    user.createdAt = userCursor.GetDateData("createdAt");

                    User.setInstance(user);

                    Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();

                }
                // else: Reset the value of edittexts and let user know
                catch (SQLiteException e) {
                    inUsername.setText("");
                    inPassword.setText("");
                    Toast.makeText(getApplicationContext(), "유효하지 않은 계정입니다.", Toast.LENGTH_LONG).show();
                }

            }

        });

        btnGoToRegister.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}
