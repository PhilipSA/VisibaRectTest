package com.example.visiba.visibarectest.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout

import com.example.papersoccer.visibarectest.R
import com.example.visiba.visibarectest.Activities.Abstractions.BaseActivity
import com.example.visiba.visibarectest.AppImage
import com.example.visiba.visibarectest.Fragments.Abstractions.IResultReturning
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class CameraActivity : BaseActivity(), IResultReturning<AppImage> {
    private lateinit var mSlindingUpPanelLayout: SlidingUpPanelLayout
    lateinit var cameraRollSliderHeaderArrow: ImageView
    lateinit var cameraRollSliderHeaderLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        mSlindingUpPanelLayout = findViewById<SlidingUpPanelLayout>(R.id.sliding_layout)
        cameraRollSliderHeaderArrow = findViewById<ImageView>(R.id.cameraRollSliderHeaderArrow)
        cameraRollSliderHeaderLayout = findViewById<RelativeLayout>(R.id.cameraRollSliderHeaderLayout)

        mSlindingUpPanelLayout.addPanelSlideListener(object : SlidingUpPanelLayout.SimplePanelSlideListener() {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {

                super.onPanelSlide(panel, slideOffset)
            }

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED && previousState == SlidingUpPanelLayout.PanelState.DRAGGING || newState == SlidingUpPanelLayout.PanelState.DRAGGING && previousState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    (panel as SlidingUpPanelLayout).isOverlayed = false
                } else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED && previousState == SlidingUpPanelLayout.PanelState.DRAGGING || newState == SlidingUpPanelLayout.PanelState.DRAGGING && previousState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    (panel as SlidingUpPanelLayout).isOverlayed = true
                }
                updateCameraRollSliderHeaderLayout(newState!!)

                super.onPanelStateChanged(panel, previousState, newState)
            }
        })
    }

    private fun updateCameraRollSliderHeaderLayout(panelState: SlidingUpPanelLayout.PanelState) {
        if (panelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            cameraRollSliderHeaderLayout.setBackgroundResource(R.color.transparentColorButton)
            cameraRollSliderHeaderArrow.scaleY = 1f
        } else if (panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            cameraRollSliderHeaderLayout.setBackgroundResource(R.color.colorButton)
            cameraRollSliderHeaderArrow.scaleY = -1f
        }
    }

    override fun onBackPressed() {
        if (mSlindingUpPanelLayout.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            mSlindingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        } else {
            super.onBackPressed()
        }
    }

    override fun finish(result: AppImage?) {
        result?.let {
            val conData = Bundle()
            conData.putString("IMAGE_ID", it.imageId.toString())
            val intent = Intent()
            intent.putExtras(conData)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }
}
