package com.ir.notification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.format.Time;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;

public class Notify extends AppCompatActivity {

    public Notify(){

    }
    public void showNotification(String title, String message, Activity ax){
        NotificationManager mNotificationManager =
                (NotificationManager) ax.getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1",
                    "alarm",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notification channel for scheduled services");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ax, "1")
                .setSmallIcon(R.drawable.settings)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
