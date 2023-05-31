package com.myapplication;

import static com.myapplication.StartActivity.Logout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class ProfileActivity extends AppCompatActivity {

    EditText nameBox, mailBox, healthBox, pointBox, passBox, pass2Box;
    Button update;
    TextView points, username, useremail, userpass, editprofile, UserName, UserEmail, UserPass, Points;
    DrawerLayout drawerLayout;
    static String iduser;
    DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        JSONObject loginjson = null;

        nameBox = findViewById(R.id.Editname);
        mailBox = findViewById(R.id.Editmail);
        passBox = findViewById(R.id.EditPass);
        points = findViewById(R.id.pointBox2);
        username = findViewById(R.id.userName);
        useremail = findViewById(R.id.userEmail);
        userpass = findViewById(R.id.userPass);
        editprofile = findViewById(R.id.textView10);
        update = findViewById(R.id.button);
        UserName = findViewById(R.id.userName3);
        UserEmail = findViewById(R.id.userEmail3);
        UserPass = findViewById(R.id.userPass3);
        Points = findViewById(R.id.pointBox);
        drawer = findViewById(R.id.drawer_layout);

        String name = nameBox.getText().toString();
        String email = mailBox.getText().toString();
        String pass = passBox.getText().toString();




        //if (email.isEmpty() || name.isEmpty() || pass.isEmpty()) {
           // Toast.makeText(getBaseContext(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();

        //}
       // if (!pass.equals(pass2)) {
          //  Toast.makeText(getBaseContext(), "As palavras passes não coincidem!", Toast.LENGTH_LONG).show();

        //}


        // Metodo Get para ir buscar os pontos do utilizador

        iduser = getIntent().getStringExtra("key");
        //JSONObj task = new JSONObj();
        JSONObjToArray task = new JSONObjToArray();
        try {
            loginjson = task.execute("http://35.176.222.11:5000/users/"+iduser).get();
            points.setText(loginjson.getString("user_points"));
            username.setText(loginjson.getString("user_name"));
            useremail.setText(loginjson.getString("user_email"));
            userpass.setText(loginjson.getString("user_password"));
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
    public void EditProfile(View view){

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameBox.setVisibility(View.VISIBLE);
                mailBox.setVisibility(View.VISIBLE);
                passBox.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);

                username.setVisibility(View.INVISIBLE);
                useremail.setVisibility(View.INVISIBLE);
                userpass.setVisibility(View.INVISIBLE);
                points.setVisibility(View.INVISIBLE);
                UserName.setVisibility(View.INVISIBLE);
                UserEmail.setVisibility(View.INVISIBLE);
                UserPass.setVisibility(View.INVISIBLE);
                Points.setVisibility(View.INVISIBLE);

            }
        });

    }


    public void UpdateProfile(View v){

        //Metodo Post para ir buscar os dados do utilizador, username, useremail, userpass
        String name = nameBox.getText().toString();
        String email = mailBox.getText().toString();
        String pass = passBox.getText().toString();

        Map<String, String> postData = new HashMap<>();
        postData.put("user_name", name);
        postData.put("user_email", email);
        postData.put("user_password", pass);

        PostMethod task1 = new PostMethod(postData);

        //Post call
        try {

        task1.execute("http://35.176.222.11:5000/users/updateuser/"+iduser);
        //Log.d("AQUIII", task1.execute("http://35.176.222.11:5000/users/updateuser/" + iduser).toString());

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


    public void ClickHelmets(View view){StartActivity.goToActivity2(this, HelmetsActivity.class,getIntent().getStringExtra("key"));}
    public void ClickActivity(View view){StartActivity.goToActivity2(this, ActivityActivity.class,getIntent().getStringExtra("key"));}
    public void ClickChallenges(View view){StartActivity.goToActivity2(this, ChallengesActivity.class,getIntent().getStringExtra("key"));}
    public void ClickHealth(View view){StartActivity.goToActivity2(this, HealthActivity.class,getIntent().getStringExtra("key"));}
    public void ClickPoints(View view){StartActivity.goToActivity2(this, PointsActivity.class,getIntent().getStringExtra("key"));}
    public void ClickProfile(View view){StartActivity.goToActivity2(this, ProfileActivity.class,getIntent().getStringExtra("key"));}
    public void ClickSubscription(View view){StartActivity.goToActivity2(this, SubscriptionActivity.class,getIntent().getStringExtra("key"));}
    public void ClickSettings(View view){StartActivity.goToActivity2(this, SettingsActivity.class,getIntent().getStringExtra("key"));}

    public void ClickMap(View view){StartActivity.goToActivity2(this, StartActivity.class,getIntent().getStringExtra("key"));}


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