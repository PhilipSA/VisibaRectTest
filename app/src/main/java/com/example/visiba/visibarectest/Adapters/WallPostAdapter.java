package com.example.visiba.visibarectest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.WallPost;

import java.util.ArrayList;

/**
 * Created by Admin on 2017-04-28.
 */

public class WallPostAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<WallPost> wallPosts;

    public WallPostAdapter(Context context, ArrayList<WallPost> wallPosts) {
        mContext = context;
        this.wallPosts = wallPosts;
    }

    public int getCount() {
        return wallPosts.size();
    }

    public Object getItem(int position) {
        return wallPosts.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.wallpost_layout, parent, false);

        ImageView leftImageView = (ImageView)view.findViewById(R.id.leftImageView);
        TextView textContentView = (TextView) view.findViewById(R.id.textContentView);
        ImageView rightImageView = (ImageView)view.findViewById(R.id.rightImageView);

        if (wallPosts.get(position).leftImage != null) leftImageView.setImageDrawable(wallPosts.get(position).leftImage.drawableImage);

        textContentView.setText(wallPosts.get(position).textContent);

        if (wallPosts.get(position).rightImage != null)rightImageView.setImageDrawable(wallPosts.get(position).rightImage.drawableImage);
        return view;
    }
}
