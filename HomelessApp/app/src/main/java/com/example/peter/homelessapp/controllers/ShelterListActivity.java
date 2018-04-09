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
import java.util.Map;

/**
 * Shelter List Activity is an activity that displays a list of shelters to the users
 * Shelters can be filtered using search bar
 */
public class ShelterListActivity extends AppCompatActivity{
    private ListView list;
    private DatabaseReference shelterRef;

    private final ArrayList<String> names = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String searchName;
    private String searchAge;
    private String searchGender;
    private String username;
    private String genderAvoid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference shelterRefPre = database.getReference();
        shelterRef = shelterRefPre.child("shelters");
        adapter = new ArrayAdapter<>(ShelterListActivity.this,
                android.R.layout.simple_list_item_1, names);
        Button settings = findViewById(R.id.changeSearch);
        list = findViewById(R.id.shelterList);
        Intent i = getIntent();
        username = i.getStringExtra("username");
        searchName = i.getStringExtra("name");
        loadSearchGender();
        loadSearchAge();
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
            intent.putExtra("fromClass", getClass().getName());
            startActivity(intent);
        });
    }

    private void loadSearchGender() {
        Intent intent = getIntent();
        int genderID = intent.getIntExtra("gender", -100);
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
            searchGender = "";
            genderAvoid = "";
        }
    }

    private void loadSearchAge() {
        Intent intent = getIntent();
        int ageID = intent.getIntExtra("age", -100);
        if (ageID == R.id.ageNewborn) {
            searchAge = "newborn";
        } else if (ageID == R.id.ageChildren) {
            searchAge = "Children";
        } else if (ageID == R.id.ageYA) {
            searchAge = "Young adults";
        } else if (ageID == R.id.ageAny) {
            searchAge = "Any";
        } else {
            searchAge = "";
        }
    }



    private void showSearchedShelter(String name, CharSequence gender, CharSequence age) {
        shelterRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Object value = dataSnapshot.getValue();
                Map string = (Map)value;
                if (string != null) {
                    Object o = string.get("restrictions");
                    if (o != null) {
                        Object nameObj = string.get("name");
                        if (matchesSearch(string, name, gender, age)) {
                            names.add(nameObj.toString());
                            adapter.notifyDataSetChanged();
                        }
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
                Map string = (Map)dataSnapshot.getValue();
                if (string != null) {
                    Object nameObj = string.get("name");
                    if (nameObj != null) {
                        names.add(nameObj.toString());
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

    private boolean matchesSearch(Map data, String name, CharSequence gender, CharSequence age) {
        String restriction = data.get("restrictions").toString();
        String databaseName = data.get("name").toString();
        boolean nameMatches = databaseName.toLowerCase().contains(name.toLowerCase());
        boolean ageMatches = (restriction.contains(age)) || (restriction.contains("Anyone"));
        boolean genderMatches;
        if ("Any".equals(gender)) {
            genderMatches = restriction.contains("Men")
                    || restriction.contains("Women")
                    || restriction.contains("Anyone");
        } else {
            genderMatches = "".equals(gender)
                    || !restriction.contains(gender)
                    || restriction.contains("Anyone");
        }
        return nameMatches && ageMatches && genderMatches;
    }



}
