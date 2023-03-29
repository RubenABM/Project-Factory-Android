package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText editTextTextEmailAddress;

    EditText editTextTextPassword2;

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword2 = findViewById(R.id.editTextTextPassword2);
        loginButton = findViewById(R.id.loginButton);

    }

    public void ClickToCreate(View view)
    {
        Intent i = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivity(i);
    }


}