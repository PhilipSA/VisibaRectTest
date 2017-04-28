package com.example.visiba.visibarectest.Activites;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.Fragments.Abstractions.IResultReturning;
import com.example.visiba.visibarectest.Fragments.CameraGalleryRollFragment;
import com.example.visiba.visibarectest.Fragments.CameraPreviewFragment;

public class CameraActivity extends AppCompatActivity implements IResultReturning<AppImage> {
    ViewPager mViewPager;
    LinearLayout cameraRollCurtainLayout;
    FragmentPagerAdapter adapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        adapterView = new MyPagerAdapter(getSupportFragmentManager());;
        mViewPager.setAdapter(adapterView);

        cameraRollCurtainLayout = (LinearLayout) findViewById(R.id.cameraRollCurtainLayout);
    }

    public void onCameraRollCurtainLayoutClick(View v) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() ^ 1, true);
    }

    public void Finish(AppImage appImage)
    {
        Bundle conData = new Bundle();
        conData.putString("IMAGE_ID", String.valueOf(appImage.imageId));
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return CameraPreviewFragment.newInstance();
                case 1:
                    return CameraGalleryRollFragment.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}
