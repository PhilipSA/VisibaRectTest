package com.example.visiba.visibarectest.Handlers;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.WallPost;

import junit.framework.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class StorageHandler
{
    private String appDirectory;
    private final String myWallPostsDirectory = File.separator+"MyWallPosts";
    private final String myImagesDirectory = File.separator+"MyImages";
    Context context;

    public StorageHandler(Context context)
    {
        this.context = context;
        appDirectory = String.format("%s", context.getFilesDir().getPath());
    }

    public void SaveWallPostData(WallPost wallPost)
    {
    }

    public void save(byte[] bytes, String directory, String fileName) throws IOException {
        File file = new File(String.format("%s%s%s%s", appDirectory, directory, File.separator ,fileName));
        file.createNewFile();
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
            output.write(bytes);
        } finally {
            if (null != output) {
                output.close();
            }
        }
    }

    private void CreateDirectoryIfNotExsits(String path)
    {
        File filePath = new File(appDirectory+path);
        if(!filePath.exists())
        {
            filePath.mkdir();
        }
    }

    public void saveAppImage(AppImage appImage){
        ByteBuffer buffer = appImage.image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        try {
            CreateDirectoryIfNotExsits(myImagesDirectory);
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
}
