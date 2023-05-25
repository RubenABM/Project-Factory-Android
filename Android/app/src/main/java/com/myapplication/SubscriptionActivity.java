package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.downloadtasks.JSONObjToArray;
import com.myapplication.downloadtasks.PostMethod;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

public class SubscriptionActivity extends AppCompatActivity {


    TextView Subscricao, Paga1, Gratis1;
    Button Alteracao, Confirmar;
    ImageView Atual, Paga, Gratis;
    static String iduser;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        JSONObject loginjson = null;

        Subscricao = findViewById(R.id.textViewAtual);
        Alteracao = findViewById(R.id.buttonChange);
        Atual  = findViewById(R.id.imageViewAtual);
        Paga = findViewById(R.id.imageViewGratis);
        Gratis = findViewById(R.id.imageViewPaga);
        Paga1 = findViewById(R.id.textViewGratis);
        Gratis1 = findViewById(R.id.textViewPaga);
        Confirmar = findViewById(R.id.buttonChange2);
        drawer = findViewById(R.id.drawer_layout);

        // Metodo Get para ir buscar a subscrição do utilizador

        iduser = getIntent().getStringExtra("key");
        //JSONObj task = new JSONObj();
        JSONObjToArray task = new JSONObjToArray();
        try {
            loginjson = task.execute("http://35.176.222.11:5000/users/1").get();
            Subscricao.setText(loginjson.getString("user_subscription"));
            Log.d("AQUIIII::::", loginjson.toString());

        } catch (ExecutionException e) {
            e.printStackTrace();
            loginjson = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            loginjson = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Alterar(View view){

        Alteracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subscricao.setVisibility(View.INVISIBLE);
                Alteracao.setVisibility(View.INVISIBLE);
                Atual.setVisibility(View.INVISIBLE);

                Paga.setVisibility(View.VISIBLE);
                Paga1.setVisibility(View.VISIBLE);
                Gratis.setVisibility(View.VISIBLE);
                Gratis1.setVisibility(View.VISIBLE);

            }


        });

    }



    /*public void Gratis2(View view){

        Gratis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Alteracao.setVisibility(View.VISIBLE);
                Atual.setVisibility(View.INVISIBLE);
                Subscricao.setVisibility(View.INVISIBLE);

                Gratis.setVisibility(View.VISIBLE);
                Gratis1.setVisibility(View.VISIBLE);


            }
        });

    }*/

    /*public void Pagar2(View view){
        Paga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alteracao.setVisibility(View.VISIBLE);
                Atual.setVisibility(View.INVISIBLE);
                Subscricao.setVisibility(View.INVISIBLE);

                Paga.setVisibility(View.VISIBLE);
                Paga.setVisibility(View.VISIBLE);
            }
        });
    }*/

    public void UpdateProfile(View v){

        //Metodo Post para mostrar a nova subscrição
        String subscricao = Paga.toString();
        String subscricao2 = Gratis.toString();


        Map<String, String> postData = new HashMap<>();
        postData.put("user_subscricao", subscricao);
        postData.put("user_subscricao", subscricao2);

        PostMethod task1 = new PostMethod(postData);

        //Post call
        try {

           task1.execute("http://35.176.222.11:5000/users/updateuser");
           Log.d("AQUIII", task1.execute("http://35.176.222.11:5000/users/updateuser/1" + iduser).toString());

           Toast.makeText(this,"Selecione uma subscrição", Toast.LENGTH_SHORT).show();

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

}