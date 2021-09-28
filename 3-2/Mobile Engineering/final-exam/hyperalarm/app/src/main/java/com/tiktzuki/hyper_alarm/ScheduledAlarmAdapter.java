package com.tiktzuki.hyper_alarm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ScheduledAlarmAdapter extends ArrayAdapter {

    public ScheduledAlarmAdapter(@NonNull Context context, int resource, @NonNull List<Alarm> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Alarm alarm = (Alarm) super.getItem(position);
        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
        View row = layoutInflater.inflate(R.layout.alarm_item, null);
        TextView scheduleTime = row.findViewById(R.id.txtScheduleTime);
        TextView title = row.findViewById(R.id.txtScheduleTitle);
        Button btn = row.findViewById(R.id.btnDeleteAlarm);
        scheduleTime.setText(String.format("%d:%d", alarm.getHour(), alarm.getMinute()));
        title.setText(alarm.getTitle());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alarm alarm = (Alarm) getItem(position);
                alarm.remove(getContext());
                remove(alarm);
            }
        });
        return row;
    }

}
