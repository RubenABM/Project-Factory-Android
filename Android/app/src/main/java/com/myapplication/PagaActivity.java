package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PagaActivity extends AppCompatActivity {

    TextView anual, mensal;
    Button continuar;

    ImageView anual1, mensal1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paga);


        mensal = findViewById(R.id.textViewMensal);
        continuar = findViewById(R.id.buttonChange);
        anual = findViewById(R.id.textViewAnual);
    }




    public void Onclick(View view){

        ImageView iconImageView = findViewById(R.id.imageViewAnual);
        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconImageView.setColorFilter(getResources().getColor(android.R.color.holo_red_dark));
            }
        });

    }

    public void Onclick1(View view){

        ImageView iconImageView = findViewById(R.id.imageViewMensal);
        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconImageView.setColorFilter(getResources().getColor(android.R.color.holo_red_dark));
            }
        });



    }

    public void Onclick4(View view){

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PagaActivity.this, SubscriptionActivity.class);
                startActivity(intent);
            }
        });

    }
}