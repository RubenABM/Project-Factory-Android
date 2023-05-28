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

                    ImageView imgTrophy = new ImageView(getBaseContext());
                    imgTrophy.setImageDrawable(getDrawable(R.drawable.trophy));
                    imgTrophy.setLayoutParams(new RelativeLayout.LayoutParams(convertDpToPixel(100, getBaseContext()), convertDpToPixel(100, getBaseContext())));
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_RIGHT);
                    rl.addView(imgTrophy);

                    TextView textLocation = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params2.addRule(RelativeLayout.CENTER_IN_PARENT);
                    textLocation.setLayoutParams(params2);
                    textLocation.setText(jsonPart.getString("chall_coord"));
                    rl.addView(textLocation);

                    TextView textKm = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params3.addRule(RelativeLayout.CENTER_IN_PARENT);
                    textKm.setLayoutParams(params3);
                    textKm.setText(jsonPart.getString("chall_totalKm"));
                    rl.addView(textKm);

                    TextView textPoints = new TextView(getBaseContext());
                    RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params4.addRule(RelativeLayout.CENTER_IN_PARENT);
                    textPoints.setLayoutParams(params4);
                    textPoints.setText(jsonPart.getString("chall_points"));
                    rl.addView(textPoints);

                    ImageView imgDone = new ImageView(getBaseContext());
                    imgDone.setImageDrawable(getDrawable(R.drawable.done));
                    imgDone.setLayoutParams(new RelativeLayout.LayoutParams(convertDpToPixel(100, getBaseContext()), convertDpToPixel(100, getBaseContext())));
                    RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    params5.addRule(RelativeLayout.ALIGN_RIGHT);
                    rl.addView(imgDone);
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