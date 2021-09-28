package com.tiktzuki.hyper_galerry.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;

import com.tiktzuki.hyper_galerry.R;
import com.tiktzuki.hyper_galerry.activities.PreviewImageActivity;
import com.tiktzuki.hyper_galerry.utils.Constants;
import com.tiktzuki.hyper_galerry.utils.ImageUtil;
import com.tiktzuki.hyper_galerry.utils.UIUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumAdapter extends ArrayAdapter<String> {
    private ImageUtil imageUtil;
    int order;

    public AlbumAdapter(@NonNull Context context, @NonNull List<String> objects, int order) {
        super(context, R.layout.album_item, objects);
        imageUtil = new ImageUtil(context);
        this.order = order;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View row = layoutInflater.inflate(R.layout.album_item, null);

        ListView lvImage = row.findViewById(R.id.lvImage);
        TextView txtAlbumName = row.findViewById(R.id.txtAlbumName);

        String albumName = getItem(position);
        txtAlbumName.setText(albumName);

        List<File> images = this.imageUtil.getFileByAlbum(albumName, this.order);
        ImageAdapter imageAdapter = new ImageAdapter(getContext(), images);
        lvImage.setAdapter(imageAdapter);
        UIUtils.setListViewHeightBasedOnItems(lvImage);

        lvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), PreviewImageActivity.class);
                intent.putExtra(Constants.KEY_IMAGE_PATH, images.get(position).getAbsolutePath());
                view.getContext().startActivity(intent);
            }
        });
        return row;
    }

}
