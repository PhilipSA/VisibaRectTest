package com.example.visiba.visibarectest.Activites;

import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.Fragments.Abstractions.IResultReturning;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class CameraActivity extends AppCompatActivity implements IResultReturning<AppImage> {
    ViewPager mViewPager;
    SlidingUpPanelLayout mSlindingUpPanelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mSlindingUpPanelLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);

        mSlindingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.SimplePanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                super.onPanelSlide(panel, slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                super.onPanelStateChanged(panel, previousState, newState);
            }
        });
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
