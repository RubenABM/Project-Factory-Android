package com.myapplication;

import static android.content.ContentValues.TAG;

import static com.myapplication.MapFragment.endDateNtime;
import static com.myapplication.TinyWebServer.endTripFlag;
import static com.myapplication.TinyWebServer.startTripFlag;
import static com.myapplication.MapFragment.startDateNtime;
import static com.myapplication.MapFragment.latLngList;
import static com.myapplication.MapFragment.firstMap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.myapplication.downloadtasks.JSONObjToArray;
import com.myapplication.downloadtasks.PostMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class StartActivity extends AppCompatActivity {

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable databaseUpdateRunnable;

    private DatabaseHelper dbHandler;
    Button startbtn;
    Button endbtn;
    BottomSheetDialog dialog;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Fragment fragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();

        drawer = findViewById(R.id.drawer_layout);
        startbtn = findViewById(R.id.start_btn);
        endbtn = findViewById(R.id.end_btn);
        dialog = new BottomSheetDialog(this);
        createDialod();

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        endbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopTripFunction(v);
            }
        });

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    private void createDialod() {
        View view = getLayoutInflater().inflate(R.layout.fragment_trip_details, null, false);

        Button starttrip = view.findViewById(R.id.startTrip_btn);
        //EDITTEXT ORIGIN (EditText origin = view.findViewById(R.id.**);
        //EDITTEXT DESTINATION (EditText destination = view.findViewById(R.id.**);

        starttrip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //initiate mysqlite
                dbHandler = new DatabaseHelper(StartActivity.this);
                //start writing the coordenates from esp32 to dataholder
                startTripFlag = true;
                endTripFlag = false;
                firstMap = true;
                /*
                databaseUpdateRunnable = new Runnable() {
                    @Override
                    public void run() {

                        if (!DataHolder.getInstance().getDataMap().isEmpty()) {
                            String latitude = DataHolder.getInstance().getDataMap().get("gpslat");
                            String longitude = DataHolder.getInstance().getDataMap().get("gpslong");
                            //String userfall = receivedDataMap.get("fall");
                            dbHandler.addNewCoords(latitude, longitude);
                            Log.d(TAG, "Database was updated!");
                        }
                        handler.postDelayed(this, 5000); // Update every 5 seconds
                    }
                };
                handler.post(databaseUpdateRunnable);
                */
                Toast.makeText(StartActivity.this, "Safe travels!", Toast.LENGTH_LONG).show();
                startbtn.setVisibility(View.INVISIBLE);
                ViewGroup parentContainer = (ViewGroup) view.getParent();
                parentContainer.removeView(view);
            }
        });

        dialog.setContentView(view);
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
    public static void goToActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    public static void goToActivity2(Activity activity, Class aClass, String liststring) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("coordList", liststring);
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

    public void criaIndex(File filesDir, String ip) {
        try {
            File f = new File(filesDir + "/index.html");

            if (f.exists()) {
                Log.d(TAG, "Ficheiro index.html existe");
                f.delete();
                Log.d(TAG, "Vou abrir ficheiro para escrita");
                FileWriter out = new FileWriter(f, true);
                Log.d(TAG, "Abri ficheiro para escrita");
                out.append("<html><head><meta charset='utf-8'></head><body><h2>Android</h2><p>O meu IP é: " + ip + "</p></body></html>");
                Log.d(TAG, "Escrevi no ficheiro");
                out.flush();
                out.close();
            }

        } catch(Exception e){
            e.printStackTrace();
            Log.d(TAG, "Erro a criar o ficheiro: " + e.toString());
        }
    }

    public void StopTripFunction(View view) {
        JSONObject getjson = null;
        String iduser = getIntent().getStringExtra("key");
        String lineString = "LINESTRING (";
        endTripFlag = true; //stops the tinywebserver from accepting POST requests on /test url
        //take the polyline and send it to the aws database
        String rname = String.valueOf(startDateNtime);
        Map<String, String> postData = new HashMap<>();
        for (LatLng latLng : latLngList) {
            // Access each LatLng object
            int index = latLngList.indexOf(latLng);
            LatLng object = latLngList.get(index);
            String latstr = String.valueOf(object.latitude);
            String longstr = String.valueOf(object.longitude);
            lineString += latstr + " " + longstr + ",";
        }
        lineString = lineString.substring(0, lineString.length()-1) + ")";
        Log.d(TAG, lineString);
        postData.put("route_name", rname);
        postData.put("route_coord", lineString);
        postData.put("route_user_id", iduser);
        PostMethod task = new PostMethod(postData);
        task.execute("http://35.176.222.11:5000/routes/insertnewroute");

        //get route id
        JSONObjToArray taskget = new JSONObjToArray();
        String idroute = "";
        String url = "http://35.176.222.11:5000/routes/user/" + iduser + "/" + rname;
        try {
            getjson = taskget.execute(url).get();
                    idroute = getjson.getString("route_id");
            Log.d("Teste:", getjson.toString());

        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        String bpm = "";
        String temp = "";
        String hum = "";
        String endDnT = String.valueOf(endDateNtime);

        Map<String, String> postData2 = new HashMap<>();
        postData.put("data_bpm", bpm);
        postData.put("data_temp", temp);
        postData.put("data_hum", hum);
        postData.put("data_startTime", rname);
        postData.put("data_endTime", endDnT);
        postData.put("data_user_id", iduser);
        postData.put("data_route_id", idroute);
        PostMethod task2 = new PostMethod(postData2);
        task2.execute("http://35.176.222.11:5000/users/insertnewdata");
        Toast.makeText(this, "Trip was saved!", Toast.LENGTH_SHORT).show();
    }
}
