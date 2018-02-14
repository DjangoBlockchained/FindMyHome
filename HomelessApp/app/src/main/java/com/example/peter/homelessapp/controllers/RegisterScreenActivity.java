package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.peter.homelessapp.R;

/**
 * Created by sanjanakadiveti on 2/11/18.
 */

public class RegisterScreenActivity extends AppCompatActivity {

    private Button cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        cancel = (Button) findViewById(R.id.cancel2);
        cancel.setOnClickListener((view) -> {
            Intent intent = new Intent(RegisterScreenActivity.this, WelcomeScreenActivity.class);
            startActivity(intent);
        });
    }
}
