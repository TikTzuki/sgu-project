package com.tiktzuki.hyper_alarm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tiktzuki.hyper_alarm.Constants;

public abstract class BaseActivity extends AppCompatActivity {


    public void showToast(String msg) {
        if (msg != null) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "not thing to show", Toast.LENGTH_SHORT).show();
        }
    }

    public void showActivity(Class t) {
        Intent intent = new Intent(this, t);
        startActivity(intent);
    }

    public void showActivity(Class t, Bundle bundle) {
        Intent intent = new Intent(this, t);
        intent.putExtra(Constants.KEY_EXTRA, bundle);
        startActivity(intent);
    }
}
