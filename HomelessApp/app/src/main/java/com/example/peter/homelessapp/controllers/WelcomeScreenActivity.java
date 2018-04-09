package com.example.peter.homelessapp.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.example.peter.homelessapp.R;
import android.content.Intent;

/**
 * The first activity the user sees
 * From here, the user can login/register,
 * and logout
 */
public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        Button login_button = findViewById(R.id.login_button);
        Button register_button = findViewById(R.id.register_button);

        login_button.setOnClickListener((view) -> {
            Intent intent = new Intent(WelcomeScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        });
        register_button.setOnClickListener((view) -> {
            Intent intent2 = new Intent(WelcomeScreenActivity.this, RegisterScreenActivity.class);
            startActivity(intent2);
        });
    }
}
