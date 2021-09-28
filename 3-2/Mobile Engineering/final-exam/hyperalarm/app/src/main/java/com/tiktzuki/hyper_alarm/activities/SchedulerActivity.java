package com.tiktzuki.hyper_alarm.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;

import com.tiktzuki.hyper_alarm.Alarm;
import com.tiktzuki.hyper_alarm.R;

import java.util.Random;

public class SchedulerActivity extends BaseActivity {
    TimePicker timePicker;
    EditText title;
    Button scheduleAlarm;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduer);
        timePicker = findViewById(R.id.tpSchedulerAlarm);
        timePicker.setIs24HourView(true);
        title = findViewById(R.id.txtAlarmTitle);
        scheduleAlarm = findViewById(R.id.btnScheduleAlarm);

        scheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleAlarm();
            }
        });

    }


    private void scheduleAlarm() {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);

        Alarm alarm = new Alarm(
                alarmId,
                timePicker.getHour(),
                timePicker.getMinute(),
                title.getText().toString()
        );
        boolean is_success = alarm.schedule(this);
        if (!is_success) {
            showToast("Can't schedule this time value!");
        }
        showActivity(MainActivity.class);
    }
}
