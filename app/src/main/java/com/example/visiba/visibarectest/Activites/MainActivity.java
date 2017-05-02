package com.example.visiba.visibarectest.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.Activites.Abstractions.BaseActivity;
import com.example.visiba.visibarectest.Adapters.WallPostAdapter;
import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.Enums.ImageRequestCodeEnum;
import com.example.visiba.visibarectest.Repositories.AppImageRepository;
import com.example.visiba.visibarectest.WallPost;
import com.example.visiba.visibarectest.Repositories.WallPostRepository;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    EditText newPostInput;
    RelativeLayout newPostButtonsLayout;
    ListView wallPostsListView;

    WallPostRepository wallPostRepository;
    AppImageRepository appImageRepository;

    ImageButton leftImageButton;
    ImageButton rightImageButton;

    AppImage leftImage;
    AppImage rightImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newPostInput = (EditText)findViewById(R.id.newPostInput);
        wallPostsListView = (ListView)findViewById(R.id.wallPostsListView);
        wallPostsListView.addHeaderView(new View(this));

        leftImageButton = (ImageButton)findViewById(R.id.leftImageButton);
        rightImageButton = (ImageButton)findViewById(R.id.rightImageButton);

        newPostButtonsLayout = (RelativeLayout)findViewById(R.id.newPostButtonsLayout);

        appImageRepository = new AppImageRepository(this);
        wallPostRepository = new WallPostRepository(this, appImageRepository);

        populateWallPostsListView();

        newPostInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0) newPostButtonsLayout.setVisibility(View.VISIBLE);
                else {
                    newPostButtonsLayout.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void onSendWallPostClick(View view)
    {
        WallPost wallPost = new WallPost(newPostInput.getText().toString(), leftImage, rightImage);
        wallPostRepository.SaveWallPostData(new WallPost.SerializableWallPost(wallPost));
        recreate();
    }

    public void OpenCameraActivity(int imageButtonRequestCode)
    {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra("IMAGE_BUTTON_REQUEST_CODE", imageButtonRequestCode);
        startActivityForResult(intent, imageButtonRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImageRequestCodeEnum.LEFT) {
            if (resultCode == RESULT_OK) {
                String imageId = data.getStringExtra("IMAGE_ID");
                leftImage = setImageButtonPreviewImage(leftImageButton, imageId);;
            }
        }
        else if (requestCode == ImageRequestCodeEnum.RIGHT) {
            if (resultCode == RESULT_OK) {
                String imageId = data.getStringExtra("IMAGE_ID");
                rightImage = setImageButtonPreviewImage(rightImageButton, imageId);
            }
        }
    }

    private AppImage setImageButtonPreviewImage(ImageButton imageButton, String imageId)
    {
        AppImage appImage = appImageRepository.loadImagesFromStorage(imageId);
        imageButton.setImageDrawable(appImage.drawableImage);
        imageButton.setImageTintList(null); // White Tint
        return appImage;
    }

    public void onLeftImageButtonClick(View v)
    {
        try {
            OpenCameraActivity(ImageRequestCodeEnum.LEFT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onRightImageButtonClick(View v)
    {
        try {
            OpenCameraActivity(ImageRequestCodeEnum.RIGHT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populateWallPostsListView()
    {
        ArrayList<WallPost> wallPosts = new ArrayList<>();

        for (WallPost.SerializableWallPost serializableWallPost : wallPostRepository.LoadAllWallPostsFromStorage())
        {
            WallPost wallPost = wallPostRepository.ConvertSerializableToWallPost(serializableWallPost);
            wallPosts.add(wallPost);
        }

        WallPostAdapter wallPostAdapter = new WallPostAdapter(getBaseContext(), wallPosts);
        wallPostsListView.setAdapter(wallPostAdapter);
    }
}
