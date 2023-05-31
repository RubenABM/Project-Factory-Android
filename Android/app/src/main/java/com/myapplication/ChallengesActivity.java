package com.myapplication;

import static com.myapplication.StartActivity.Logout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class ChallengesActivity extends AppCompatActivity {

    TextView textChalDesc, textPoints;
    ProgressBar progressBar;
    static String iduser;
    DrawerLayout drawer;
    Integer points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        drawer = findViewById(R.id.drawer_layout);
        LinearLayout ll = findViewById(R.id.linearChallengesActivity);

        iduser = getIntent().getStringExtra("key");
        String url = "http://35.176.222.11:5000/challenges/";

        JSONArray getjson = null;
        JSONArr taskget = new JSONArr();
        try {
            getjson = taskget.execute(url).get();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                for(int i = 0; i < getjson.length(); i++)
                {
                    JSONObject jsonPart = getjson.getJSONObject(i);

                    RelativeLayout rl = new RelativeLayout(getBaseContext());
                    rl.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

                    RelativeLayout rl2 = new RelativeLayout(getBaseContext());
                    RelativeLayout.LayoutParams rl2Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    rl2Params.setMargins(convertDpToPixel(10, getBaseContext()), convertDpToPixel(10, getBaseContext()),0,0);
                    rl2.setLayoutParams(rl2Params);
                    rl.addView(rl2);

                    ImageView imgTrophy = new ImageView(getBaseContext());
                    imgTrophy.setImageDrawable(getDrawable(R.drawable.trophy));
                    imgTrophy.setLayoutParams(new RelativeLayout.LayoutParams(convertDpToPixel(65, getBaseContext()), convertDpToPixel(65, getBaseContext())));
                    rl2.addView(imgTrophy);

                    RelativeLayout rl3 = new RelativeLayout(getBaseContext());
                    RelativeLayout.LayoutParams rl3params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    rl3params.setMargins(convertDpToPixel(100, getBaseContext()), convertDpToPixel(10, getBaseContext()), 0, 0);
                    rl3.setLayoutParams(rl3params);
                    rl.addView(rl3);

                    /*TextView textLocation = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params2.setMargins(0, 0, 0, 0);
                    textLocation.setLayoutParams(params2);
                    textLocation.setText(jsonPart.getString("chall_award"));
                    textLocation.setTextSize(20);
                    rl3.addView(textLocation);*/

                    TextView textKm = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    //params3.addRule(RelativeLayout.BELOW, textLocation.getId());
                    //params3.setMargins(0, convertDpToPixel(30, getBaseContext()), 0, 0);
                    textKm.setLayoutParams(params3);
                    textKm.setText(jsonPart.getString("chall_totalkm") + "km");
                    textKm.setTextSize(20);
                    rl3.addView(textKm);

                    TextView textPoints = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params4.addRule(RelativeLayout.BELOW, textKm.getId());
                    //params4.setMargins(0, convertDpToPixel(60, getBaseContext()), 0, 0);
                    params4.setMargins(0, convertDpToPixel(30, getBaseContext()), 0, 0);
                    textPoints.setLayoutParams(params4);
                    textPoints.setText(jsonPart.getString("chall_points") + " points");
                    textPoints.setTextSize(20);
                    rl3.addView(textPoints);

                    RelativeLayout rl4 = new RelativeLayout(getBaseContext());
                    RelativeLayout.LayoutParams rl4params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    rl4params.setMargins(convertDpToPixel(250, getBaseContext()), convertDpToPixel(10, getBaseContext()), 0, 0);
                    rl4.setLayoutParams(rl4params);
                    rl.addView(rl4);

                    ImageView imgDone = new ImageView(getBaseContext());
                    imgDone.setImageDrawable(getDrawable(R.drawable.done));
                    imgDone.setLayoutParams(new RelativeLayout.LayoutParams(convertDpToPixel(40, getBaseContext()), convertDpToPixel(40, getBaseContext())));
                    RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params5.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    //params5.setMargins(convertDpToPixel(60, getBaseContext()), 0, 0, 0);
                    imgDone.setOnClickListener(new View.OnClickListener() {
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

                            String sumPoints = String.valueOf(points + challengePoints);

                            postData.put("user_points", sumPoints);

                            PostMethod task1 = new PostMethod(postData);

                            try {

                                task1.execute("http://35.176.222.11:5000/users/updatepoints/" + iduser);

                                Toast.makeText(getBaseContext(), "Ganhou novos pontos", Toast.LENGTH_SHORT).show();

                            } catch (Exception e){
                                Toast.makeText(getBaseContext(), "Ora bolas! Parece que não vai ter mais pontos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    rl4.addView(imgDone);

                    ll.addView(rl);
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
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


    public void ClickHelmets(View view){StartActivity.goToActivity2(this, HelmetsActivity.class,getIntent().getStringExtra("key"));}
    public void ClickActivity(View view){StartActivity.goToActivity2(this, ActivityActivity.class,getIntent().getStringExtra("key"));}
    public void ClickChallenges(View view){StartActivity.goToActivity2(this, ChallengesActivity.class,getIntent().getStringExtra("key"));}
    public void ClickHealth(View view){StartActivity.goToActivity2(this, HealthActivity.class,getIntent().getStringExtra("key"));}
    public void ClickPoints(View view){StartActivity.goToActivity2(this, PointsActivity.class,getIntent().getStringExtra("key"));}
    public void ClickProfile(View view){StartActivity.goToActivity2(this, ProfileActivity.class,getIntent().getStringExtra("key"));}
    public void ClickSubscription(View view){StartActivity.goToActivity2(this, SubscriptionActivity.class,getIntent().getStringExtra("key"));}
    public void ClickSettings(View view){StartActivity.goToActivity2(this, SettingsActivity.class,getIntent().getStringExtra("key"));}

    public void ClickMap(View view){StartActivity.goToActivity2(this, StartActivity.class,getIntent().getStringExtra("key"));}

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