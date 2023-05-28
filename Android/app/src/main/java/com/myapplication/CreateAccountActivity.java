package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapplication.downloadtasks.PostMethod;


import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    Button CreateButton;

    EditText emailBox;

    EditText passwordBox;
    EditText usernameBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        CreateButton = findViewById(R.id.BackButton);
        emailBox = findViewById(R.id.editTextTextEmailAddress2);
        passwordBox = findViewById(R.id.editTextTextPassword);
        usernameBox = findViewById(R.id.editTextUsername);

    }

    public void createAccount(View view) {

        // Inputs
        String email = emailBox.getText().toString();
        String pass = passwordBox.getText().toString();
        String username = usernameBox.getText().toString();


        // Validar inputs
        if (email.isEmpty()) {
            // Mensagem de erro
            Toast.makeText(this, "Por favor insere um email!", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            // Mensagem de erro
            Toast.makeText(this, "Por favor insere uma password!", Toast.LENGTH_SHORT).show();
        } else if (username.isEmpty()) {
            // Mensagem de erro
            Toast.makeText(this, "Por favor insere um username!", Toast.LENGTH_SHORT).show();
        } else {
            try {

                // Dados do post
                Map<String, String> postData = new HashMap<>();
                postData.put("user_name", username);
                postData.put("user_email", email);
                postData.put("user_password", pass);

                // Post call
                PostMethod task = new PostMethod(postData);
                task.execute("http://13.40.214.190:5000/users/insertnewuser");

                Toast.makeText(this,"Conta foi criada com sucesso!", Toast.LENGTH_SHORT).show();

                // Trocar de activity
                Intent myIntent = new Intent(this, LoginActivity.class);
                this.startActivity(myIntent);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,"Erro ao criar conta!", Toast.LENGTH_SHORT).show();
            }
        }


    }

}