package com.tiktzuki.hyper_galerry.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiktzuki.hyper_galerry.R;
import com.tiktzuki.hyper_galerry.utils.LoadImageTask;
import com.tiktzuki.hyper_galerry.utils.UIUtils;

import java.io.File;
import java.util.Date;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<File> {
    public ImageAdapter(@NonNull Context context, List<File> objects) {
        super(context, R.layout.image_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View row = layoutInflater.inflate(R.layout.image_item, null);
        ImageView iv = row.findViewById(R.id.ivImage);
        TextView txtImageName = row.findViewById(R.id.txtImageName);
        TextView txtDate = row.findViewById(R.id.txtImageTime);

        File image = getItem(position);
        txtImageName.setText(image.getName());
        txtDate.setText(new Date(image.lastModified()).toLocaleString());
        new LoadImageTask(iv).execute(image);
        return row;
    }
}
