package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.peter.homelessapp.R;
import com.example.peter.homelessapp.model.Administer;
import com.example.peter.homelessapp.model.Shelter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by Peter on 2/11/18.
 */

public class AdminScreenActivity extends AppCompatActivity {
    private Button logout;
    private Administer admin;
    private Button shelterList;

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
        shelterList = (Button) findViewById(R.id.sheltersList);
        shelterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readShelters();
                Intent intent2 = new Intent(AdminScreenActivity.this, ShelterListActivity.class);
                startActivity(intent2);
            }
        });
    }
    private void readShelters() {
        try {
            InputStream is = getResources().openRawResource(R.raw.shelters);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            br1.readLine();
            String s = br1.readLine();
            while ((s=br1.readLine()) != null) {
                String[] tokens = s.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                Integer capacity = Shelter.parseCapacity(tokens[2]);
                Shelter newShelter = new Shelter(tokens[0], tokens[1],
                        capacity, tokens[3], Double.parseDouble(tokens[4]),
                        Double.parseDouble(tokens[5]), tokens[6], tokens[7], tokens[8]);
            }
            br1.close();
        } catch (IOException e) {
            System.out.println("error!");
        }
    }
}
