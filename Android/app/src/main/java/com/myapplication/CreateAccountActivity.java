package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.myapplication.models.CreateAccountRequest;
import com.myapplication.models.CreateAccountModel;

import java.util.concurrent.ExecutionException;

public class CreateAccountActivity extends AppCompatActivity {

    Button CreateButton;

    EditText emailBox;

    EditText passwordBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        CreateButton = findViewById(R.id.CreateButton);
        emailBox = findViewById(R.id.editTextTextEmailAddress2);
        passwordBox = findViewById(R.id.editTextTextPassword);
    }

    public void createAccount(View view) {

        String email = emailBox.getText().toString();
        String pass = passwordBox.getText().toString();

        Toast.makeText(this,"Olá " + email + " a tua conta foi criada com sucesso!", Toast.LENGTH_SHORT).show();


        CreateAccountRequest request = new CreateAccountRequest();
        request.email = email;
        request.password = pass;








    }

}