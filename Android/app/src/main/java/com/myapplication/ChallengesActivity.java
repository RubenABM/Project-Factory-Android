package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

import org.json.JSONObject;

public class ChallengesActivity extends AppCompatActivity {

    ProgressBar progressBar;
    static String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        JSONObject challengesjson = null;
        progressBar = findViewById(R.id.progressBarChallenge);
        // set the progress value based on the challenge progress
        //int progress =

        //código challenge bar
        //progressBar.setProgress(progress);

    }
}