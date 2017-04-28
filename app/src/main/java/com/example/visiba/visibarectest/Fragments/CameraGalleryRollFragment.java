package com.example.visiba.visibarectest.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.os.IResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.Activites.CameraActivity;
import com.example.visiba.visibarectest.Adapters.ImageAdapter;
import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.Fragments.Abstractions.IResultReturning;
import com.example.visiba.visibarectest.Handlers.StorageHandler;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Admin on 2017-04-27.
 */

public class CameraGalleryRollFragment extends Fragment implements IResultReturning<AppImage> {
    public static Fragment newInstance() {
        CameraGalleryRollFragment fragmentFirst = new CameraGalleryRollFragment();;
        return fragmentFirst;
    }

    private StorageHandler storageHandler;
    private GridView cameraRollGalleryGrid;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_camera_roll, container, false);
        // Inflate the layout for this fragment
        storageHandler = new StorageHandler(getContext());
        cameraRollGalleryGrid = (GridView)view.findViewById(R.id.cameraRollGalleryGrid);
        displayAllImages();

        return view;
    }

    private void displayAllImages()
    {
        ArrayList<AppImage> appImages = storageHandler.loadAllImagesFromStorage();
        Collections.sort(appImages);

        ImageAdapter imageAdapter = new ImageAdapter(getContext(), appImages);

        cameraRollGalleryGrid.setAdapter(imageAdapter);
    }

    @Override
    public void Finish(AppImage result) {
        ((CameraActivity)getActivity()).Finish(result);
    }
}
