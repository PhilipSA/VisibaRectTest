package com.example.visiba.visibarectest.Repositories;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.Repositories.Abstractions.BaseRepository;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class AppImageRepository extends BaseRepository
{
    private final String myImagesDirectory = String.format("%s%s%s", File.separator, "MyImages", File.separator);

    public AppImageRepository(Context context) {
        super(context);
    }

    public void saveAppImage(AppImage appImage){
        ByteBuffer buffer = appImage.image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        try {
            save(bytes, myImagesDirectory, appImage.CreateFileName("jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<AppImage> loadAllImagesFromStorage() {
        ArrayList<AppImage> appImageArrayList = new ArrayList<>();
        File dir = new File(appDirectory + myImagesDirectory);
        File[] filelist = dir.listFiles();

        if (filelist == null) return new ArrayList<>();

        for (File file : filelist)
        {
            String fileName = file.getName().split("\\.")[0];
            AppImage appImage = new AppImage(UUID.fromString(fileName), BitmapFactory.decodeFile(file.getPath()), new Date(file.lastModified()), context);
            appImageArrayList.add(appImage);
        }
        return appImageArrayList;
    }

    public AppImage loadImagesFromStorage(String imageName){
        File file = new File(appDirectory + myImagesDirectory + imageName + ".jpg");
        if (!file.exists()) return null;

        String fileName = file.getName().split("\\.")[0];

        return new AppImage(UUID.fromString(fileName), BitmapFactory.decodeFile(file.getPath()), new Date(file.lastModified()), context);
    }
}
