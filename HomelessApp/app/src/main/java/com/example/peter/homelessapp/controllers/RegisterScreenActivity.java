package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.peter.homelessapp.R;

/**
 * Created by sanjanakadiveti on 2/11/18.
 */

public class RegisterScreenActivity extends AppCompatActivity {

    private Button cancel;
    private Button register;
    private EditText name;
    private EditText username;
    private EditText password1;
    private EditText password2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        name = (EditText) findViewById(R.id.regName);
        username = (EditText) findViewById(R.id.regUserName);
        password1 = (EditText) findViewById(R.id.regPass1);
        password2 = (EditText) findViewById(R.id.regPass2);

        cancel = (Button) findViewById(R.id.cancel2);
        cancel.setOnClickListener((view) -> {
            Intent intent = new Intent(RegisterScreenActivity.this, WelcomeScreenActivity.class);
            startActivity(intent);
        });

        register = (Button) findViewById(R.id.registerUser);
        register.setOnClickListener((view) -> {
            if (password1.getText().toString().equals(password2.getText().toString())) {
                if (validUserName(username.getText().toString())) {

                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(RegisterScreenActivity.this);
                    alert.setMessage("That user name is already taken. Pick another one!");
                    alert.setTitle("Invalid User Name");
                    alert.setPositiveButton("OK", null);
                    alert.create().show();
                }
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterScreenActivity.this);
                alert.setMessage("Your passwords didn't match. Make sure you put the same password for each!");
                alert.setTitle("Password Mismatch");
                alert.setPositiveButton("OK", null);
                alert.create().show();
            }
        });
    }

    private boolean validUserName(String username) {
        return false;
    }
}
