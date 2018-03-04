package com.example.peter.homelessapp.controllers;

import android.content.Intent;
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
import java.util.Map;

/**
 * Created by sanjanakadiveti on 2/26/18.
 */

public class ShelterListActivity extends AppCompatActivity{
    private ListView list;
    private DatabaseReference shelterRef;

    private ArrayList<String> names = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);
        shelterRef = FirebaseDatabase.getInstance().getReference().child("shelters");
        adapter = new ArrayAdapter(ShelterListActivity.this, android.R.layout.simple_list_item_1, names);
        list = findViewById(R.id.shelterList);
        shelterRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> string = (Map<String, Object> )dataSnapshot.getValue();
                names.add(string.get("name").toString());
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
                System.out.println("Database Error!");
            }
        });
        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) list.getItemAtPosition(position);
            Intent intent = new Intent(ShelterListActivity.this, ShelterDetailActivity.class);
            intent.putExtra("Shelter Name", selected);
            startActivity(intent);
        });
    }
}
