package com.example.peter.homelessapp.controllers;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.peter.homelessapp.R;
import com.example.peter.homelessapp.model.HomelessUser;
import com.example.peter.homelessapp.model.Shelter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Allows a user to claim spots at a shelter.
 */
public class ClaimScreenActivity extends AppCompatActivity {

    private DatabaseReference shelterRef;
    private Shelter shelter;
    private HomelessUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_screen);

        NumberPicker np = findViewById(R.id.np);

        np.setMinValue(1);
        np.setMaxValue(10);

        loadUser();
        loadShelter(getIntent().getStringExtra("Shelter Name"));

        String name = getIntent().getStringExtra("Shelter Name");
        shelterRef = FirebaseDatabase.getInstance().getReference().child("shelters").child(name);
        shelterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelter = dataSnapshot.getValue(Shelter.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button claimButton = findViewById(R.id.claim);
        claimButton.setOnClickListener((view) -> {
            int beds = np.getValue();

            if (!shelter.hasVacancy(beds)) {
                showNotEnoughBedsAlert();
            }

            String currentShelter = currentUser.getCurrentShelter();
            if (currentShelter != null) {
                showAlreadyCheckedInAlert(beds, currentShelter);
            } else {
                checkIn(beds);
            }
        });

    }


    private void loadUser() {
        //User user = getIntent().getParcelableExtra("user");
        String username = getIntent().getStringExtra("username");
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child(username);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(HomelessUser.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadShelter(String name) {
        shelterRef = FirebaseDatabase.getInstance().getReference()
                .child("shelters").child(name);// .getParent();
        shelterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelter = dataSnapshot.getValue(Shelter.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkIn(int beds) {
        shelter.checkIn(currentUser, beds);
        finish();
    }

    private void showNotEnoughBedsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Not Enough Beds Available.");
        builder.setMessage("This shelter has " + shelter.getNumberOfVacancies()
                + " beds available.");
        builder.setPositiveButton("OK", (dialog, id) ->  dialog.dismiss());
        builder.create().show();
    }

    private void showAlreadyCheckedInAlert(int beds, String currentShelter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Already Checked In");
        builder.setMessage("You are already checked in to the shelter '"
                + currentShelter
                + "'. Would you like to check out and check in to the new shelter?");
        builder.setPositiveButton("Check Out", (dialog, id) -> {
            shelterRef = FirebaseDatabase.getInstance().getReference()
                    .child("shelters").child(currentShelter);// .getParent();
            shelterRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Shelter currShelter = dataSnapshot.getValue(Shelter.class);
                    if (currShelter == null) { return; }
                    Shelter.checkOut(shelter, currShelter, currentUser);
                    checkIn(beds);
                    dialog.dismiss();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        });
        builder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

        builder.create().show();
    }

}