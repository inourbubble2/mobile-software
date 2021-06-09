package com.example.mobliesoftware9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText inUsername;
    private EditText inPassword;
    private EditText inCheckPassword;
    private EditText inEmail;
    private Button btnRegister;
    private Button btnGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inUsername = (EditText) findViewById(R.id.inUsername);
        inPassword = (EditText) findViewById(R.id.inPassword);
        inCheckPassword = (EditText) findViewById(R.id.inCheckPassword);
        inEmail = (EditText) findViewById(R.id.inEmail);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnGoToLogin = (Button) findViewById(R.id.btnGoToLogin);

        btnRegister.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                String username = inUsername.getText().toString();
                String password = inPassword.getText().toString();
                String checkPassword = inCheckPassword.getText().toString();
                String email = inEmail.getText().toString();

                // Check Null
                if (username.equals("") || password.equals("") || checkPassword.equals("") || email.equals("")) {
                    // Alert
                    return;
                }

                // Check ID and E-mail duplication from DB

                // Check passwords
                if (!password.equals(checkPassword)) {
                    return;
                }

                // Save to DB

                // Let user know the registration is completed

            }


        });

        btnGoToLogin.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
