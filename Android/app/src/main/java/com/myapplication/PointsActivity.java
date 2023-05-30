
package com.myapplication;

import static com.myapplication.StartActivity.Logout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.downloadtasks.DownloadTask;
import com.myapplication.downloadtasks.JSONArr;
import com.myapplication.downloadtasks.JSONObjToArray;
import com.myapplication.downloadtasks.PostMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PointsActivity extends AppCompatActivity {

    TextView textPoints, textOferta, textDescricao;
    static String iduser;
    DrawerLayout drawer;
    GridLayout gridLayout;
    Integer points;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        textPoints = findViewById(R.id.textViewPoints);
        //textOferta = findViewById(R.id.textViewPointsOferta);
        //textDescricao = findViewById(R.id.textViewPointsOfertaDescricao);
        gridLayout = findViewById(R.id.pointsGridLayout);
        drawer = findViewById(R.id.drawer_layout);

        //Método GET para ir buscar os pontos do user
        iduser = getIntent().getStringExtra("key");
        JSONArr taskGetPoints = new JSONArr();

        try{
            JSONArray pointsJson = taskGetPoints.execute("http://35.176.222.11:5000/users/" + iduser).get();
            JSONObject jsonPart = pointsJson.getJSONObject(0);
            textPoints.setText(jsonPart.getString("user_points") + " pts");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Método GET para ir buscar as challenges
        String url = "http://35.176.222.11:5000/challenges";

        JSONArray getjson = null;
        JSONArr taskget = new JSONArr();

        try {
            GridLayout ll = findViewById(R.id.pointsGridLayout);
            getjson = taskget.execute(url).get();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                for(int i = 0; i < getjson.length(); i++)
                {
                    JSONObject jsonPart = getjson.getJSONObject(i);

                    RelativeLayout rl = new RelativeLayout(getBaseContext());
                    rl.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

                    RelativeLayout rl2 = new RelativeLayout(getBaseContext());
                    rl2.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

                    ImageView img = new ImageView(getBaseContext());
                    img.setImageDrawable(getDrawable(R.drawable.circulo));
                    img.setLayoutParams(new RelativeLayout.LayoutParams(convertDpToPixel(120, getBaseContext()), convertDpToPixel(120, getBaseContext())));
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //update points
                            JSONArr taskGetPoints = new JSONArr();

                            int challengePoints = 0;

                            try{
                                JSONArray pointsJson = taskGetPoints.execute("http://35.176.222.11:5000/users/" + iduser).get();
                                JSONObject jsonPart2 = pointsJson.getJSONObject(0);
                                String userPoints = jsonPart2.getString("user_points");
                                points = Integer.parseInt(userPoints);

                                String chalPoints = jsonPart.getString("chall_points");
                                challengePoints = Integer.parseInt(chalPoints);

                            } catch (Exception e) {
                                String test = e.getMessage();
                                throw new RuntimeException(e);
                            }
                            Map<String, String> postData = new HashMap<>();

                            String sumPoints = String.valueOf(points - challengePoints);

                            postData.put("user_points", sumPoints);

                            PostMethod task1 = new PostMethod(postData);

                            try {

                                task1.execute("http://35.176.222.11:5000/users/updatepoints/" + iduser);

                                Toast.makeText(getBaseContext(), "Vai receber uma mensagem com o código!", Toast.LENGTH_SHORT).show();

                            } catch (Exception e){
                                Toast.makeText(getBaseContext(), "Ora bolas! Parece que não vai receber nenhum prémio", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    rl2.addView(img);

                    TextView text = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    text.setLayoutParams(params);
                    text.setText(jsonPart.getString("chall_points") + " pts");
                    text.setTextColor(Color.parseColor("#FFFFFF"));
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    rl2.addView(text);
                    rl.addView(rl2);

                    TextView text2 = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params2.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params2.setMargins(0,convertDpToPixel(125, getBaseContext()),0,0);
                    text2.setLayoutParams(params2);
                    text2.setText(jsonPart.getString("chall_award"));
                    text2.setTextColor(Color.parseColor("#000000"));
                    rl.addView(text2);

                    TextView text3 = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params3.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    //params3.addRule(RelativeLayout.BELOW, text2.getId());
                    params3.setMargins(0,convertDpToPixel(150, getBaseContext()),0,0);
                    text3.setLayoutParams(params3);
                    text3.setText(jsonPart.getString("chall_provider"));
                    text3.setTextColor(Color.parseColor("#000000"));
                    rl.addView(text3);

                    ll.addView(rl);
                }
            }

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


    //menus
    public void OpenLeftSideMenu(View view){openDrawer(drawer);}
    public void OpenRightSideMenu(View view){openDrawer2(drawer);}

    public void CloseLeftMenu(View view){closeDrawer(drawer);}
    public void CloseRightMenu(View view){closeDrawer2(drawer);}
    public void OpenTripDetailsDrawer(View view){openDrawer3(drawer);}
    public static void openDrawer(DrawerLayout drawer) {
        drawer.openDrawer(GravityCompat.START);
    }

    public static void openDrawer2(DrawerLayout drawer) {
        drawer.openDrawer(GravityCompat.END);
    }
    public static void openDrawer3(DrawerLayout drawer) {
        drawer.openDrawer(GravityCompat.END);
    }
    public static void closeDrawer(DrawerLayout drawer) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
    public static void closeDrawer2(DrawerLayout drawer) {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
    }


    public void ClickHelmets(View view){StartActivity.goToActivity(this, HelmetsActivity.class);}
    public void ClickActivity(View view){StartActivity.goToActivity(this, ActivityActivity.class);}
    public void ClickChallenges(View view){goToActivity2(this, ChallengesActivity.class, getIntent().getStringExtra("key"));}
    public void ClickHealth(View view){StartActivity.goToActivity(this, HealthActivity.class);}
    public void ClickPoints(View view){StartActivity.goToActivity2(this, PointsActivity.class, getIntent().getStringExtra("key"));}
    public void ClickProfile(View view){StartActivity.goToActivity(this, ProfileActivity.class);}
    public void ClickSubscription(View view){StartActivity.goToActivity(this, SubscriptionActivity.class);}
    public void ClickSettings(View view){StartActivity.goToActivity(this, SettingsActivity.class);}
    public void ClickLogout(View view){Logout(this);}

    public static void goToActivity2(Activity activity, Class aClass, String iduser) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("key", iduser);
        activity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawer);
    }

}
