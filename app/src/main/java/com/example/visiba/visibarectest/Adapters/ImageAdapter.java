package com.example.visiba.visibarectest.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.visiba.visibarectest.AppImage;

import java.util.ArrayList;

/**
 * Created by Admin on 2017-04-28.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<AppImage> images;

    public ImageAdapter(Context c, ArrayList<AppImage> images) {
        mContext = c;
        this.images = images;
    }

    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return images.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageDrawable(images.get(position).drawableImage);
        return imageView;
    }
}

