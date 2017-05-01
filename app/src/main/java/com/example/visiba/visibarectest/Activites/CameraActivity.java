package com.example.visiba.visibarectest.Activites;

import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.Fragments.Abstractions.IResultReturning;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class CameraActivity extends AppCompatActivity implements IResultReturning<AppImage> {

    SlidingUpPanelLayout mSlindingUpPanelLayout;
    ImageView cameraRollSliderHeaderArrow;
    RelativeLayout cameraRollSliderHeaderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mSlindingUpPanelLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
        cameraRollSliderHeaderArrow = (ImageView) findViewById(R.id.cameraRollSliderHeaderArrow);
        cameraRollSliderHeaderLayout = (RelativeLayout) findViewById(R.id.cameraRollSliderHeaderLayout);

        mSlindingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.SimplePanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

                super.onPanelSlide(panel, slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                super.onPanelStateChanged(panel, previousState, newState);

                if ((newState == SlidingUpPanelLayout.PanelState.EXPANDED && previousState == SlidingUpPanelLayout.PanelState.DRAGGING) ||
                        (newState == SlidingUpPanelLayout.PanelState.DRAGGING && previousState == SlidingUpPanelLayout.PanelState.COLLAPSED))
                {
                    ((SlidingUpPanelLayout)panel).setOverlayed(false);
                }
                else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED && previousState == SlidingUpPanelLayout.PanelState.DRAGGING)
                {
                    ((SlidingUpPanelLayout)panel).setOverlayed(true);
                }
                updateCameraRollSliderHeaderLayout(newState);
            }
        });
    }

    private void updateCameraRollSliderHeaderLayout(SlidingUpPanelLayout.PanelState panelState)
    {
        if (panelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            cameraRollSliderHeaderLayout.setBackgroundResource(R.color.transparentColorButton);
            cameraRollSliderHeaderArrow.setScaleY(1f);
        }
        else if (panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            cameraRollSliderHeaderLayout.setBackgroundResource(R.color.colorButton);
            cameraRollSliderHeaderArrow.setScaleY(-1f);
        }
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
