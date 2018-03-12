package com.example.peter.homelessapp.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.NumberPicker;

import com.example.peter.homelessapp.R;

/**
 * Created by valentinewilson on 3/11/18.
 */

public class ClaimScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_screen);

        final TextView tv = (TextView) findViewById(R.id.tv);
        NumberPicker np = (NumberPicker) findViewById(R.id.np);

        np.setMinValue(1);
        np.setMaxValue(10);

    }
}