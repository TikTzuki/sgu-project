package com.tiktzuki.hyper_galerry.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EventListener;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ImageUtil {
    Context context;
    File storageDir;

    public ImageUtil(Context context) {
        this.context = context;
        this.storageDir = context.getExternalFilesDir(Environment.getStorageDirectory().toString());
    }

    public File saveBitmap(Bitmap bmp, String album) {
        try {
            File dir = new File(this.storageDir + "/" + album);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File newFile = generateStorageFile(dir);
            FileOutputStream fos = new FileOutputStream(newFile);
            BufferedOutputStream bufferOutStream = new BufferedOutputStream(fos);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bufferOutStream);
            bufferOutStream.flush();
            bufferOutStream.close();
            return newFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File generateStorageFile(File dir) throws IOException {
        File image = File.createTempFile("JPG", ".jpg", dir);
        return image;
    }

    public List<String> getAlbums() {
        List<String> dirs = Arrays.stream(Objects.requireNonNull(storageDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                long count = Arrays.stream(file.listFiles()).filter(f -> f.isFile()).count();
                return file.isDirectory() && count > 0;
            }
        }))).map(File::getName).collect(Collectors.toList());
        return dirs;
    }

    public List<File> getFileByAlbum(String album, int order) {
        File albumDir = new File(storageDir.getAbsolutePath() + "/" + album);
        if (!albumDir.exists()) {
            return null;
        }
        List<File> files = Arrays.asList(Objects.requireNonNull(albumDir.listFiles())).stream().sorted(new Comparator<File>() {
            @Override
            public int compare(File file, File t1) {
                if (order < 0)
                    return (int) (file.lastModified() - t1.lastModified());
                else
                    return (int) (t1.lastModified() - file.lastModified());
            }
        }).collect(Collectors.toList());
        return files;
    }

    public boolean moveImage(Context context, File image, String currentAlbum, String targetAlbum) {
        try {
            File targetDir = new File(storageDir + "/" + targetAlbum);
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            String[] imageNameParts = image.getName().split("\\.");
            File newImagePath = File.createTempFile("JPG", "." + imageNameParts[1], targetDir);
            File oldImagePath = new File(storageDir + "/" + currentAlbum + "/" + image.getName());
            oldImagePath.renameTo(newImagePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<File> getAllImage(File imageFolder) {
        File[] listFile = imageFolder.listFiles();
        if (listFile != null && listFile.length != 0) {
            return Arrays.asList(listFile).stream().filter(imageFile -> {
                String filename = imageFile.getName();
                return !imageFile.isDirectory() && (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png"));
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public void deleteImage(File image) {
        image.delete();
    }

}
