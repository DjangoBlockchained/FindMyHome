package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.peter.homelessapp.R;

/**
 * Created by Peter on 2/11/18.
 */

public class ApplicationScreenActivity extends AppCompatActivity {
    private Button logout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_screen);

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ApplicationScreenActivity.this, WelcomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}
