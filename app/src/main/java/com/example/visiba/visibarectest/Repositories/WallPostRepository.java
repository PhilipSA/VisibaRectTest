package com.example.visiba.visibarectest.Repositories;

import android.content.Context;

import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.Repositories.Abstractions.BaseRepository;
import com.example.visiba.visibarectest.WallPost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class WallPostRepository extends BaseRepository
{
    private final String myWallPostsDirectory = String.format("%s%s%s", File.separator, "MyWallPosts", File.separator);
    private AppImageRepository appImageRepository;

    public WallPostRepository(Context context, AppImageRepository appImageRepository) {
        super(context);
        this.appImageRepository = appImageRepository;
    }

    public WallPost ConvertSerializableToWallPost(WallPost.SerializableWallPost serializableWallPost)
    {
        return new WallPost(serializableWallPost.id,
                serializableWallPost.textContent,
                appImageRepository.loadImagesFromStorage(String.valueOf(serializableWallPost.leftImageId)),
                appImageRepository.loadImagesFromStorage(String.valueOf(serializableWallPost.rightImageId)),
                serializableWallPost.postedDate);
    }

    public void SaveWallPostData(WallPost.SerializableWallPost serializeableWallPost)
    {
        saveObject(serializeableWallPost, myWallPostsDirectory, String.valueOf(serializeableWallPost.id));
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
}
