package com.example.peter.homelessapp.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.peter.homelessapp.R;
import com.example.peter.homelessapp.model.Administer;
import com.example.peter.homelessapp.model.HomelessUser;
import com.example.peter.homelessapp.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Peter on 2/11/18.
 */

public class LoginScreenActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private Button login;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = userName.getText().toString();
                String pass = password.getText().toString();
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("users");
                dbRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getKey().equals(uname)) {
                            System.out.println("here!!!!!");
                            if (dataSnapshot.child("password").getValue().equals(pass)) {
                                if (dataSnapshot.child("type").equals("administer")) {
                                    Intent intent = new Intent(LoginScreenActivity.this, AdminScreenActivity.class);
                                    intent.putExtra("username", uname);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(LoginScreenActivity.this, ApplicationScreenActivity.class);
                                    intent.putExtra("username", uname);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(LoginScreenActivity.this);
                                alert.setMessage("Your username and password do not match with a real user.");
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
            }
        });


        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
