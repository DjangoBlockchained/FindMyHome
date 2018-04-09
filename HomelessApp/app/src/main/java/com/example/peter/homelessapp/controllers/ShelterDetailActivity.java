package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.peter.homelessapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Shelter Detail Activity is an activity that displays details of a selected shelter
 * Details include occupied beds, address, name, genders and groups allowed, etc.
 */
public class ShelterDetailActivity extends AppCompatActivity{



    private final ArrayList<String> details = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent i = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_details);
        ListView list = findViewById(R.id.shelterdetails);
        Button claimbutton = findViewById(R.id.claimbutton);
        adapter = new ArrayAdapter<>(ShelterDetailActivity.this,
                android.R.layout.simple_list_item_1, details);
        username = i.getStringExtra("username");
        String name = i.getStringExtra("Shelter Name");
        FirebaseDatabase shelterDB = FirebaseDatabase.getInstance();
        DatabaseReference shelterRefPre = shelterDB.getReference();
        DatabaseReference shelterRef = shelterRefPre.child("shelters");
        DatabaseReference nameRef = shelterRef.child(name);

        claimbutton.setOnClickListener((view) -> {
            Intent intent = new Intent(ShelterDetailActivity.this, ClaimScreenActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("Shelter Name", name);
            startActivity(intent);
        });
        nameRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Object data =  dataSnapshot.getValue();

                details.add(getLabel(dataSnapshot.getKey(), data));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Object data = dataSnapshot.getValue();
                String key = dataSnapshot.getKey();
                // Find the current row for the key and replace it with the new value.
                int index = -1;
                boolean found = false;
                for (int i = 0; (i < details.size()) && (!found); i++) {
                    String ss = details.get(i);
                    if (ss.contains(key)) {
                        index = i;
                        found = true;
                    }
                }
                if (index != -1) {
                    details.set(index, getLabel(dataSnapshot.getKey(), data));
                } else {
                    details.add(getLabel(dataSnapshot.getKey(), data));
                }
                adapter.notifyDataSetChanged();

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

    private String getLabel(String key, Object value) {
        return key + ": " + value;
    }
}