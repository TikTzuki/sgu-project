package com.tiktzuki.hyper_galerry.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tiktzuki.hyper_galerry.utils.Constants;

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

}
