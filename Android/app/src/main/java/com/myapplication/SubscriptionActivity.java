package com.myapplication;

import static com.myapplication.StartActivity.Logout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
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


    TextView Subscricao, paga, gratis;
    Button alterar, back;

    ImageView paga1, gratis1, atual1;
    static String iduser;
    DrawerLayout drawer;

    String sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        JSONObject json = null;

        Subscricao = findViewById(R.id.textViewAtual);
        atual1 = findViewById(R.id.imageViewAtual);
        alterar = findViewById(R.id.buttonChange);
        back = findViewById(R.id.buttonBack);
        paga = findViewById(R.id.textViewPaga);
        gratis = findViewById(R.id.textViewGratis);
        paga1 = findViewById(R.id.imageViewPaga);
        gratis1 = findViewById(R.id.imageViewGratis);
        drawer = findViewById(R.id.drawer_layout);

        // Metodo Get para ir buscar a subscrição do utilizador

        iduser = getIntent().getStringExtra("key");
        //JSONObj task = new JSONObj();
        JSONObjToArray task = new JSONObjToArray();
        String tier = "";

        //Subscricao.setText("teste");

        try {
            json = task.execute("http://35.176.222.11:5000/users/1").get();

            tier = json.getString("user_subscription");

            if(tier.equals("Free")) {
                Subscricao.setText("Subscrição grátis");
            } else if(tier.equals("Premium")) {
                Subscricao.setText("Subscrição paga");
            }

            //Subscricao.setText(json.getString("user_subscription"));

        } catch (ExecutionException e) {
            e.printStackTrace();
            json = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            json = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }






    //Ir para a pagina das subscrições pagas
    public void Onclick1(View view){

        paga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubscriptionActivity.this, PagaActivity.class);
                startActivity(intent);
            }
        });

    }

    //Botão para fazer return para a pagina principal das subscrições
    public void Onclick3(View view){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubscriptionActivity.this, SubscriptionActivity.class);
                startActivity(intent);
            }
        });

    }

    public void OnClickAlterar(View view){

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObjToArray task = new JSONObjToArray();
                JSONObject json = null;
                String tier = "";

                try {
                    json = task.execute("http://35.176.222.11:5000/users/1").get();

                    tier = json.getString("user_subscription");

                    if(tier.equals("Free")) {
                        Subscricao.setVisibility(View.INVISIBLE);
                        atual1.setVisibility(View.INVISIBLE);
                        paga.setVisibility(View.VISIBLE);
                        paga1.setVisibility(View.VISIBLE);
                        gratis.setVisibility(View.INVISIBLE);
                        gratis1.setVisibility(View.INVISIBLE);
                        back.setVisibility(View.VISIBLE);
                        alterar.setVisibility(View.INVISIBLE);
                    } else if(tier.equals("Premium")) {
                        Subscricao.setVisibility(View.INVISIBLE);
                        atual1.setVisibility(View.INVISIBLE);
                        paga.setVisibility(View.INVISIBLE);
                        paga1.setVisibility(View.INVISIBLE);
                        gratis.setVisibility(View.VISIBLE);
                        gratis1.setVisibility(View.VISIBLE);
                        back.setVisibility(View.VISIBLE);
                        alterar.setVisibility(View.INVISIBLE);
                    }

                    //Subscricao.setText(json.getString("user_subscription"));

                } catch (ExecutionException e) {
                    e.printStackTrace();
                    json = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    json = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        });
    }

    public void OnclickG(View view){
        //Só funciona a clicar na estrela e não no texto!
        gratis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> postData = new HashMap<>();
                postData.put("user_subscription", "Free");
                PostMethod task = new PostMethod(postData);

                //Post call
                try {

                    task.execute("http://35.176.222.11:5000/users/updatesubscription/1");
                    //Log.d("AQUIII", task.execute("http://35.176.222.11:5000/users/updatesubscription/1").toString());

                    Toast.makeText(SubscriptionActivity.this,"Subscrição atualizada!", Toast.LENGTH_SHORT).show();

                } catch (Exception e){
                    Toast.makeText(SubscriptionActivity.this,"ERRO!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /*public void UpdateProfile(View v){

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
        }*/

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