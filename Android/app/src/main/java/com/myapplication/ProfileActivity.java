package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.downloadtasks.JSONObjToArray;
import com.myapplication.downloadtasks.PostMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {

    EditText nameBox, mailBox, healthBox, pointBox, passBox, pass2Box;
    Button update;
    TextView points, username, useremail, userpass, editprofile, UserName, UserEmail, UserPass, Points;
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
        editprofile = findViewById(R.id.textView10);
        update = findViewById(R.id.button);
        UserName = findViewById(R.id.userName3);
        UserEmail = findViewById(R.id.userEmail3);
        UserPass = findViewById(R.id.userPass3);
        Points = findViewById(R.id.pointBox);

        String name = nameBox.getText().toString();
        String email = mailBox.getText().toString();
        String pass = passBox.getText().toString();




        //if (email.isEmpty() || name.isEmpty() || pass.isEmpty()) {
           // Toast.makeText(getBaseContext(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();

        //}
       // if (!pass.equals(pass2)) {
          //  Toast.makeText(getBaseContext(), "As palavras passes não coincidem!", Toast.LENGTH_LONG).show();

        //}


        // Metodo Get para ir buscar os pontos do utilizador

        iduser = getIntent().getStringExtra("key");
        //JSONObj task = new JSONObj();
        JSONObjToArray task = new JSONObjToArray();
        try {
            loginjson = task.execute("http://13.40.214.190:5000/users/1").get();
            points.setText(loginjson.getString("user_points"));
            username.setText(loginjson.getString("user_name"));
            useremail.setText(loginjson.getString("user_email"));
            userpass.setText(loginjson.getString("user_password"));
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
    public void EditProfile(View view){

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameBox.setVisibility(View.VISIBLE);
                mailBox.setVisibility(View.VISIBLE);
                passBox.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);

                username.setVisibility(View.INVISIBLE);
                useremail.setVisibility(View.INVISIBLE);
                userpass.setVisibility(View.INVISIBLE);
                points.setVisibility(View.INVISIBLE);
                UserName.setVisibility(View.INVISIBLE);
                UserEmail.setVisibility(View.INVISIBLE);
                UserPass.setVisibility(View.INVISIBLE);
                Points.setVisibility(View.INVISIBLE);

            }
        });

    }


    public void UpdateProfile(View v){

        //Metodo Post para ir buscar os dados do utilizador, username, useremail, userpass
        String name = nameBox.getText().toString();
        String email = mailBox.getText().toString();
        String pass = passBox.getText().toString();

        Map<String, String> postData = new HashMap<>();
        postData.put("user_name", name);
        postData.put("user_email", email);
        postData.put("user_password", pass);

        PostMethod task1 = new PostMethod(postData);

        //Post call
        try {

        task1.execute("http://13.40.214.190:5000/users/updateuse");
        Log.d("AQUIII", task1.execute("http://13.40.214.190:5000/users/updateuser/1" + iduser).toString());

        Toast.makeText(this,"Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            Toast.makeText(this,"ERRO!", Toast.LENGTH_SHORT).show();
        }

    }

}