package com.example.visiba.visibarectest.Handlers;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.WallPost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageHandler
{
    private final String myWallPostsFilename = "myWallPosts";
    private final String myImagesFilename = "myImages";
    Context context;

    public StorageHandler(Context context)
    {
        this.context = context;
    }

    public void SaveWallPostData(WallPost wallPost)
    {
        int homeScore;
        byte[] homeScoreBytes = new byte[4];

        homeScoreBytes[0] = Byte.parseByte(wallPost.leftImage.imageId.toString());
        homeScoreBytes[1] = Byte.parseByte(wallPost.textContent);
        homeScoreBytes[2] = Byte.parseByte(wallPost.rightImage.imageId.toString());
        homeScoreBytes[3] = Byte.parseByte(wallPost.postedDate.toString());

        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(myWallPostsFilename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            outputStream.write(homeScoreBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String saveAppImage(AppImage appImage){
        ContextWrapper cw = new ContextWrapper(context);

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, String.valueOf(appImage.imageId));

        return "";
        /*Bitmap bitmapImage = BitmapFactory.(appImage.image);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();*/
    }

    private Bitmap loadImageFromStorage(String path)
    {
        try {
            File f=new File(path, "profile.jpg");
            return BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
