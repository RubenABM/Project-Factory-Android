package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapplication.downloadtasks.JSONArr;
import com.myapplication.downloadtasks.JSONObj;
import com.myapplication.downloadtasks.JSONObjToArray;
import com.myapplication.downloadtasks.PostMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    public void ClickToCreate(View view) {

        //METODO POST - TENTAR MAIS TARDE

        /*
        JSONObject loginjson = null;

        try {

            String email = editTextTextEmailAddress.getText().toString();
            String pass = editTextTextPassword2.getText().toString();

            // Dados do post
            Map<String, String> postData = new HashMap<>();
            postData.put("user_email", email);
            postData.put("user_password", pass);

            // Post call
            PostMethod task = new PostMethod(postData);


            //Log.i("TAGGGGGGGGGGGGGGGGGGGGG", task.execute("http://13.40.214.190:5000/users/login").toString());
            loginjson = task.execute("http://13.40.214.190:5000/users/login");

            if(loginjson != null)
            {
                Toast.makeText(this, "Bem vindo !!", Toast.LENGTH_SHORT).show();

                // Trocar de activity
                Intent myIntent = new Intent(this, MainActivity.class);
                this.startActivity(myIntent);

            }else{
                Toast.makeText(this,"Dados incorretos 2!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Dados incorretos 3!", Toast.LENGTH_SHORT).show();
        }*/

        JSONObject loginjson = null;

        JSONObjToArray task = new JSONObjToArray();
        try {

            //Metodo get com 2 parametros
            loginjson = task.execute("http://13.40.214.190:5000/users/login2/" + editTextTextEmailAddress.getText().toString() + "/" + editTextTextPassword2.getText().toString()).get();

            if (loginjson != null) {
                Toast.makeText(this, "Bem vindo " + loginjson.getString("user_name") + " !!!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(this, MainActivity.class);
                myIntent.putExtra("key", loginjson.getString("user_id"));
                this.startActivity(myIntent);
            } else {
                Toast.makeText(this, "Os dados inseridos estão incorretos!!", Toast.LENGTH_LONG).show();
            }


        } catch (ExecutionException e) {
            e.printStackTrace();
            loginjson = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            loginjson = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void GoCreate(View view) {
        Intent myIntent = new Intent(this, CreateAccountActivity.class);
        this.startActivity(myIntent);
    }

}