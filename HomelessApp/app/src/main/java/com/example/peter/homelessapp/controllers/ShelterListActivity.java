package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.peter.homelessapp.R;
import com.example.peter.homelessapp.model.HomelessUser;
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
    private Button settings;
    private String searchName;
    private String searchAge;
    private String searchGender;
    private String genderAvoid;
    //private HomelessUser currentUser;
    private String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);
        shelterRef = FirebaseDatabase.getInstance().getReference().child("shelters");
        adapter = new ArrayAdapter(ShelterListActivity.this, android.R.layout.simple_list_item_1, names);
        settings = findViewById(R.id.changeSearch);
        list = findViewById(R.id.shelterList);
        username = getIntent().getStringExtra("username");
        searchName = getIntent().getStringExtra("name");
        int genderID = getIntent().getIntExtra("gender", -100);
        if (genderID == R.id.genderMale) {
            searchGender = "Men";
            genderAvoid = "Women";
        } else if (genderID == R.id.genderFemale) {
            searchGender = "Women";
            genderAvoid = "Men";
        } else if (genderID == R.id.genderAny) {
            searchGender = "Any";
            genderAvoid = "Any";
        } else {
            searchGender = null;
            genderAvoid = null;
        }
        int ageID = getIntent().getIntExtra("age", -100);
        if (ageID == R.id.ageNewborn) {
            searchAge = "newborn";
        } else if (ageID == R.id.ageChildren) {
            searchAge = "Children";
        } else if (ageID == R.id.ageYA) {
            searchAge = "Young adults";
        } else if (ageID == R.id.ageAny) {
            searchAge = "Any";
        } else {
            searchAge = null;
        }
        if ((searchName == null) && (searchGender == null) && (searchAge == null) ) {
            showAllShelters();
        } else {
            if (searchName == null) {
                searchName = "";
            }
            if (searchGender == null) {
                searchGender = "";
                genderAvoid = "";
            }
            if (searchAge == null) {
                searchAge = "";
            }
            showSearchedShelter(searchName, genderAvoid, searchAge);
        }
        settings.setOnClickListener((view) -> {
            Intent intent = new Intent(ShelterListActivity.this, SearchScreenActivity.class);
            intent.putExtra("name", searchName);
            intent.putExtra("gender", searchGender);
            intent.putExtra("age", searchAge);
            startActivity(intent);
        });
    }
    private void showSearchedShelter(String name, String gender, String age) {
        shelterRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> string = (Map<String, Object>) dataSnapshot.getValue();
                String restriction = string.get("restrictions").toString();
                String dbname = string.get("name").toString();
                if (gender.equals("Any")) {
                    if (dbname.toLowerCase().contains(name.toLowerCase())
                            && (((!(restriction.contains("Men") || (restriction.contains("Women")))) || (restriction.contains("Anyone")))
                            && ((restriction.contains(age)) || (restriction.contains("Anyone"))))) {
                        names.add(string.get("name").toString());
                        adapter.notifyDataSetChanged();
                    }
                } else if (!gender.equals("")) {
                    if (dbname.toLowerCase().contains(name.toLowerCase())
                            && (((!restriction.contains(gender)) || (restriction.contains("Anyone")))
                            && ((restriction.contains(age)) || (restriction.contains("Anyone"))))) {
                        names.add(string.get("name").toString());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    if (dbname.toLowerCase().contains(name.toLowerCase()) && ((restriction.contains(age)) || (restriction.contains("Anyone")))) {
                        names.add(string.get("name").toString());
                        adapter.notifyDataSetChanged();
                    }
                }
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
        list.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) list.getItemAtPosition(position);
            Intent intent = new Intent(ShelterListActivity.this, ShelterDetailActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("Shelter Name", selected);
            startActivity(intent);
        });
    }
    private void showAllShelters() {
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
            intent.putExtra("username", username);
            intent.putExtra("Shelter Name", selected);
            startActivity(intent);
        });
    }
}
