package com.example.visiba.visibarectest.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import com.example.papersoccer.visibarectest.R
import com.example.visiba.visibarectest.Activities.CameraActivity
import com.example.visiba.visibarectest.Adapters.ImageAdapter
import com.example.visiba.visibarectest.AppImage
import com.example.visiba.visibarectest.Fragments.Abstractions.IResultReturning
import com.example.visiba.visibarectest.Repositories.AppImageRepository
import kotlinx.android.synthetic.main.fragment_camera_roll.*

class CameraGalleryRollFragment : Fragment(), IResultReturning<AppImage> {

    lateinit private var appImageRepository: AppImageRepository
    private val cameraRollGalleryGrid get() = camera_cameraRollGalleryGrid

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_camera_roll, container, false)
        appImageRepository = AppImageRepository()
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraRollGalleryGrid.onItemClickListener = onGalleryItemClick
        displayAllImages()
    }

    private val onGalleryItemClick = AdapterView.OnItemClickListener { parent, v, position, id -> finish(parent.getItemAtPosition(position) as AppImage) }

    private fun displayAllImages() {
        val appImages = appImageRepository.loadAllItems()
        appImages.sortBy { it.takenDate }

        val imageAdapter = ImageAdapter(context, appImages)

        cameraRollGalleryGrid.adapter = imageAdapter
    }

    override fun finish(result: AppImage?) {
        (activity as CameraActivity).finish(result)
    }
}
