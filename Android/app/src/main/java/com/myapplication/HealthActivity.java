package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.myapplication.downloadtasks.JSONObj;
import com.myapplication.downloadtasks.JSONObjToArray;

import org.json.JSONObject;

public class HealthActivity extends AppCompatActivity {

    TextView bpmRes;
    TextView tempRes;
    static String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        JSONObject loginjson = null;

        bpmRes = findViewById(R.id.textViewBPMResults);
        tempRes = findViewById(R.id.textViewTemperatureResults);

        //Método GET para ir buscar a Health Data do user

        iduser = getIntent().getStringExtra("key");
        JSONObjToArray task = new JSONObjToArray();

        try{
            //loginjson = task.execute("http://13.40.214.190:5000/users/" + iduser + idrota).get();
            //bpmRes.setText(loginjson.getString("user_"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}