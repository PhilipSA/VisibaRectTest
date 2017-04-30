package com.example.visiba.visibarectest.Handlers;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.WallPost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class StorageHandler
{
    private String appDirectory;
    private final String myWallPostsDirectory = String.format("%s%s%s", File.separator, "MyWallPosts", File.separator);
    private final String myImagesDirectory = String.format("%s%s%s", File.separator, "MyImages", File.separator);
    Context context;

    public StorageHandler(Context context)
    {
        this.context = context;
        appDirectory = String.format("%s", context.getFilesDir().getPath());
    }

    public void SaveWallPostData(WallPost.SerializableWallPost serializeableWallPost)
    {
        File dir = new File(appDirectory+myWallPostsDirectory+serializeableWallPost.id);
        FileOutputStream fileOutputStream = null;
        try {
            CreateDirectoryIfNotExists(myWallPostsDirectory);
            fileOutputStream = new FileOutputStream(dir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(serializeableWallPost);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<WallPost.SerializableWallPost> LoadAllWallPostsFromStorage()
    {
        ArrayList<WallPost.SerializableWallPost> wallPosts = new ArrayList<>();
        File dir = new File(appDirectory + myWallPostsDirectory);
        File[] filelist = dir.listFiles();

        if (filelist == null) return new ArrayList<>();

        for (File file : filelist) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                ObjectInputStream is = new ObjectInputStream(fis);
                WallPost.SerializableWallPost simpleClass = (WallPost.SerializableWallPost) is.readObject();
                is.close();
                fis.close();
                wallPosts.add(simpleClass);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wallPosts;
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

    private void CreateDirectoryIfNotExists(String path)
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
            CreateDirectoryIfNotExists(myImagesDirectory);
            save(bytes, myImagesDirectory, appImage.CreateFileName("jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AppImage loadImagesFromStorage(String imageName){
        File file = new File(appDirectory + myImagesDirectory + imageName);
        if (!file.exists()) return null;

        String fileName = file.getName().split("\\.")[0];

        return new AppImage(UUID.fromString(fileName), BitmapFactory.decodeFile(file.getPath()), new Date(file.lastModified()), context);
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
