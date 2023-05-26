package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myapplication.downloadtasks.DownloadTask;
import com.myapplication.downloadtasks.JSONObjToArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ChallengesActivity extends AppCompatActivity {

    TextView textChalDesc, textPoints;
    ProgressBar progressBar;
    static String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        progressBar = findViewById(R.id.progressBarChallenge);
        textChalDesc = findViewById(R.id.textViewChallengeDescription);
        textPoints = findViewById(R.id.textViewChallengePoints);

        iduser = getIntent().getStringExtra("key");
        DownloadTask task = new DownloadTask();

        /*try {

            JSONArray challengesArray = task.execute("http://35.176.222.11:5000/challenges/").get();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                for(int i = 0; i < challengesArray.length(); i++)
                {
                    JSONObject jsonPart = challengesArray.getJSONObject(i);

                    RelativeLayout rl = new RelativeLayout(getBaseContext());
                    rl.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

                    ImageView img = new ImageView(getBaseContext());
                    img.setImageDrawable(getDrawable(R.drawable.trophy));
                    img.setLayoutParams(new RelativeLayout.LayoutParams(convertDpToPixel(100, getBaseContext()), convertDpToPixel(100, getBaseContext())));
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_RIGHT);
                    rl.addView(img);

                    TextView textDescription = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params2.addRule(RelativeLayout.CENTER_IN_PARENT);
                    textDescription.setLayoutParams(params2);
                    textDescription.setText(jsonPart.getString("chall_totalKm"));
                    rl.addView(textDescription);

                    TextView textPoints = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params3.addRule(RelativeLayout.CENTER_IN_PARENT);
                    textPoints.setLayoutParams(params3);
                    textPoints.setText(jsonPart.getString("chall_points"));
                    rl.addView(textPoints);

                    ProgressBar pb = new ProgressBar(getBaseContext());
                    RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params4.addRule(RelativeLayout.CENTER_IN_PARENT);
                    rl.addView(pb);
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }*/
    }

    public static int convertDpToPixel(int dp, Context context){
        return dp * ((int) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}