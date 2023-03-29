package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;

    Button loginButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.loginButton);
    }

        public void ClickToCreate(View view)
        {
            Intent i = new Intent(getApplicationContext(), CreateAccountActivity.class);
            startActivity(i);
        }

        public void login(View view)
        {
            String username = editTextTextEmailAddress.getText().toString();
            String pass = editTextTextPassword.getText().toString();

            if(username.isEmpty() || pass.isEmpty())
            {
                Toast.makeText(getBaseContext(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();
                return;
            }




    }
}