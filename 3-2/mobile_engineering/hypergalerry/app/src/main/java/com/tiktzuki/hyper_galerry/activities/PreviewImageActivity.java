package com.tiktzuki.hyper_galerry.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tiktzuki.hyper_galerry.R;
import com.tiktzuki.hyper_galerry.SplashActivity;
import com.tiktzuki.hyper_galerry.utils.Constants;
import com.tiktzuki.hyper_galerry.utils.DialogUtil;
import com.tiktzuki.hyper_galerry.utils.ImageUtil;
import com.tiktzuki.hyper_galerry.utils.LoadImageTask;
import com.tiktzuki.hyper_galerry.utils.UIUtils;

import java.io.File;
import java.util.List;

public class PreviewImageActivity extends BaseActivity {
    ImageUtil imageUtil;
    ImageView ivImage;
    File image;
    AutoCompleteTextView acFormAlbum;
    Button btnMoveImage;
    TextView txtImageAlbum;
    ImageView ivRemoveImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);
        imageUtil = new ImageUtil(this);
        acFormAlbum = findViewById(R.id.acChoosingAlbum);
        btnMoveImage = findViewById(R.id.btnMoveImage);
        ivImage = findViewById(R.id.ivPreviewImage);
        txtImageAlbum = findViewById(R.id.txtImageAlbum);
        ivRemoveImage = findViewById(R.id.ivRemoveImage);

        Intent intent = getIntent();
        String imagePath = intent.getStringExtra(Constants.KEY_IMAGE_PATH);
        image = new File(imagePath);
        new LoadImageTask(ivImage).execute(image);
        String[] temp = imagePath.split("/");
        String currentAlbum = temp[temp.length - 2];
        txtImageAlbum.setText(currentAlbum);

        List<String> albums = imageUtil.getAlbums();
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, albums);
        acFormAlbum.setAdapter(adapterFrom);
        acFormAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = adapterFrom.getItem(position);
                acFormAlbum.setText(text);
            }
        });

        btnMoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UIUtils.onlyText(acFormAlbum.getText().toString())) {
                    showToast("Invalid characters");
                    return;
                }
                boolean isMoved = imageUtil.moveImage(view.getContext(), image, currentAlbum, acFormAlbum.getText().toString());
                if (isMoved) {
                    showToast("Image is moved to album " + acFormAlbum.getText().toString());
                    showActivity(SplashActivity.class);
                }
            }
        });

        ivRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.createAlertDialog(view.getContext(), "Delete image", "Are U soure?!!!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                imageUtil.deleteImage(image);
                                showActivity(SplashActivity.class);
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();

            }
        });

    }

    private float x1, x2;
    static final int MIN_DISTANCE = 50;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    List<File> images = imageUtil.getFileByAlbum(txtImageAlbum.getText().toString(), SplashActivity.ORDER);
                    if (x2 > x1) {
                        int index = Math.max(images.indexOf(image) - 1, 0);
                        loadImage(images.get(index));
                    } else {
                        int index = images.indexOf(image) + 1 < images.size() ? images.indexOf(image) + 1 : 0;
                        loadImage(images.get(index));
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void loadImage(File newImage) {
        this.image = newImage;
        if (newImage != null && !newImage.isDirectory()) {
            new LoadImageTask(ivImage).execute(newImage);
        }
    }
}
