package com.example.visiba.visibarectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

public class AppImage implements Comparable<AppImage>
{
    public UUID imageId;
    public Image image;
    public Drawable drawableImage;
    public Date takenDate;

    public AppImage(UUID imageId, Bitmap bitmapImage, Date takenDate, Context context) {
        this.imageId = imageId;
        this.takenDate = takenDate;
        drawableImage = new BitmapDrawable(context.getResources(), bitmapImage);
    }

    public AppImage(UUID imageId, Image image) {
        this.imageId = imageId;
        this.image = image;
    }

    public String CreateFileName(String fileEnding)
    {
        return String.format("%s.%s", imageId, fileEnding);
    }

    @Override
    public int compareTo(@NonNull AppImage appImage) {
        if (this.takenDate.before(appImage.takenDate)) {
            return -1;
        }
        else if(this.takenDate.after(appImage.takenDate)){
            return 1;
        }

        return 0;
    }
}
