package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.peter.homelessapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The activity for logging into the app.
 */
public class LoginScreenActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        login.setOnClickListener((View view) -> {
            Editable usernameText = userName.getText();
            String username = usernameText.toString();
            Editable passwordText = password.getText();
            String pass = passwordText.toString();
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference()
                    .child("users");
            dbRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getKey().equals(username)) {
                        Object password = dataSnapshot.child("password").getValue();
                        if ((password != null) && password.equals(pass)) {
                            if ("administer".equals(dataSnapshot.child("type").getValue())) {
                                Intent intent
                                        = new Intent(LoginScreenActivity.this,
                                        AdminScreenActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent
                                        = new Intent(LoginScreenActivity.this,
                                        ApplicationScreenActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            AlertDialog.Builder alert
                                    = new AlertDialog.Builder(LoginScreenActivity.this);
                            alert.setMessage(
                                    "Your username and password do not match with a real user.");
                            alert.setTitle("Login Error");
                            alert.setPositiveButton("OK", null);
                            alert.create().show();
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
        });


        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener((View view) -> {
            Intent intent = new Intent(LoginScreenActivity.this, WelcomeScreenActivity.class);
            startActivity(intent);
        });
    }
}
