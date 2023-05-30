package com.myapplication;

import static com.myapplication.StartActivity.Logout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myapplication.downloadtasks.DownloadTask;
import com.myapplication.downloadtasks.JSONObjToArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ChallengesActivity extends AppCompatActivity {

    TextView textChalDesc, textPoints;
    ProgressBar progressBar;
    static String iduser;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        textChalDesc = findViewById(R.id.textViewChallengeDescription);
        textPoints = findViewById(R.id.textViewChallengePoints);
        drawer = findViewById(R.id.drawer_layout);

        iduser = getIntent().getStringExtra("key");
        DownloadTask task = new DownloadTask();

        /*try {

            JSONArray challengesArray = task.execute("http://35.176.222.11:5000/challenges/").get();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                for(int i = 0; i < challengesArray.length(); i++)
                {
                    JSONObject jsonPart = challengesArray.getJSONObject(i);

                    RelativeLayout rl = new RelativeLayout(getBaseContext());
                    rl.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

                    ImageView imgTrophy = new ImageView(getBaseContext());
                    imgTrophy.setImageDrawable(getDrawable(R.drawable.trophy));
                    imgTrophy.setLayoutParams(new RelativeLayout.LayoutParams(convertDpToPixel(100, getBaseContext()), convertDpToPixel(100, getBaseContext())));
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_RIGHT);
                    rl.addView(imgTrophy);

                    TextView textLocation = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params2.addRule(RelativeLayout.CENTER_IN_PARENT);
                    textLocation.setLayoutParams(params2);
                    textLocation.setText(jsonPart.getString("chall_coord"));
                    rl.addView(textLocation);

                    TextView textKm = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params3.addRule(RelativeLayout.CENTER_IN_PARENT);
                    textKm.setLayoutParams(params3);
                    textKm.setText(jsonPart.getString("chall_totalKm"));
                    rl.addView(textKm);

                    TextView textPoints = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params4.addRule(RelativeLayout.CENTER_IN_PARENT);
                    textPoints.setLayoutParams(params4);
                    textPoints.setText(jsonPart.getString("chall_points"));
                    rl.addView(textPoints);

                    ImageView imgDone = new ImageView(getBaseContext());
                    imgDone.setImageDrawable(getDrawable(R.drawable.done));
                    imgDone.setLayoutParams(new RelativeLayout.LayoutParams(convertDpToPixel(100, getBaseContext()), convertDpToPixel(100, getBaseContext())));
                    RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params5.addRule(RelativeLayout.ALIGN_RIGHT);
                    rl.addView(imgDone);
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
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