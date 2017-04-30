package com.example.visiba.visibarectest.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.visiba.visibarectest.Fragments.CameraGalleryRollFragment;
import com.example.visiba.visibarectest.Fragments.CameraPreviewFragment;

public class CameraFragmentAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public CameraFragmentAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return CameraPreviewFragment.newInstance();
                case 1:
                    return CameraGalleryRollFragment.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
}
