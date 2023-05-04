package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        JSONObject loginjson = null;

        Subscricao = findViewById(R.id.textView6);
        Alteracao = findViewById(R.id.buttonChange);
        Atual  = findViewById(R.id.imageView5);
        Paga = findViewById(R.id.imageView6);
        Gratis = findViewById(R.id.imageView7);
        Paga1 = findViewById(R.id.textView7);
        Gratis1 = findViewById(R.id.textView9);
        Confirmar = findViewById(R.id.buttonChange2);

        // Metodo Get para ir buscar a subscrição do utilizador

        iduser = getIntent().getStringExtra("key");
        //JSONObj task = new JSONObj();
        JSONObjToArray task = new JSONObjToArray();
        try {
            loginjson = task.execute("http://13.40.214.190:5000/users/1").get();
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

    public void Paga2(View view){
        Paga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Alteracao.setVisibility(View.VISIBLE);
                Atual.setVisibility(View.INVISIBLE);
                Subscricao.setVisibility(View.INVISIBLE);

                Paga.setVisibility(View.VISIBLE);
                Paga1.setVisibility(View.VISIBLE);



            }
        });

    }

    public void Gratis2(View view){

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

    }

    //public void UpdateProfile(View v){

        //Metodo Post para mostrar a nova subscrição
        //String subscricao = Paga.toString();
        //String subscricao2 = Gratis.toString();


       // Map<String, String> postData = new HashMap<>();
        // postData.put("user_subscricao", subscricao);
        // postData.put("user_subscricao", subscricao2);




        // PostMethod task1 = new PostMethod(postData);

        //Post call
        // try {

           // task1.execute("http://13.40.214.190:5000/users/updateuser");
            // Log.d("AQUIII", task1.execute("http://13.40.214.190:5000/users/updateuser/1" + iduser).toString());

            // Toast.makeText(this,"Selecione uma subscrição", Toast.LENGTH_SHORT).show();

        // } catch (Exception e){
           // Toast.makeText(this,"ERRO!", Toast.LENGTH_SHORT).show();
        //}

    //}

}