package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

public class HelmetsActivity extends AppCompatActivity {

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helmets);

        drawer = findViewById(R.id.drawer_layout);
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


    public void ClickHelmets(View view){goToActivity(this, HelmetsActivity.class);}
    public void ClickActivity(View view){
        //goToActivity(this,**);
        Toast.makeText(this, "Function 'Activities' is not available yet", Toast.LENGTH_SHORT).show();
    }
    public void ClickChallenges(View view){goToActivity(this, ChallengesActivity.class);}
    public void ClickHealth(View view){
        //goToActivity(this,**);
        Toast.makeText(this, "Function 'Health' is not available yet", Toast.LENGTH_SHORT).show();
    }
    public void ClickPoints(View view){
        //goToActivity(this,**);
        Toast.makeText(this, "Function 'Points' is not available yet", Toast.LENGTH_SHORT).show();
    }
    public void ClickProfile(View view){
        //goToActivity(this,**);
        Toast.makeText(this, "Function 'Profile' is not available yet", Toast.LENGTH_SHORT).show();
    }
    public void ClickSubscription(View view){goToActivity(this, SubscriptionActivity.class);}
    public void ClickSettings(View view){
        //goToActivity(this,**);
        Toast.makeText(this, "Function 'Settings' is not available yet", Toast.LENGTH_SHORT).show();
    }
    public void ClickLogout(View view){
        //goToActivity(this,**);
        Toast.makeText(this, "Function 'Logout' is not available yet", Toast.LENGTH_SHORT).show();
    }
}