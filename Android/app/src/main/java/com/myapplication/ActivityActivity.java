package com.myapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import static com.myapplication.MapFragment.firstMap;
import static com.myapplication.StartActivity.startingflag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.downloadtasks.JSONArr;
import com.myapplication.downloadtasks.JSONObjToArray;
import com.myapplication.downloadtasks.PostMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        String firstURL = "http://35.176.222.11:5000/routes/user/" + iduser;
        drawer = findViewById(R.id.drawer_layout);
        atualizar = findViewById(R.id.editButton);
        iconTrip = findViewById(R.id.imageView10);

        JSONArray getjson = null;
        JSONArr taskget = new JSONArr();
        JSONObject test = null;
        try {
            getjson = taskget.execute(firstURL).get();
            Log.d(TAG, getjson.toString());
            for (int i = 0; i < getjson.length(); i++) {
                test = getjson.getJSONObject(i);
                routeCards = findViewById(R.id.routeCard);
                idroute = test.getString("route_id");
                routeCoords = test.getString("route_coord");
                Log.d(TAG, routeCoords);
                routes.put(idroute, routeCoords);
                startDnTime = findViewById(R.id.dateNTimeStart);
                //String tripTime = getjson.getString("data_startTime") + " - " + getjson.getString("data_endTime");
                String tripTime = "GET fix!";
                startDnTime.setText(tripTime);
                idrout = findViewById(R.id.routeid);
                idrout.setText(idroute);
                routeName = findViewById(R.id.editTextPercurso1);
                routeName.setText(test.getString("route_name"));

            }

        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }

        iconTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToTrip(v, idroute);
            }
        });

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateText(v, idrout.toString());
            }
        });
    }

    public void UpdateText(View v, String routeId) {

        try {
            Map<String, String> postData = new HashMap<>();
            postData.put("route_name", routeName.getText().toString());
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


    public void ClickHelmets(View view) {
        StartActivity.goToActivity(this, HelmetsActivity.class);
    }

    public void ClickActivity(View view) {
        StartActivity.goToActivity2(this, ActivityActivity.class, iduser);
    }

    public void ClickChallenges(View view) {
        StartActivity.goToActivity(this, ChallengesActivity.class);
    }

    public void ClickHealth(View view) {
        StartActivity.goToActivity(this, HealthActivity.class);
    }

    public void ClickPoints(View view) {
        StartActivity.goToActivity(this, PointsActivity.class);
    }

    public void ClickProfile(View view) {
        StartActivity.goToActivity(this, ProfileActivity.class);
    }

    public void ClickSubscription(View view) {
        StartActivity.goToActivity(this, SubscriptionActivity.class);
    }

    public void ClickSettings(View view) {
        StartActivity.goToActivity(this, SettingsActivity.class);
    }

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

}