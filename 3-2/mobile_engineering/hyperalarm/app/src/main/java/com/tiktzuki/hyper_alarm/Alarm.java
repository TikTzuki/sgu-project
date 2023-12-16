package com.tiktzuki.hyper_alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.tiktzuki.hyper_alarm.activities.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class Alarm {
    private int alarmId;
    private int hour, minute;
    private String title;
    private UUID uuid;

    public int getAlarmId() {
        return alarmId;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getTitle() {
        return title;
    }

    public Alarm(int alarmId, int hour, int minute, String title) {
        this.alarmId = alarmId;
        this.hour = hour;
        this.minute = minute;
        this.title = title;
        this.uuid = UUID.randomUUID();
    }

    public boolean schedule(Context context) {
        List<Alarm> alarms = Alarm.getScheduledAlarm(context);
        for (Alarm alarm : alarms) {
            if (alarm.hour == this.hour && alarm.minute == this.minute) {
                return false;
            }
        }
        alarms.add(this);
        Alarm.writeScheduledAlarms(alarms, context);
        this.setAlarmClock(context, this.alarmId, this.title, this.hour, this.minute);
        return true;
    }

    public static List<Alarm> getScheduledAlarm(Context context) {
        JsonWrapper wrapper = new JsonWrapper();
        Gson gson = new Gson();
        File file = new File(context.getExternalFilesDir(Environment.getStorageDirectory().toString()) + "/" + Constants.SCHEDULED_ALARM_FILE);
        try {
            if (!file.exists() || file.length() == 0) {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(gson.toJson(wrapper).getBytes());
            }
            wrapper = gson.fromJson(new FileReader(file), JsonWrapper.class);

        } catch (IOException e) {
            e.printStackTrace();
            file.delete();
        }
        return wrapper.data;
    }

    public void remove(Context context) {
        List<Alarm> alarms = Alarm.getScheduledAlarm(context);
        alarms.removeIf(alarm -> alarm.uuid.equals(this.uuid));
        Alarm.writeScheduledAlarms(alarms, context);
    }

    public static void writeScheduledAlarms(List<Alarm> alarms, Context context) {
        try {
            File file = new File(context.getExternalFilesDir(Environment.getStorageDirectory().toString()) + "/" + Constants.SCHEDULED_ALARM_FILE);
            Gson gson = new Gson();
            JsonWrapper wrapper = new JsonWrapper();
            wrapper.data = alarms;
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(gson.toJson(wrapper).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAlarmClock(Context context, int notificationId, String title, int hour, int minute) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "ALARM_CHANNEL")
                .setSmallIcon(R.drawable.circle)
                .setContentTitle(title)
                .setContentText(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, AlarmBroadcastReceiver.class);
        notificationIntent.putExtra(Constants.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(Constants.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void removeAlarmClock(Context context, String title, int hour, int minute) {

    }

    @Override
    public String toString() {
        return "Alarm{" +
                "alarmId=" + alarmId +
                ", hour=" + hour +
                ", minute=" + minute +
                ", title='" + title + '\'' +
                ", uuid=" + uuid +
                '}';
    }

    static class JsonWrapper {
        List<Alarm> data = new ArrayList<>();
    }
}
