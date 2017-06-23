package com.example.visiba.visibarectest.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.example.papersoccer.visibarectest.R
import com.example.visiba.visibarectest.AppImage
import com.example.visiba.visibarectest.WallPost

import java.util.ArrayList

class WallPostAdapter(private val mContext: Context, private val wallPosts: MutableList<WallPost>) : BaseAdapter() {

    override fun getCount(): Int {
        return wallPosts.size
    }

    override fun getItem(position: Int): Any {
        return wallPosts[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    private fun setImageViewValues(imageView: ImageView, appImage: AppImage?) {
        if (appImage != null) {
            imageView.setImageDrawable(appImage.getDrawable(mContext))
            imageView.clipToOutline = true
        } else {
            setViewWeightParam(imageView, 0f)
        }
    }

    private fun setTextWeight(textView: TextView, position: Int) {
        if (wallPosts[position].leftImage == null || wallPosts[position].rightImage == null) {
            val params = textView.layoutParams as LinearLayout.LayoutParams
            setViewWeightParam(textView, params.weight + 1)
        }
    }

    private fun setViewWeightParam(view: View, weight: Float) {
        val params = view.layoutParams as LinearLayout.LayoutParams
        params.weight = weight
        view.layoutParams = params
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val view: View
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.wallpost_layout, parent, false)

        val leftImageView = view.findViewById(R.id.leftImageView) as ImageView
        val textContentView = view.findViewById(R.id.textContentView) as TextView
        val rightImageView = view.findViewById(R.id.rightImageView) as ImageView

        setImageViewValues(leftImageView, wallPosts[position].leftImage)
        textContentView.text = wallPosts[position].textContent
        setImageViewValues(rightImageView, wallPosts[position].rightImage)

        setTextWeight(textContentView, position)

        return view
    }
}
