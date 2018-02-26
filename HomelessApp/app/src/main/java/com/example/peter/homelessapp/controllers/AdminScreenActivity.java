package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.peter.homelessapp.R;
import com.example.peter.homelessapp.model.Administer;

/**
 * Created by Peter on 2/11/18.
 */

public class AdminScreenActivity extends AppCompatActivity {
    private Button logout;
    private Administer admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        admin = (Administer) getIntent().getParcelableExtra("admin");

        logout = (Button) findViewById(R.id.adminLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminScreenActivity.this, WelcomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}
