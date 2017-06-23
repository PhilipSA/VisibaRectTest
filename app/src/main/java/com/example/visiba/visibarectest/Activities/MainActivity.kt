package com.example.visiba.visibarectest.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageButton
import com.example.papersoccer.visibarectest.R
import com.example.visiba.visibarectest.Activities.Abstractions.BaseActivity
import com.example.visiba.visibarectest.Adapters.WallPostAdapter
import com.example.visiba.visibarectest.AppImage
import com.example.visiba.visibarectest.Enums.ImageRequestCodeEnum
import com.example.visiba.visibarectest.Repositories.AppImageRepository
import com.example.visiba.visibarectest.WallPost
import com.example.visiba.visibarectest.Repositories.WallPostRepository
import io.paperdb.Paper
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity() {

    val newPostButtonsLayout get() = main_newPostButtonsLayout
    val wallPostsListView get() = main_wallPostsListView

    val newPostInput get() = main_newPostInput

    lateinit var wallPostRepository: WallPostRepository
    lateinit var appImageRepository: AppImageRepository

    val leftImageButton get() = main_leftImageButton
    val rightImageButton get() = main_rightImageButton

    var leftImage: AppImage? = null
    var rightImage: AppImage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        Paper.init(this)

        appImageRepository = AppImageRepository()
        wallPostRepository = WallPostRepository()

        populateWallPostsListView()

        newPostInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length != 0)
                    newPostButtonsLayout.visibility = View.VISIBLE
                else {
                    newPostButtonsLayout.visibility = View.INVISIBLE
                }
            }
        })
    }

    fun onSendWallPostClick(view: View) {
        val wallPost = WallPost(newPostInput.text.toString(), leftImage!!, rightImage!!)
        wallPostRepository.SaveWallPostData(wallPost)
        init()
    }

    fun OpenCameraActivity(imageButtonRequestCode: Int) {
        val intent = Intent(this, CameraActivity::class.java)
        intent.putExtra("IMAGE_BUTTON_REQUEST_CODE", imageButtonRequestCode)
        startActivityForResult(intent, imageButtonRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == ImageRequestCodeEnum.LEFT) {
            if (resultCode == Activity.RESULT_OK) {
                val imageId = data.getStringExtra("IMAGE_ID")
                leftImage = setImageButtonPreviewImage(leftImageButton, imageId)!!
            }
        } else if (requestCode == ImageRequestCodeEnum.RIGHT) {
            if (resultCode == Activity.RESULT_OK) {
                val imageId = data.getStringExtra("IMAGE_ID")
                rightImage = setImageButtonPreviewImage(rightImageButton, imageId)!!
            }
        }
    }

    private fun setImageButtonPreviewImage(imageButton: ImageButton, imageId: String): AppImage? {
        val appImage = appImageRepository.loadItem(imageId)
        imageButton.setImageDrawable(appImage?.getDrawable(this))
        imageButton.imageTintList = null // White Tint
        return appImage
    }

    fun onLeftImageButtonClick(v: View) {
        try {
            OpenCameraActivity(ImageRequestCodeEnum.LEFT)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun onRightImageButtonClick(v: View) {
        try {
            OpenCameraActivity(ImageRequestCodeEnum.RIGHT)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun populateWallPostsListView() {
        val wallPosts = wallPostRepository.loadAllItems()
        val wallPostAdapter = WallPostAdapter(baseContext, wallPosts!!.asReversed())
        wallPostsListView.adapter = wallPostAdapter
    }
}
