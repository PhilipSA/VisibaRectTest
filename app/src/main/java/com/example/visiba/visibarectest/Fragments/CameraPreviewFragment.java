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

public class CameraPreviewFragment extends Fragment {
    public static Fragment newInstance() {
        CameraPreviewFragment fragmentFirst = new CameraPreviewFragment();
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_camera_preview, container, false);
        return rootView;
    }


}
