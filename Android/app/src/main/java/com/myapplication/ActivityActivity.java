package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
//import static com.myapplication.MapFragment.firstMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    static String iduser;
    public static String linestr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        JSONObject loginjson = null;

        percurso = findViewById(R.id.editTextPercurso);
        data = findViewById(R.id.editTextPercurso1);
        drawer = findViewById(R.id.drawer_layout);
        editar = findViewById(R.id.button);
        km = findViewById(R.id.editTextKm);
        atualizar = findViewById(R.id.button2);



        // Metodo GET para ir buscar os dados do percurso do utilizador
        linestr = getIntent().getStringExtra("key");
        JSONObjToArray task = new JSONObjToArray();
        try {
            loginjson = task.execute("http://35.176.222.11:5000/users/data/2/3").get();
            data.setText(loginjson.getString("user_route"));
            Log.d("AQUIIII:::::", loginjson.toString());

        }catch (ExecutionException e){
            e.printStackTrace();
            loginjson = null;
        } catch (InterruptedException e){
            e.printStackTrace();
            loginjson = null;
        }catch (JSONException e){
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

        //Metodo Post para ir buscar as rotas do utilizador
        String percursos = percurso.getText().toString();


        Map<String, String> postData = new HashMap<>();
        postData.put("user_route", percursos);


        PostMethod task1 = new PostMethod(postData);

        //Post call
        try {

            task1.execute("http://35.176.222.11:5000/routes/updateroutename/1/1");
            Log.d("AQUIII", task1.execute("http://35.176.222.11:5000/routes/updateroutename/" + iduser).toString());

            Toast.makeText(this,"Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            Toast.makeText(this,"ERRO!", Toast.LENGTH_SHORT).show();
        }

    }

    public static void goToActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
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





}