package com.example.peter.homelessapp.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.peter.homelessapp.R;
import com.example.peter.homelessapp.model.Shelter;

/**
 * Created by sanjanakadiveti on 2/27/18.
 */

public class ShelterDetailActivity extends AppCompatActivity{
    TextView txt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_details);
        TextView txt = (TextView) findViewById(R.id.details);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            int unique_id = (Integer) b.get("Unique ID");
            Shelter toShow = Shelter.getShelter(unique_id);
            String t = "\n" + "\n" + toShow.toString();
            txt.setText(t);
        }
    }
}
