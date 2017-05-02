package com.example.visiba.visibarectest.Adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.WallPost;

import java.util.ArrayList;

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

    private void setImageViewValues(ImageView imageView, AppImage appImage)
    {
        if (appImage != null) {
            imageView.setImageDrawable(appImage.drawableImage);
            imageView.setClipToOutline(true);
        }
        else {
            setViewWeightParam(imageView, 0);
        }
    }

    private void setTextWeight(TextView textView, int position)
    {
        if (wallPosts.get(position).leftImage == null || wallPosts.get(position).rightImage == null)
        {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)textView.getLayoutParams();
            setViewWeightParam(textView, params.weight + 1);
        }
    }

    private void setViewWeightParam(View view, float weight)
    {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();
        params.weight = weight;
        view.setLayoutParams(params);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.wallpost_layout, parent, false);

        ImageView leftImageView = (ImageView)view.findViewById(R.id.leftImageView);
        TextView textContentView = (TextView) view.findViewById(R.id.textContentView);
        ImageView rightImageView = (ImageView)view.findViewById(R.id.rightImageView);

        setImageViewValues(leftImageView, wallPosts.get(position).leftImage);
        textContentView.setText(wallPosts.get(position).textContent);
        setImageViewValues(rightImageView, wallPosts.get(position).rightImage);

        setTextWeight(textContentView, position);

        return view;
    }
}
