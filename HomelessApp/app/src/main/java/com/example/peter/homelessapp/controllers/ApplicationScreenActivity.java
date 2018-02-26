package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.peter.homelessapp.R;
import com.example.peter.homelessapp.model.Administer;
import com.example.peter.homelessapp.model.HomelessUser;
import com.example.peter.homelessapp.model.User;

/**
 * Created by Peter on 2/11/18.
 */

public class ApplicationScreenActivity extends AppCompatActivity {
    private Button logout;
    private HomelessUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_screen);

        user = (HomelessUser) getIntent().getParcelableExtra("user");
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
