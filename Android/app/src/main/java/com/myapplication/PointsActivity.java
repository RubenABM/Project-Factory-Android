
package com.myapplication;

import static com.myapplication.StartActivity.Logout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
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

import com.myapplication.downloadtasks.DownloadTask;
import com.myapplication.downloadtasks.JSONObjToArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PointsActivity extends AppCompatActivity {

    TextView textPoints, textOferta, textDescricao;
    static String iduser;
    DrawerLayout drawer;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        textPoints = findViewById(R.id.textViewPoints);
        textOferta = findViewById(R.id.textViewPointsOferta);
        textDescricao = findViewById(R.id.textViewPointsOfertaDescricao);
        //drawer = findViewById(R.id.drawer_layout);

        //Método GET para ir buscar os pontos do user
        iduser = getIntent().getStringExtra("key");
        DownloadTask task = new DownloadTask();

        try{
            JSONArray pointsjson = task.execute("http://35.176.222.11:5000/users/" + iduser).get();

            //JSONObject jsonPart = pointsjson.getJSONObject(i);

            //textPoints.setText(pointsjson.getString("user_points"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Método GET para ir buscar as challenges
        JSONObjToArray task1 = new JSONObjToArray();

        /*try {
            GridLayout ll = findViewById(R.id.pointsGridLayout);

            JSONObject challengesjson = task1.execute("http://35.176.222.11:5000/challenges").get();

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
        }*/

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
    public void ClickChallenges(View view){StartActivity.goToActivity(this, ChallengesActivity.class);}
    public void ClickHealth(View view){StartActivity.goToActivity(this, HealthActivity.class);}
    public void ClickPoints(View view){StartActivity.goToActivity(this, PointsActivity.class);}
    public void ClickProfile(View view){StartActivity.goToActivity(this, ProfileActivity.class);}
    public void ClickSubscription(View view){StartActivity.goToActivity(this, SubscriptionActivity.class);}
    public void ClickSettings(View view){StartActivity.goToActivity(this, SettingsActivity.class);}
    public void ClickLogout(View view){Logout(this);}


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
