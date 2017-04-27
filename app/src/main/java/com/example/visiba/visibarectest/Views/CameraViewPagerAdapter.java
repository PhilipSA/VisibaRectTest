package com.example.visiba.visibarectest.Views;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.papersoccer.visibarectest.R;

/**
 * Created by Admin on 2017-04-27.
 */

public class CameraViewPagerAdapter extends PagerAdapter {
    Context mContext;
    Activity activity;

    CameraViewPagerAdapter(Context context, Activity activity) {
        this.mContext = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return sliderImagesId.length;
    }

    private int[] sliderImagesId = new int[]{R.layout.fragment_camera_roll, R.layout.activity_camera};

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((VideoView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        View pages = new View(mContext);

        String videoName = "tutorial1";

        int id = mContext.getResources().getIdentifier(videoName, "raw", activity.getPackageName());

        String uri = "android.resource://" + mContext.getPackageName() + "/" + sliderImagesId[i];


        ((ViewPager) container).addView(pages, 0);
        return pages;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }
}
