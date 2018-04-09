package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.peter.homelessapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private DatabaseReference shelterRef;
    private String username;
    private Button search;

    private String searchName;
    private String searchGender;
    private String searchAge;
    private String genderAvoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        search = (Button) findViewById(R.id.mapSearch);
        shelterRef = FirebaseDatabase.getInstance().getReference().child("shelters");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search.setOnClickListener((view) -> {
            Intent intent = new Intent(MapsActivity.this, SearchScreenActivity.class);
            intent.putExtra("name", searchName);
            intent.putExtra("gender", searchGender);
            intent.putExtra("age", searchAge);
            intent.putExtra("fromClass", getClass().getName());
            startActivity(intent);
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (searchName.equals("") && searchGender.equals("") && searchAge.equals("")) {
            showAllShelters();
        } else {
            showSearchedShelter(searchName, searchGender, searchAge);
        }
        mMap.setMinZoomPreference((float) 11.5);
        mMap.setMaxZoomPreference(18);
        LatLng startLocation = new LatLng(33.779955, -84.4);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startLocation));
        mMap.setOnMarkerClickListener(this);
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    public boolean onMarkerClick(final Marker marker) {
        String name = marker.getTitle();
        Intent intent = new Intent(MapsActivity.this, ShelterDetailActivity.class);
        intent.putExtra("Shelter Name", name);
        intent.putExtra("username", username);
        startActivity(intent);
        return false;
    }

    private void showAllShelters() {
        shelterRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> string = (Map<String, Object> )dataSnapshot.getValue();
                double latitude = (double) string.get("latitude");
                double longitude = (double) string.get("longitute");
                String name = string.get("name").toString();
                LatLng location = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(location).title(name));
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
                        double latitude = (double) string.get("latitude");
                        double longitude = (double) string.get("longitute");
                        LatLng location = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(location).title(dbname));
                    }
                } else if (!gender.equals("")) {
                    if (dbname.toLowerCase().contains(name.toLowerCase())
                            && (((!restriction.contains(gender)) || (restriction.contains("Anyone")))
                            && ((restriction.contains(age)) || (restriction.contains("Anyone"))))) {
                        double latitude = (double) string.get("latitude");
                        double longitude = (double) string.get("longitute");
                        LatLng location = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(location).title(dbname));
                    }
                } else {
                    if (dbname.toLowerCase().contains(name.toLowerCase()) && ((restriction.contains(age)) || (restriction.contains("Anyone")))) {
                        double latitude = (double) string.get("latitude");
                        double longitude = (double) string.get("longitute");
                        LatLng location = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(location).title(dbname));
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
    }
}
