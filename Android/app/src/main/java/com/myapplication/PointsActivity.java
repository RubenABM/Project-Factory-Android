package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myapplication.downloadtasks.JSONObjToArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PointsActivity extends AppCompatActivity {

    TextView textPoints, textOferta, textDescricao;
    static String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        textPoints = findViewById(R.id.textViewPoints);
        textOferta = findViewById(R.id.textViewPointsOferta);
        textDescricao = findViewById(R.id.textViewPointsOfertaDescricao);

        //Método GET para ir buscar os pontos do user
        iduser = getIntent().getStringExtra("key");
        JSONObjToArray task = new JSONObjToArray();

        try{
            JSONObject pointsjson = task.execute("http://13.40.214.190:5000/users/" + iduser).get();
            textPoints.setText(pointsjson.getString("user_points"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Método GET para ir buscar as challenges
        JSONObjToArray task1 = new JSONObjToArray();

        try {
            GridLayout ll = findViewById(R.id.pointsGridLayout);

            JSONObject challengesjson = task.execute("http://13.40.214.190:5000/challenges").get();

            ArrayList challenges = new ArrayList();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                challenges.forEach(item -> {
                    RelativeLayout rl = new RelativeLayout(getBaseContext());
                    rl.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

                    ImageView img = new ImageView(getBaseContext());
                    img.setImageDrawable(getDrawable(R.drawable.circulo));
                    img.setLayoutParams(new RelativeLayout.LayoutParams(convertDpToPixel(125, getBaseContext()), convertDpToPixel(125, getBaseContext())));
                    rl.addView(img);

                    TextView text = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    text.setLayoutParams(params);
                    text.setText(item.chall_points);
                    rl.addView(text);

                    TextView text2 = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params2.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params2.setMargins(0,convertDpToPixel(125, getBaseContext()),0,0);
                    text2.setLayoutParams(params2);
                    text2.setText(item.chall_award);
                    text2.setTextColor(Color.parseColor("#000000"));
                    rl.addView(text2);

                    ll.addView(rl);
                });
            }

            textOferta.setText(challengesjson.getString("chall_points"));
            textDescricao.setText(challengesjson.getString("chall_provider"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public static int convertDpToPixel(int dp, Context context){
        return dp * ((int) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}