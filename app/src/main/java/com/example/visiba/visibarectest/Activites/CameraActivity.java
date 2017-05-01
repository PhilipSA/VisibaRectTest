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

    @Override
    public void onBackPressed() {
        if (mSlindingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)
        {
            mSlindingUpPanelLayout.setPanelState( SlidingUpPanelLayout.PanelState.COLLAPSED );
        }
        else {
            super.onBackPressed();
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
