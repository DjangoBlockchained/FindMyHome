package com.example.peter.homelessapp.controllers;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.example.peter.homelessapp.R;
import android.content.Intent;
//import android.support.design.widget.Snackbar;
/**
 * Created by Peter on 2/11/18.
 */

public class WelcomeScreenActivity extends AppCompatActivity {
    private Button login_button;
    private Button register_button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);

        login_button.setOnClickListener((view) -> {
            Intent intent = new Intent(WelcomeScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent);
        });
        register_button.setOnClickListener((view) -> {
            Intent intent2 = new Intent(WelcomeScreenActivity.this, LoginScreenActivity.class);
            startActivity(intent2);
        });
    }
}
