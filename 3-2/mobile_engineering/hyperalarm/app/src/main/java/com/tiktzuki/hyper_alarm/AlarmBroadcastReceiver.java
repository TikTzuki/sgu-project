package com.tiktzuki.hyper_alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "not thing to show", Toast.LENGTH_SHORT).show();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        Notification notification = intent.getParcelableExtra(Constants.NOTIFICATION);
        int notificationId = intent.getIntExtra(Constants.NOTIFICATION_ID, 1);
        notificationManagerCompat.notify(notificationId, notification);
    }
}
