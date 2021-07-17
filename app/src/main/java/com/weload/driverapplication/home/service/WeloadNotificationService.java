package com.weload.driverapplication.home.service;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.weload.driverapplication.R;
import com.weload.driverapplication.home.view.activites.JobListActivity;
import com.weload.driverapplication.util.AppConstants;

public class WeloadNotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        getFirebaseMessage(remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody());

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        AppConstants.Preferences.setStringPreferences(this,AppConstants.Keys.FIREBASE_TOKEN,s);
    }

    public void getFirebaseMessage(String title, String msg){
        Intent intent = new Intent(getApplicationContext(), JobListActivity.class);
      startActivity(intent);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_weload_driver_logo_02)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true);
        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(this);
        managerCompat.notify(101,builder.build());
        Log.d("Log","title = "+title);
        Log.d("Log","mesg"+msg);
    }
}
