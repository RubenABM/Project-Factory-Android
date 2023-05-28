package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import static com.myapplication.StartActivity.startingflag;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.downloadtasks.JSONArr;
import com.myapplication.downloadtasks.JSONObj;
import com.myapplication.downloadtasks.JSONObjToArray;
import com.myapplication.downloadtasks.PostMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "HTTPDIR";
    EditText editTextTextEmailAddress;

    EditText editTextTextPassword2;

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword2 = findViewById(R.id.editTextTextPassword2);
        loginButton = findViewById(R.id.loginButton);

        //String dir = getApplicationInfo().dataDir;

        if (!checkPermissions()) {
            requestPermissions();
        }

        if (!checkPermissions()) {
            Log.d(TAG, "Não foram dadas permissões");
            finish();
        } else {
            Log.d(TAG, "Já há permissões de escrita");
        }

        File filesDir = getFilesDir();
        String dir = filesDir.getAbsolutePath();
        Log.d(TAG,"CAMINHO:" + dir);

        Context context = getApplicationContext();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        @SuppressWarnings("deprecation")
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        Log.d(TAG, "IP: " + ip);

        Log.d(TAG, "FilesDIR: " + filesDir);
        criaIndex(filesDir, ip);
        //criaIndex2("", ip);

        //TinyWebServer.startServer("localhost",8080, dir);
        TinyWebServer.startServer(ip,8080, dir);

        // Dados do post
        Map<String, String> postData = new HashMap<>();
        postData.put("newText", ip);
        PostMethod task = new PostMethod(postData);
        task.execute("http://35.176.222.11:5000/updateip");


    }

    public void ClickToCreate(View view) {

        //METODO POST - TENTAR MAIS TARDE

        /*
        JSONObject loginjson = null;

        try {

            String email = editTextTextEmailAddress.getText().toString();
            String pass = editTextTextPassword2.getText().toString();

            // Dados do post
            Map<String, String> postData = new HashMap<>();
            postData.put("user_email", email);
            postData.put("user_password", pass);

            // Post call
            PostMethod task = new PostMethod(postData);


            //Log.i("TAGGGGGGGGGGGGGGGGGGGGG", task.execute("http://13.40.214.190:5000/users/login").toString());
            loginjson = task.execute("http://13.40.214.190:5000/users/login");

            if(loginjson != null)
            {
                Toast.makeText(this, "Bem vindo !!", Toast.LENGTH_SHORT).show();

                // Trocar de activity
                Intent myIntent = new Intent(this, MainActivity.class);
                this.startActivity(myIntent);

            }else{
                Toast.makeText(this,"Dados incorretos 2!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Dados incorretos 3!", Toast.LENGTH_SHORT).show();
        }*/

        JSONObject loginjson = null;

        JSONObjToArray task = new JSONObjToArray();
        try {

            //Metodo get com 2 parametros
            loginjson = task.execute("http://35.176.222.11:5000/users/login2/" + editTextTextEmailAddress.getText().toString() + "/" + editTextTextPassword2.getText().toString()).get();

            if (loginjson != null) {
                startingflag = true;
                Toast.makeText(this, "Bem vindo " + loginjson.getString("user_name") + " !!!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(this, StartActivity.class);
                myIntent.putExtra("key", loginjson.getString("user_id"));
                this.startActivity(myIntent);
            } else {
                Toast.makeText(this, "Os dados inseridos estão incorretos!!", Toast.LENGTH_LONG).show();
            }


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

    public void GoCreate(View view) {
        Intent myIntent = new Intent(this, CreateAccountActivity.class);
        this.startActivity(myIntent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //stop webserver on destroy of service or process
        TinyWebServer.stopServer();
    }

    private boolean checkPermissions() {
        Log.d(TAG, "checkPermissions");
        boolean perm = PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                ;
        return  perm;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },
                1);
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

    public void criaIndex2(String filesDir, String ip) {
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

}