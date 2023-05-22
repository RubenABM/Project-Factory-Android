package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.myapplication.downloadtasks.JSONObjToArray;

public class PointsActivity extends AppCompatActivity {

    TextView textPoints;
    static String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        textPoints = findViewById(R.id.textViewPoints);

        //Método GET para ir buscar os pontos do user
        iduser = getIntent().getStringExtra("key");
        JSONObjToArray task = new JSONObjToArray();

        try{
            //pointsjson = task.execute("http://13.40.214.190:5000/users/" + iduser).get();
            //textPoints.setText(healthjson.getString("user_points"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Método GET para reverter os pontos em ofertas
        JSONObjToArray task1 = new JSONObjToArray();


    }
}