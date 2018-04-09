package com.example.peter.homelessapp.controllers;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.widget.NumberPicker;

import com.example.peter.homelessapp.R;
import com.example.peter.homelessapp.model.HomelessUser;
import com.example.peter.homelessapp.model.Shelter;
import com.example.peter.homelessapp.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by valentinewilson on 3/11/18.
 */

public class ClaimScreenActivity extends AppCompatActivity {

    private Button claimButton;

    private DatabaseReference shelterRef;
    private DatabaseReference userRef;
    private Shelter shelter;
    private HomelessUser currentUser;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_screen);

        final TextView tv = findViewById(R.id.tv);
        NumberPicker np = findViewById(R.id.np);

        np.setMinValue(1);
        np.setMaxValue(10);

        // User user = getIntent().getParcelableExtra("user");
        // currentUser = (HomelessUser) User.getUser(user.getUsername());

        np.setMinValue(1);
        np.setMaxValue(10);

        //User user = getIntent().getParcelableExtra("user");
        username = getIntent().getStringExtra("username");
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(username);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(HomelessUser.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String name = getIntent().getStringExtra("Shelter Name");
        shelterRef = FirebaseDatabase.getInstance().getReference().child("shelters").child(name);// .getParent();
        shelterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelter = dataSnapshot.getValue(Shelter.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // bedsPicker = (EditText) findViewById(R.id.claimnum);

        claimButton = findViewById(R.id.claim);
        claimButton.setOnClickListener((view) -> {
            int beds = np.getValue();

            if (!shelter.hasVacancy(beds)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Not Enough Beds Available.");
                builder.setMessage("This shelter has " + shelter.getNumberOfVacancies() + " beds available.");
                builder.setPositiveButton("OK", (dialog, id) -> {
                    dialog.dismiss();
                });
                builder.create().show();
                return;
            }

            if (currentUser.getCurrentShelter() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Already Checked In");
                builder.setMessage("You are already checked in to the shelter '"
                        + currentUser.getCurrentShelter()
                        + "'. Would you like to check out and check in to the new shelter?");
                builder.setPositiveButton("Check Out", (dialog, id) -> {
                    String sheltername = currentUser.getCurrentShelter();
                    shelterRef = FirebaseDatabase.getInstance().getReference().child("shelters").child(sheltername);// .getParent();
                    shelterRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Shelter currShelter = dataSnapshot.getValue(Shelter.class);
                            if (currShelter.getName().equals(shelter.getName())) {
                                shelter.checkOut(currentUser);
                            } else {
                                currShelter.checkOut(currentUser);
                            }
                            checkIn(beds);
                            dialog.dismiss();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                });
                builder.setNegativeButton("Cancel", (dialog, id) -> {
                    dialog.dismiss();
                });
                builder.create().show();
                return;
            } else {
                checkIn(beds);
            }
        });

    }

    private void checkIn(int beds) {
        shelter.checkIn(currentUser, beds);
        finish();
    }
}