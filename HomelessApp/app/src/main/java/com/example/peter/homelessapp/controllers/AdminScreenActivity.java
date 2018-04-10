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
 * Shows options for Admin user.
 */
public class AdminScreenActivity extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);
        username = getIntent().getStringExtra("username");
        //admin = (Administer) getIntent().getParcelableExtra("admin");

        Button logout = findViewById(R.id.adminLogout);
        logout.setOnClickListener((View view) -> {
            Intent intent
                    = new Intent(AdminScreenActivity.this,
                    WelcomeScreenActivity.class);
            startActivity(intent);
        });
        logout = findViewById(R.id.adminLogout);
        logout.setOnClickListener((View view) -> finish());

        Button shelterList = findViewById(R.id.sheltersList);
        shelterList.setOnClickListener((View view) -> {
            readShelters();
            Intent intent2
                    = new Intent(AdminScreenActivity.this, ShelterListActivity.class);
            intent2.putExtra("username", username);
            startActivity(intent2);
        });
        shelterList = findViewById(R.id.sheltersList);
        shelterList.setOnClickListener((View view) -> {
                readShelters();
                Intent intent2 = new Intent(AdminScreenActivity.this, ShelterListActivity.class);
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
