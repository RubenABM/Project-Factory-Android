package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {

    EditText nameBox, mailBox, healthBox, pointBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameBox=findViewById(R.id.Editname);
        mailBox=findViewById(R.id.Editmail);
        healthBox=findViewById(R.id.EditHealth);
        pointBox=findViewById(R.id.EditPoint);
    }
        public void EditProfile(View view){


        }







}