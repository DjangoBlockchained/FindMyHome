package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.example.peter.homelessapp.R;

import com.example.peter.homelessapp.model.Shelter;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Shows the options for a signed-in HomelessUser.
 */
public class ApplicationScreenActivity extends AppCompatActivity {
    private String username;
    private boolean shouldReadShelters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_screen);
        shouldReadShelters = false;

        username = getIntent().getStringExtra("username");
        //user = (HomelessUser) getIntent().getParcelableExtra("user");
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener((View view) -> finish());
        Button shelterList = findViewById(R.id.shelterListButton);
        shelterList.setOnClickListener((View view) -> {
            if (shouldReadShelters) {
                readShelters();
            }
            Intent intent2
                    = new Intent(ApplicationScreenActivity.this,
                    ShelterListActivity.class);
            intent2.putExtra("username", username);
            startActivity(intent2);
        });

        Button mapView = findViewById(R.id.mapViewButton);
        mapView.setOnClickListener((View view) -> {
            Intent intent2
                    = new Intent(ApplicationScreenActivity.this, MapsActivity.class);
            intent2.putExtra("username", username);
            startActivity(intent2);
        });
        mapView = findViewById(R.id.mapViewButton);
        mapView.setOnClickListener((View view) -> {
            Intent intent2
                    = new Intent(ApplicationScreenActivity.this, MapsActivity.class);
            intent2.putExtra("username", username);
            startActivity(intent2);
        });
    }
    private void readShelters() {
        try {
            InputStream is = getResources().openRawResource(R.raw.shelters);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is,
                    StandardCharsets.UTF_8));
            br1.readLine();
            String s = br1.readLine();
            while (s != null) {
                String[] tokens = s.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                Integer capacity = Shelter.parseCapacity(tokens[2]);
                new Shelter(tokens[0], tokens[1],
                        capacity, tokens[3], Double.parseDouble(tokens[4]),
                        Double.parseDouble(tokens[5]), tokens[6], tokens[7], tokens[8]);
                s = br1.readLine();
            }
            br1.close();
        } catch (IOException e) {
            android.util.Log.d("", "error!");
        }
    }
}
