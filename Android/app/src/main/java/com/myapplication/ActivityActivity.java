package com.myapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import static com.myapplication.MapFragment.firstMap;
import static com.myapplication.StartActivity.startingflag;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.downloadtasks.JSONArr;
import com.myapplication.downloadtasks.JSONObjToArray;
import com.myapplication.downloadtasks.PostMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class ActivityActivity extends AppCompatActivity {

    EditText routeName;
    TextView startDnTime, idrout;
    DrawerLayout drawer;
    Button editar, atualizar;

    ImageView iconTrip;
    static String iduser;
    public static String linestr;

    String idroute = "";
    String routeCoords = "";
    RelativeLayout routeCards;

    public static HashMap<String, String> routes = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        iduser = getIntent().getStringExtra("key");
        String firstURL = "http://35.176.222.11:5000/routes/user/" + 2;
        drawer = findViewById(R.id.drawer_layout);

        LinearLayout ll = findViewById(R.id.linearActivityActivity);

        JSONArray getjson = null;
        JSONArr taskget = new JSONArr();
        try {
            getjson = taskget.execute(firstURL).get();
            Log.d(TAG, getjson.toString());
            for (int i = 0; i < getjson.length(); i++) {
                JSONObject test = getjson.getJSONObject(i);

                String dataStartTime = test.getString("data_starttime");
                String dataEndTime = test.getString("data_endtime");

                int startTime = Integer.parseInt(dataStartTime);
                int endTime = Integer.parseInt(dataEndTime);

                //int duration = (endTime - startTime) / 1000;
                int duration = (endTime - startTime) / 3600000;

                RelativeLayout rl = new RelativeLayout(getBaseContext());
                rl.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

                RelativeLayout rl2 = new RelativeLayout(getBaseContext());
                RelativeLayout.LayoutParams rl2Params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                rl2Params.setMargins(convertDpToPixel(10, getBaseContext()), convertDpToPixel(10, getBaseContext()),0,0);
                rl2.setLayoutParams(rl2Params);
                rl.addView(rl2);

                ImageView img = new ImageView(getBaseContext());
                img.setImageDrawable(getDrawable(R.drawable.edit_road));
                img.setLayoutParams(new RelativeLayout.LayoutParams(convertDpToPixel(77, getBaseContext()), convertDpToPixel(58, getBaseContext())));
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            GoToTrip(v, test.getString("route_id"));
                        }catch(Exception e){}
                    }
                });
                rl2.addView(img);

                Button btn = new Button(getBaseContext());
                RelativeLayout.LayoutParams params8 = new RelativeLayout.LayoutParams(convertDpToPixel(75, getBaseContext()),convertDpToPixel(48, getBaseContext()));
                params8.setMargins(0, convertDpToPixel(60, getBaseContext()), 0, 0);
                params8.addRule(RelativeLayout.BELOW, img.getId());
                btn.setBackgroundColor(Color.parseColor("#003B3F"));
                btn.setTextColor(Color.parseColor("#FFFFFF"));
                btn.setText("Edit");
                btn.setLayoutParams(params8);
                rl2.addView(btn);


                RelativeLayout rl3 = new RelativeLayout(getBaseContext());
                RelativeLayout.LayoutParams rl3params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                rl3params.setMargins(convertDpToPixel(100, getBaseContext()), convertDpToPixel(10, getBaseContext()), 0, 0);
                rl3.setLayoutParams(rl3params);
                rl.addView(rl3);

                TextView textRouteId = new TextView(getBaseContext());
                RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                textRouteId.setLayoutParams(params2);
                textRouteId.setText("Route ID: ");
                textRouteId.setTextColor(Color.parseColor("#000000"));
                textRouteId.setTextSize(20);
                rl3.addView(textRouteId);

                TextView textRouteIdPlaceHolder = new TextView(getBaseContext());
                RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params3.setMargins(convertDpToPixel(95, getBaseContext()), 0, 0, 0);
                params3.addRule(RelativeLayout.END_OF, textRouteId.getId());
                textRouteIdPlaceHolder.setLayoutParams(params3);
                textRouteIdPlaceHolder.setText(test.getString("route_id"));
                textRouteIdPlaceHolder.setTextSize(20);
                textRouteIdPlaceHolder.setTextColor(Color.parseColor("#000000"));
                rl3.addView(textRouteIdPlaceHolder);

                TextView textPercursoName = new TextView(getBaseContext());
                RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params4.setMargins(0, convertDpToPixel(30, getBaseContext()), 0, 0);
                params4.addRule(RelativeLayout.BELOW, textRouteId.getId());
                textPercursoName.setLayoutParams(params4);
                textPercursoName.setText("Name: ");
                textPercursoName.setTextColor(Color.parseColor("#000000"));
                textPercursoName.setTextSize(20);
                rl3.addView(textPercursoName);

                EditText editTextPercursoName = new EditText(getBaseContext());
                RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params5.setMargins(convertDpToPixel(90, getBaseContext()), convertDpToPixel(20, getBaseContext()), 0, 0);
                params5.addRule(RelativeLayout.BELOW, textRouteIdPlaceHolder.getId());
                editTextPercursoName.setLayoutParams(params5);
                editTextPercursoName.setText(test.getString("route_name"));
                editTextPercursoName.setTextColor(Color.parseColor("#000000"));
                editTextPercursoName.setTextSize(20);
                rl3.addView(editTextPercursoName);

                TextView textDate = new TextView(getBaseContext());
                RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params6.addRule(RelativeLayout.BELOW, textPercursoName.getId());
                params6.setMargins(0, convertDpToPixel(60, getBaseContext()), 0, 0);
                textDate.setLayoutParams(params6);
                textDate.setText("Duração: ");
                textDate.setTextColor(Color.parseColor("#000000"));
                textDate.setTextSize(20);
                rl3.addView(textDate);

                TextView textDatePlaceHolder = new TextView(getBaseContext());
                RelativeLayout.LayoutParams params7 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params7.setMargins(convertDpToPixel(95, getBaseContext()), convertDpToPixel(60, getBaseContext()), 0, 0);
                params7.addRule(RelativeLayout.BELOW, editTextPercursoName.getId());
                textDatePlaceHolder.setLayoutParams(params7);
                textDatePlaceHolder.setText(String.valueOf(duration) + "h");
                textDatePlaceHolder.setTextColor(Color.parseColor("#000000"));
                textDatePlaceHolder.setTextSize(20);
                rl3.addView(textDatePlaceHolder);

                routes.put(test.getString("route_id"), test.getString("route_coord"));

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            UpdateText(v, test.getString("route_id"), editTextPercursoName);
                        }catch(Exception e) {}
                    }
                });

                ll.addView(rl);
            }

        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void UpdateText(View v, String routeId, EditText routeNameEdit) {

        try {
            Map<String, String> postData = new HashMap<>();
            postData.put("route_name", routeNameEdit.getEditableText().toString());
            PostMethod task = new PostMethod(postData);
            String url = "http://35.176.222.11:5000/routes/updateroutename/" + iduser + "/" + routeId;
            task.execute(url);

            Toast.makeText(this, "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "ERRO!", Toast.LENGTH_SHORT).show();
        }

    }

    //menus
    public void OpenLeftSideMenu(View view) {
        openDrawer(drawer);
    }

    public void OpenRightSideMenu(View view) {
        openDrawer2(drawer);
    }

    public void CloseLeftMenu(View view) {
        closeDrawer(drawer);
    }

    public void CloseRightMenu(View view) {
        closeDrawer2(drawer);
    }

    public void OpenTripDetailsDrawer(View view) {
        openDrawer3(drawer);
    }

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

    public void ClickLogout(View view) {
        //goToActivity(this,**);
        Toast.makeText(this, "Function 'Logout' is not available yet", Toast.LENGTH_SHORT).show();
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


    public void GoToTrip(View view, String idroute) {
        firstMap = false;
        startingflag = false;
        linestr = routes.get(idroute);
        StartActivity.goToActivity(this, StartActivity.class);
    }

    public static int convertDpToPixel(int dp, Context context){
        return dp * ((int) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}