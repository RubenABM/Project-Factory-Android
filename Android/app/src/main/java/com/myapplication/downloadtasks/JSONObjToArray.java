package com.myapplication.downloadtasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONObjToArray extends AsyncTask<String, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(String... urls) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;
        JSONArray jarr = null;
        JSONObject jobj;
        try{

            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();
            while(data != -1)
            {
                char current = (char)data;
                result += current;
                data = reader.read();
            }

            jarr = new JSONArray(result);
            jobj = jarr.getJSONObject(0);
            Log.i("", urls[0]);

            return jobj;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}