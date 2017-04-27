package com.example.visiba.visibarectest.Views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.visiba.visibarectest.WallPost;

/**
 * Created by Admin on 2017-04-27.
 */

public class WallPostView extends View
{
    public ImageView leftImage;
    public TextView textContent;
    public ImageView rightImage;

    public WallPostView(Context context, WallPost wallPost)
    {
        super(context);

        leftImage = new ImageView(context);
        leftImage.setImageDrawable(wallPost.leftImage.image);

        textContent = new TextView(context);
        textContent.setText(wallPost.textContent);

        rightImage = new ImageView(context);
        rightImage.setImageDrawable(wallPost.rightImage.image);
    }
}
