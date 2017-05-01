package com.example.visiba.visibarectest.Repositories.Abstractions;

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

public abstract class BaseRepository
{
    protected String appDirectory;
    protected Context context;

    public BaseRepository(Context context)
    {
        this.context = context;
        appDirectory = String.format("%s", context.getFilesDir().getPath());
    }

    public void save(byte[] bytes, String directory, String fileName) throws IOException {
        File file = new File(String.format("%s%s%s%s", appDirectory, directory, File.separator, fileName));
        CreateDirectoryIfNotExists(fileName);
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

    public void saveObject(Object object, String directory, String fileName) {
        File dir = new File(appDirectory+directory+fileName);
        FileOutputStream fileOutputStream = null;
        try {
            CreateDirectoryIfNotExists(directory);
            fileOutputStream = new FileOutputStream(dir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
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
}
