package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    EditText nameBox, mailBox, healthBox, pointBox;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        nameBox=findViewById(R.id.Editname);
        mailBox=findViewById(R.id.Editmail);

    }
        public void updateProfile(View view)
        {
            String name = nameBox.getText().toString();
            String email = mailBox.getText().toString();
            String health = healthBox.getText().toString();
            String point = pointBox.getText().toString();

            if(email.isEmpty() || name.isEmpty() || health.isEmpty())
            {
                Toast.makeText(getBaseContext(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();
                return;
            }





        }







}