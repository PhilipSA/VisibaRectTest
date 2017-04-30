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
import com.example.visiba.visibarectest.Adapters.CameraFragmentAdapter;
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
        adapterView = new CameraFragmentAdapter(getSupportFragmentManager());;
        mViewPager.setAdapter(adapterView);

        cameraRollCurtainLayout = (LinearLayout) findViewById(R.id.cameraRollCurtainLayout);
    }

    public void onCameraRollCurtainLayoutClick(View v) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() ^ 1, true);
    }

    @Override
    public void onBackPressed() {
        if(mViewPager.getCurrentItem() == 1) {
            mViewPager.setCurrentItem(0, true);
        } else {
            super.onBackPressed(); // This will pop the Activity from the stack.
        }
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
}
