package com.tiktzuki.hyper_galerry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.tiktzuki.hyper_galerry.activities.BaseActivity;
import com.tiktzuki.hyper_galerry.adapters.AlbumAdapter;
import com.tiktzuki.hyper_galerry.utils.ImageUtil;
import com.tiktzuki.hyper_galerry.utils.UIUtils;


import java.util.List;

public class SplashActivity extends BaseActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageUtil imageUtil;
    ImageView btnSortImageByDate;
    ListView lvAlbums;
    ImageView ivSplash;
    LinearLayout lnAlbum;
    public static int ORDER = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageUtil = new ImageUtil(this);

        btnSortImageByDate = findViewById(R.id.ivShortImageByDate);
        lvAlbums = findViewById(R.id.lv_albums);
        ivSplash = findViewById(R.id.iv_splash);
        lnAlbum = findViewById(R.id.lnAlbum);

        loadAlbums();

        ivSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        btnSortImageByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortImage();
            }
        });
    }

    private void loadAlbums() {
        List<String> albums = imageUtil.getAlbums();
        AlbumAdapter albumAdapter = new AlbumAdapter(this, albums, ORDER);
        lvAlbums.setAdapter(albumAdapter);
        int height = UIUtils.setListViewHeightBasedOnItems(lvAlbums);
        ViewGroup.LayoutParams params = lnAlbum.getLayoutParams();
        params.height += height;
        lnAlbum.setLayoutParams(params);
        lnAlbum.requestLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAlbums();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageUtil.saveBitmap(imageBitmap, "default");
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void sortImage() {
        this.ORDER = -(this.ORDER);
        if (this.ORDER > 0)
            btnSortImageByDate.setImageResource(R.drawable.arrow_up);
        else
            btnSortImageByDate.setImageResource(R.drawable.arrow_down);

        loadAlbums();
    }

}
