package com.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("NOTIFICATION_CLICKED_ACTION")) {
            Toast.makeText(context, "Continuação de boa viagem!", Toast.LENGTH_SHORT).show();

            // Send a local broadcast to notify the activity
            Intent cancelCallIntent = new Intent("CANCEL_PHONE_CALL_ACTION");
            LocalBroadcastManager.getInstance(context).sendBroadcast(cancelCallIntent);
        }
    }
}

