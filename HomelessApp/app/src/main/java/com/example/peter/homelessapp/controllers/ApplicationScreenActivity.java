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
import com.example.peter.homelessapp.model.Shelter;
import com.example.peter.homelessapp.model.User;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by Peter on 2/11/18.
 */

public class ApplicationScreenActivity extends AppCompatActivity {
    private Button logout;
    private HomelessUser user;
    private Button shelterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //System.out.println("Application Screen");
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
        shelterList = (Button) findViewById(R.id.shelterListButton);
        shelterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readShelters();
                List<String> shelters = SimpleModel.INSTANCE.shelterNameList();
                for (String s : shelters) {
                    System.out.println(s);
                }
                Intent intent2 = new Intent(ApplicationScreenActivity.this, ShelterListActivity.class);
                startActivity(intent2);
            }
        });
    }
    private void readShelters() {
        SimpleModel model = SimpleModel.INSTANCE;
        try {
            InputStream is = getResources().openRawResource(R.raw.shelters);

            BufferedReader br1 = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            br1.readLine();
            String s = br1.readLine();
            while ((s=br1.readLine()) != null) {
                String[] tokens = s.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                model.addShelter(new Shelter(Integer.parseInt(tokens[0]), tokens[1],
                        (tokens[2]), tokens[3], (tokens[4]),
                        (tokens[5]), tokens[6], tokens[7], tokens[8]));
            }
            br1.close();
        } catch (IOException e) {
            System.out.println("error!");
        }
    }
}
