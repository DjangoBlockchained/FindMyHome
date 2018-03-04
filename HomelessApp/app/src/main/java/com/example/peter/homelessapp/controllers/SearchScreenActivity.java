package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.peter.homelessapp.R;

/**
 * Created by Peter on 3/4/18.
 */

public class SearchScreenActivity extends AppCompatActivity {
    private Button submit;
    private EditText name;
    private RadioGroup ageGroup;
    private RadioGroup genderGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        submit = (Button) findViewById(R.id.applySettings);
        name = (EditText) findViewById(R.id.nameSearch);
        ageGroup = (RadioGroup) findViewById(R.id.ageGroup);
        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);

        if (getIntent().getStringExtra("name") != null) {
            name.setText(getIntent().getStringExtra("name"));
        }

        if (getIntent().getStringExtra("gender") != null) {
            String gender = getIntent().getStringExtra("gender");
            if (gender.equals("male")) {
                genderGroup.check(R.id.genderMale);
            } else if (gender.equals("female")) {
                genderGroup.check(R.id.genderFemale);
            } else if (gender.equals("any")) {
                genderGroup.check(R.id.genderAny);
            } else {
                genderGroup.check(R.id.genderDefault);
            }
        } else {
            genderGroup.check(R.id.genderDefault);
        }

        if (getIntent().getStringExtra("age") != null) {
            String age = getIntent().getStringExtra("age");
            if (age.equals("newborn")) {
                ageGroup.check(R.id.ageNewborn);
            } else if (age.equals("children")) {
                ageGroup.check(R.id.ageChildren);
            } else if (age.equals("ya")) {
                ageGroup.check(R.id.ageYA);
            } else if (age.equals("any")) {
                ageGroup.check(R.id.ageAny);
            } else {
                ageGroup.check(R.id.ageDefault);
            }
        } else {
            ageGroup.check(R.id.ageDefault);
        }

        submit.setOnClickListener((view) -> {
            Intent intent = new Intent(SearchScreenActivity.this, ShelterListActivity.class);
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("gender", genderGroup.getCheckedRadioButtonId());
            intent.putExtra("age", ageGroup.getCheckedRadioButtonId());
            startActivity(intent);
        });
    }
}
