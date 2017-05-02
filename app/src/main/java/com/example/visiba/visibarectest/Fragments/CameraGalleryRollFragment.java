package com.example.visiba.visibarectest.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.Activites.CameraActivity;
import com.example.visiba.visibarectest.Adapters.ImageAdapter;
import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.Fragments.Abstractions.IResultReturning;
import com.example.visiba.visibarectest.Repositories.Abstractions.BaseRepository;
import com.example.visiba.visibarectest.Repositories.AppImageRepository;

import java.util.ArrayList;
import java.util.Collections;

public class CameraGalleryRollFragment extends Fragment implements IResultReturning<AppImage> {

    private AppImageRepository appImageRepository;
    private GridView cameraRollGalleryGrid;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_camera_roll, container, false);

        appImageRepository = new AppImageRepository(getContext());
        cameraRollGalleryGrid = (GridView)view.findViewById(R.id.cameraRollGalleryGrid);
        displayAllImages();

        cameraRollGalleryGrid.setOnItemClickListener(onGalleryItemClick);

        return view;
    }

    private AdapterView.OnItemClickListener onGalleryItemClick = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView<?> parent, View v, int position, long id)
        {
            Finish((AppImage)parent.getItemAtPosition(position));
        }
    };

    private void displayAllImages()
    {
        ArrayList<AppImage> appImages = appImageRepository.loadAllImagesFromStorage();
        Collections.sort(appImages);

        ImageAdapter imageAdapter = new ImageAdapter(getContext(), appImages);

        cameraRollGalleryGrid.setAdapter(imageAdapter);
    }

    @Override
    public void Finish(AppImage result) {
        ((CameraActivity)getActivity()).Finish(result);
    }
}
