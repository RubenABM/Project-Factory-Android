package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import static com.myapplication.MapFragment.firstMap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.downloadtasks.JSONObjToArray;
import com.myapplication.downloadtasks.PostMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class ActivityActivity extends AppCompatActivity {

    EditText percurso;
    TextView data, km;
    DrawerLayout drawer;
    Button editar, atualizar;

    ImageView iconTrip;
    static String iduser;
    public static String linestr;

    String idroute = "";
    String routeCoords = "";

    public static HashMap<String, String> routes = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        JSONObject loginjson = null;
        iduser = getIntent().getStringExtra("key");
        String firstURL = "http://35.176.222.11:5000/routes/user/" + iduser;
        percurso = findViewById(R.id.editTextPercurso);
        data = findViewById(R.id.editTextPercurso1);
        drawer = findViewById(R.id.drawer_layout);
        editar = findViewById(R.id.button);
        km = findViewById(R.id.editTextKm);
        atualizar = findViewById(R.id.button2);
        iconTrip = findViewById(R.id.imageView10);
        iconTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToTrip(v);
            }
        });
        JSONObject getjson = null;
        JSONObjToArray taskget = new JSONObjToArray();
        try {
            getjson = taskget.execute(firstURL).get();
            for (int i = 0; i < getjson.length(); i++){
                idroute = getjson.getString("route_id");
                routeCoords = getjson.getString("route_coord");
                routes.put(idroute, routeCoords);
            }

        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void EditarTexto(View view){

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setVisibility(View.INVISIBLE);
                km.setVisibility(View.INVISIBLE);
                percurso.setVisibility(View.VISIBLE);
                atualizar.setVisibility(View.VISIBLE);
                editar.setVisibility(View.INVISIBLE);

            }
        });

    }


    public void UpdateText(View v){

        try {
            String routeId = "";
            String iduser = getIntent().getStringExtra("key");
            Map<String, String> postData = new HashMap<>();
            postData.put("route_name", percurso.getText().toString());
            PostMethod task = new PostMethod(postData);
            String url = "http://35.176.222.11:5000/routes/updateroutename/" + iduser + "/" + routeId;
            task.execute(url);

            Toast.makeText(this,"Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            Toast.makeText(this,"ERRO!", Toast.LENGTH_SHORT).show();
        }

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
    public void ClickLogout(View view){
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


    public void GoToTrip(View view) {
        firstMap = false;
        String routeId = ""; //this needs to a correct value!!!
        //GET method
        JSONObject getjson = null;
        iduser = getIntent().getStringExtra("key");
        JSONObjToArray taskget = new JSONObjToArray();
        String url = "http://35.176.222.11:5000/routes/user/" + iduser + "/" + routeId;
        try {
            getjson = taskget.execute(url).get();
            linestr = getjson.getString("route_coord"); //asign linestring to public variable

        } catch (ExecutionException | InterruptedException | JSONException e) {
        e.printStackTrace();
        }
        StartActivity.goToActivity(this, StartActivity.class);
    }

}