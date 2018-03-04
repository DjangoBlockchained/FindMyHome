package com.example.peter.homelessapp.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.peter.homelessapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by sanjanakadiveti on 2/27/18.
 */

public class ShelterDetailActivity extends AppCompatActivity{
    private ListView list;
    private DatabaseReference shelterRef;

    private ArrayList<String> details = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_details);
        list = findViewById(R.id.shelterdetails);
        adapter = new ArrayAdapter(ShelterDetailActivity.this, android.R.layout.simple_list_item_1, details);
        String name = getIntent().getStringExtra("Shelter Name");
        shelterRef = FirebaseDatabase.getInstance().getReference().child("shelters").child(name);
        shelterRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String string = (String) dataSnapshot.getValue();
                details.add(dataSnapshot.getKey()+ ": "+ string);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        list.setAdapter(adapter);
    }
}
