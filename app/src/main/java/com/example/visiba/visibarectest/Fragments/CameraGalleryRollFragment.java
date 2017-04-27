package com.example.visiba.visibarectest.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.papersoccer.visibarectest.R;

/**
 * Created by Admin on 2017-04-27.
 */

public class CameraGalleryRollFragment extends Fragment {
    public static Fragment newInstance() {
        CameraGalleryRollFragment fragmentFirst = new CameraGalleryRollFragment();
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_roll, container, false);
    }

}
