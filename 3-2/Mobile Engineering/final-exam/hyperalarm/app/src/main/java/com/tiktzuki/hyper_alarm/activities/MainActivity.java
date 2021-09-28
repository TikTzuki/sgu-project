package com.tiktzuki.hyper_alarm.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tiktzuki.hyper_alarm.Alarm;
import com.tiktzuki.hyper_alarm.R;
import com.tiktzuki.hyper_alarm.ScheduledAlarmAdapter;

import java.util.List;

public class MainActivity extends BaseActivity {
    ScheduledAlarmAdapter scheduledAlarmAdapter;
    ListView scheduledAlarm;
    Button btnAddScheduleAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduledAlarm = (ListView) findViewById(R.id.listAlarm);
        btnAddScheduleAlarm = (Button) findViewById(R.id.btnAddAlarm);

        List<Alarm> alarms = Alarm.getScheduledAlarm(this);
        scheduledAlarmAdapter = new ScheduledAlarmAdapter(this, R.layout.alarm_item, alarms);
        scheduledAlarm.setAdapter(scheduledAlarmAdapter);

        btnAddScheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActivity(SchedulerActivity.class);
            }
        });
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("ALARM_CHANNEL", "ALARM_CHANNEL", importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}