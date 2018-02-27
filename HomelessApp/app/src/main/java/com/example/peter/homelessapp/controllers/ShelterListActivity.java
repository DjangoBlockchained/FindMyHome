package com.example.peter.homelessapp.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.peter.homelessapp.R;
import com.example.peter.homelessapp.model.Shelter;

import java.util.List;

/**
 * Created by sanjanakadiveti on 2/26/18.
 */

public class ShelterListActivity extends AppCompatActivity{
    private ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);
        populateListView();
    }
    private void populateListView() {
        list = (ListView) findViewById(R.id.shelterList);
        List<String> shelters = SimpleModel.INSTANCE.shelterNameList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShelterListActivity.this, android.R.layout.simple_list_item_1, shelters);
        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) list.getItemAtPosition(position);
            int unique_id = Integer.parseInt(selected.substring(0, selected.indexOf(" ")));
            Intent intent = new Intent(ShelterListActivity.this, ShelterDetailActivity.class);
            intent.putExtra("Unique ID", unique_id);
            startActivity(intent);
        });
    }
}
