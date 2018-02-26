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
                if (User.checkLogin(userName.getText().toString(), password.getText().toString())) {
                    if (User.getUser(userName.getText().toString()) instanceof Administer) {
                        System.out.println("I'm an admin!");
                        Intent intent = new Intent(LoginScreenActivity.this, AdminScreenActivity.class);
                        intent.putExtra("admin", (Administer) User.getUser(userName.getText().toString()));
                        startActivity(intent);
                    } else {
                        System.out.println("I'm a regular user!");
                        Intent intent = new Intent(LoginScreenActivity.this, ApplicationScreenActivity.class);
                        intent.putExtra("user", (HomelessUser) User.getUser(userName.getText().toString()));
                        startActivity(intent);
                    }
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginScreenActivity.this);
                    alert.setMessage("Your username and password do not match with a real user.");
                    alert.setTitle("Login Error");
                    alert.setPositiveButton("OK", null);
                    alert.create().show();
                }
            }
        });

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreenActivity.this, WelcomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}
