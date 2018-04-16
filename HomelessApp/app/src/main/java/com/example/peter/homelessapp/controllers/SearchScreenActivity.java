package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.peter.homelessapp.R;


/**
 * Search Screen Activity is an activity that allows user to search for shelter specifications
 * such as gender, age group, name of shelter
 */
public class SearchScreenActivity extends AppCompatActivity {
    private EditText name;
    private RadioGroup ageGroup;
    private RadioGroup genderGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        Button submit = findViewById(R.id.applySettings);
        name = findViewById(R.id.nameSearch);
        ageGroup = findViewById(R.id.ageGroup);
        genderGroup = findViewById(R.id.genderGroup);
        Intent i = getIntent();
        if (i.getStringExtra("name") != null) {
            name.setText(i.getStringExtra("name"));

        }

        loadSearchAge();
        loadSearchGender();

        submit.setOnClickListener((view) -> {
            Intent intent;
            String className = i.getStringExtra("fromClass");
            Class target;
            try {
                target = Class.forName(className);
            } catch (ClassNotFoundException ex) {
                android.util.Log.d("", "Class not found. Cannot exit search");
                return;
            }
            intent = new Intent(SearchScreenActivity.this, target);
            Editable nameText = name.getText();
            intent.putExtra("name", nameText.toString());
            intent.putExtra("gender", genderGroup.getCheckedRadioButtonId());
            intent.putExtra("age", ageGroup.getCheckedRadioButtonId());
            startActivity(intent);
            // finish();
        });
    }
    private void loadSearchAge() {
        Intent i = getIntent();
        if (i.getStringExtra("age") != null) {
            String age = i.getStringExtra("age");
            if ("newborn".equals(age)) {
                ageGroup.check(R.id.ageNewborn);
            } else if ("Children".equals(age)) {
                ageGroup.check(R.id.ageChildren);
            } else if ("Young adults".equals(age)) {
                ageGroup.check(R.id.ageYA);
            } else if ("Any".equals(age)) {
                ageGroup.check(R.id.ageAny);
            } else {
                ageGroup.check(R.id.ageDefault);
            }
        } else {
            ageGroup.check(R.id.ageDefault);
        }
    }
    private void loadSearchGender() {
        Intent i = getIntent();
        if (i.getStringExtra("gender") != null) {
            String gender = i.getStringExtra("gender");
            if ("Men".equals(gender)) {
                genderGroup.check(R.id.genderMale);
            } else if ("Women".equals(gender)) {
                genderGroup.check(R.id.genderFemale);
            } else if ("Any".equals(gender)) {
                genderGroup.check(R.id.genderAny);
            } else {
                genderGroup.check(R.id.genderDefault);
            }
        } else {
            genderGroup.check(R.id.genderDefault);
        }
    }
}
