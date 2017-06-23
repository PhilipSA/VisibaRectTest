package com.example.visiba.visibarectest.Adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.visiba.visibarectest.AppImage

class ImageAdapter(private val mContext: Context, private val images: MutableList<AppImage>) : BaseAdapter() {
    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Any {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(mContext)
        } else {
            imageView = convertView as ImageView
        }

        imageView.setImageDrawable(images[position].getDrawable(mContext))
        imageView.adjustViewBounds = true

        return imageView
    }
}

