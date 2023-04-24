package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HealthActivity extends AppCompatActivity {

    TextView bpmRes;
    TextView tempRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        bpmRes = findViewById(R.id.textViewBPMResults);
        tempRes = findViewById(R.id.textViewTemperatureResults);
    }
}