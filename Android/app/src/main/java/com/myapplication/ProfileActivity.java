package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    EditText nameBox, mailBox, healthBox, pointBox, passBox, pass2Box;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        nameBox=findViewById(R.id.Editname);
        mailBox=findViewById(R.id.Editmail);
        passBox=findViewById(R.id.EditPass);
        pass2Box=findViewById(R.id.EditPass2);

    }
        public void updateProfile(View view)
        {
            String name = nameBox.getText().toString();
            String email = mailBox.getText().toString();
            String pass = passBox.getText().toString();
            String pass2 = pass2Box.getText().toString();

            if(email.isEmpty() || name.isEmpty() || pass.isEmpty())
            {
                Toast.makeText(getBaseContext(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();
                return;
            }
            if(!pass.equals(pass2))
            {
                Toast.makeText(getBaseContext(), "As palavras passes não coincidem!", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(this,"Data changed! ", Toast.LENGTH_LONG).show();





        }







}