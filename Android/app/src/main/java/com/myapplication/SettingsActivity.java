package com.myapplication;

import static com.myapplication.StartActivity.Logout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Switch darkModeSwitch;
    public static String phone = "x";

    DrawerLayout drawer;
    EditText editNumber;

    Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        darkModeSwitch = findViewById(R.id.switchMode);
        drawer = findViewById(R.id.drawer_layout);
        editNumber = findViewById(R.id.editTextNumber);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        editNumber.setText(phone);

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Do something when the switch is on
                } else {
                    // Do something when the switch is off
                }
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = editNumber.getText().toString();
                Toast.makeText(SettingsActivity.this, "Numero atualizado!", Toast.LENGTH_SHORT).show();
            }
        });

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