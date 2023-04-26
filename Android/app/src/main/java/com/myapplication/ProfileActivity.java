package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.myapplication.downloadtasks.JSONObj;
import com.myapplication.downloadtasks.PostMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {

    EditText nameBox, mailBox, healthBox, pointBox, pointBox2, passBox, pass2Box;
    DrawerLayout drawerLayout;
    JSONObject loginjson = null;
    static String iduser;


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


            // Dados do get dos pontos

            iduser = getIntent().getStringExtra("key");
            JSONObj task = new JSONObj();
            try {
                loginjson = task.execute("http://13.40.214.190:5000/users/"+1).get();
                pointBox2.setText(loginjson.getString("user_points"));

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

            //Dados do post do nome, mail e pass

            //Map<String, String> postData = new HashMap<>();
            //postData.put("user_name", name);
            //postData.put("user_email", email);
            //postData.put("user_password", pass);

            // Post call
            //PostMethod task = new PostMethod(postData);
            //task.execute("http://13.40.214.190:5000/users/insertnewuser");

            //Toast.makeText(this,"Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();







}