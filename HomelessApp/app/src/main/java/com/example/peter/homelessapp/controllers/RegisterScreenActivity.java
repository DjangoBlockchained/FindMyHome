package com.example.peter.homelessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.peter.homelessapp.R;
import com.example.peter.homelessapp.model.Administer;
import com.example.peter.homelessapp.model.HomelessUser;
import com.example.peter.homelessapp.model.User;

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
    private CheckBox adminBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        name = (EditText) findViewById(R.id.regName);
        username = (EditText) findViewById(R.id.regUserName);
        password1 = (EditText) findViewById(R.id.regPass1);
        password2 = (EditText) findViewById(R.id.regPass2);
        adminBox = (CheckBox) findViewById(R.id.checkBox);

        cancel = (Button) findViewById(R.id.cancel2);
        cancel.setOnClickListener((view) -> {
            Intent intent = new Intent(RegisterScreenActivity.this, WelcomeScreenActivity.class);
            startActivity(intent);
        });

        register = (Button) findViewById(R.id.registerUser);
        register.setOnClickListener((view) -> {
            if ((name.getText().length() == 0)
                    || (username.getText().length() == 0)
                    || (password1.getText().length() == 0)
                    || (password2.getText().length() == 0)) {
                AlertDialog.Builder alert2 = new AlertDialog.Builder(RegisterScreenActivity.this);
                alert2.setMessage("Cannot register until all fields have been filled!");
                alert2.setTitle("Registration Error");
                alert2.setPositiveButton("OK", null);
                alert2.create().show();
            } else if (password1.getText().toString().equals(password2.getText().toString())) {
                if (validUserName(username.getText().toString())) {
                    if (adminBox.isChecked()) {
                        Administer newUser = new Administer(name.getText().toString(), username.getText().toString(), password1.getText().toString());
                        Intent intent = new Intent(RegisterScreenActivity.this, AdminScreenActivity.class);
                        intent.putExtra("admin", newUser);
                        startActivity(intent);
                    } else {
                        HomelessUser newUser = new HomelessUser(name.getText().toString(), username.getText().toString(), password1.getText().toString());
                        Intent intent = new Intent(RegisterScreenActivity.this, ApplicationScreenActivity.class);
                        intent.putExtra("user", newUser);
                        startActivity(intent);
                    }
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
        return User.checkUsername(username);
    }
}
