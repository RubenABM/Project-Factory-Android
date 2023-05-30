package com.myapplication;

import static android.content.ContentValues.TAG;

import static com.myapplication.MapFragment.endDateNtime;
import static com.myapplication.TinyWebServer.endTripFlag;
import static com.myapplication.TinyWebServer.startTripFlag;
import static com.myapplication.MapFragment.startDateNtime;
import static com.myapplication.MapFragment.latLngList;
import static com.myapplication.MapFragment.firstMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.myapplication.downloadtasks.JSONObj;
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

    private Runnable checkFallRunnable;
    public static boolean fallFlag = false;

    //call--------------------------
    private static final int NOTIFICATION_ID = 1;
    private static final long CALL_DELAY_MS = 10000; // 10 seconds

    public String phone = "x";

    public Handler handler2 = new Handler();
    public Runnable callRunnable = new Runnable() {
        @Override
        public void run() {
            makePhoneCall();
        }
    };
    private BroadcastReceiver cancelCallReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Cancel the scheduled phone call
            handler2.removeCallbacks(callRunnable);
        }
    };
    //___________________________________________

    public static boolean startingflag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //IntentFilter filter = new IntentFilter("CANCEL_PHONE_CALL_ACTION");
        //LocalBroadcastManager.getInstance(this).registerReceiver(cancelCallReceiver, filter);
        //showNotification();
        //schedulePhoneCall();

        checkFallRunnable = new Runnable() {
            @Override
            public void run() {

                if (fallFlag) {
                    IntentFilter filter = new IntentFilter("CANCEL_PHONE_CALL_ACTION");
                    LocalBroadcastManager.getInstance(StartActivity.this).registerReceiver(cancelCallReceiver, filter);
                    showNotification();
                    schedulePhoneCall();
                    fallFlag = false;
                }
                handler.postDelayed(this, 5000); // Update every 5 seconds
            }
        };
        handler.post(checkFallRunnable);


        if (startingflag){
            firstMap = true;
        } else if (!startingflag) {
            firstMap = false;
        }

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
    public void ClickActivity(View view){goToActivity2(this, ActivityActivity.class, getIntent().getStringExtra("key"));}
    public void ClickChallenges(View view){goToActivity2(this, ChallengesActivity.class, getIntent().getStringExtra("key"));}
    public void ClickHealth(View view){
        //goToActivity(this,**);
        Toast.makeText(this, "Function 'Health' is not available yet", Toast.LENGTH_SHORT).show();
    }
    public void ClickPoints(View view){goToActivity2(this, PointsActivity.class, getIntent().getStringExtra("key"));}
    public void ClickProfile(View view){goToActivity(this, ProfileActivity.class);}
    public void ClickSubscription(View view){goToActivity(this, SubscriptionActivity.class);}
    public void ClickSettings(View view){
        //goToActivity(this,**);
        Toast.makeText(this, "Function 'Settings' is not available yet", Toast.LENGTH_SHORT).show();
    }
    public void ClickLogout(View view){Logout(this);}

    //Logout
    public static void Logout(Activity activity) {
        //Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Set title
        builder.setTitle("Logout");
        //Set message
        builder.setMessage("Are you sure you want to logout?");
        //Positive yes button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Finish activity
                activity.finishAffinity();
                //Exit app
                System.exit(0);

            }
        });


        //Negative no button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                //Dismiss dialog
                dialog.dismiss();
            }
        });
        //Show dialog
        builder.show();
    }


    public static void goToActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
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

        //Codigo temperatura e humidade, ver variaveis globais
        JSONObject taskResult = null;
        double temperature;
        int humidity;

        try {
            String lat = DataHolder.getInstance().getDataMap().get("gpslat");
            String lon = DataHolder.getInstance().getDataMap().get("gpslong");
            taskResult = new JSONObj().execute("https://api.openweathermap.org/data/2.5/weather?lat=" + lat +  "&lon=" + lon + "&appid=5602c937232287bb6c643d7790297bb6").get();
            temperature = taskResult.getJSONObject("main").getDouble("temp");
            humidity = taskResult.getJSONObject("main").getInt("humidity");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        String bpm = "";
        String temp = String.valueOf(temperature);
        String hum = String.valueOf(humidity);
        String endDnT = String.valueOf(endDateNtime);

        Map<String, String> postData2 = new HashMap<>();
        postData2.put("data_bpm", bpm);
        postData2.put("data_temp", temp);
        postData2.put("data_hum", hum);
        postData2.put("data_startTime", rname);
        postData2.put("data_endTime", endDnT);
        postData2.put("data_user_id", iduser);
        postData2.put("data_route_id", idroute);
        PostMethod task2 = new PostMethod(postData2);
        task2.execute("http://35.176.222.11:5000/users/insertnewdata");
        Toast.makeText(this, "Trip was saved!", Toast.LENGTH_SHORT).show();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotificationChannelName";
            String description = "NotificationChannelDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @SuppressLint("MissingPermission")
    private void showNotification() {
        createNotificationChannel();

        boolean notificationsEnabled = NotificationManagerCompat.from(this).areNotificationsEnabled();

        if (!notificationsEnabled) {
            // Notifications are disabled, handle it accordingly (e.g., show a toast, prompt user to enable)
            Toast.makeText(this, "Please enable notifications to receive alerts.", Toast.LENGTH_SHORT).show();

            handler2.removeCallbacks(callRunnable);
            return;
        }

        Intent intent = new Intent(this, NotificationClickReceiver.class);
        intent.setAction("NOTIFICATION_CLICKED_ACTION");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Queda detectada!")
                .setContentText("Clique aqui se estiver tudo bem.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    private void schedulePhoneCall() {
        handler2.postDelayed(callRunnable, CALL_DELAY_MS);
    }

    private void makePhoneCall() {
        // Cancel the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(NOTIFICATION_ID);

        // Make the phone call
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone)); // Replace PHONE_NUMBER with the desired phone number
        //sendMessage(phone, "A pessoa caiu! - Localização: (" + lat + "," + lon + ")");
        //https://www.google.com/maps/dir/[latitude1],[longitude1]
        //sendMessage(phone, "Pessoa caiu! - Localização: https://www.google.com/maps/dir/36,-9");
        String lat = DataHolder.getInstance().getDataMap().get("gpslat");
        String lon = DataHolder.getInstance().getDataMap().get("gpslong");
        sendMessage(phone, "Pessoa caiu! - Localização: https://www.google.com/maps/dir/"+ lat + "," + lon);
        startActivity(callIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the scheduled phone call when the activity is destroyed
        LocalBroadcastManager.getInstance(this).unregisterReceiver(cancelCallReceiver);
        handler2.removeCallbacks(callRunnable);
    }

    public void sendMessage(String phoneNumber, String message) {
        /*
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "SMS permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }*/

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        Toast.makeText(this, "Mensagem de ajuda enviada!", Toast.LENGTH_SHORT).show();
    }

}