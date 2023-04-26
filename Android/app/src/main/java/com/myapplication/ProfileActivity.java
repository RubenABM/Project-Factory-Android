package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.downloadtasks.JSONObj;
import com.myapplication.downloadtasks.JSONObjToArray;
import com.myapplication.downloadtasks.PostMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {

    EditText nameBox, mailBox, healthBox, pointBox, passBox, pass2Box;
    TextView points, username, useremail, userpass;
    DrawerLayout drawerLayout;
    static String iduser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        JSONObject loginjson = null;

        nameBox = findViewById(R.id.Editname);
        mailBox = findViewById(R.id.Editmail);
        passBox = findViewById(R.id.EditPass);
        points = findViewById(R.id.pointBox2);
        username = findViewById(R.id.userName);
        useremail = findViewById(R.id.userEmail);
        userpass = findViewById(R.id.userPass);

        String name = nameBox.getText().toString();
        String email = mailBox.getText().toString();
        String pass = passBox.getText().toString();
        String pass2 = pass2Box.getText().toString();



        if (email.isEmpty() || name.isEmpty() || pass.isEmpty()) {
            Toast.makeText(getBaseContext(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();

        }
        if (!pass.equals(pass2)) {
            Toast.makeText(getBaseContext(), "As palavras passes não coincidem!", Toast.LENGTH_LONG).show();

        }


        // Metodo Get para ir buscar os pontos do utilizador

        iduser = getIntent().getStringExtra("key");
        //JSONObj task = new JSONObj();
        JSONObjToArray task = new JSONObjToArray();
        try {
            loginjson = task.execute("http://13.40.214.190:5000/users/" + iduser).get();
            points.setText(loginjson.getString("user_points"));
            Log.d("AQUIIII::::", loginjson.toString());

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



    //Metodo Post para ir buscar os dados do utilizador, username, useremail, userpass

    Map<String, String> postData = new HashMap<>();
    postData.put("user_name", username);
    postData.put("user_email", useremail);
    postData.put("user_password", userpass);

     //Post call
    PostMethod task = new PostMethod(postData);
    task.execute("http://13.40.214.190:5000/users/updateuser/");

    Toast.makeText(this,"Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();
}