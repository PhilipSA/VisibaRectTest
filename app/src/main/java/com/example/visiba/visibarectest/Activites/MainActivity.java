package com.example.visiba.visibarectest.Activites;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.Adapters.WallPostAdapter;
import com.example.visiba.visibarectest.AppImage;
import com.example.visiba.visibarectest.Enums.ImageRequestCodeEnum;
import com.example.visiba.visibarectest.Handlers.StorageHandler;
import com.example.visiba.visibarectest.WallPost;
import com.example.visiba.visibarectest.Handlers.WallPostsHandler;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText newPostInput;
    RelativeLayout newPostButtonsLayout;
    WallPostsHandler wallPostsHandler;
    ListView wallPostsListView;

    StorageHandler storageHandler;

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

        leftImageButton = (ImageButton)findViewById(R.id.leftImageButton);
        rightImageButton = (ImageButton)findViewById(R.id.rightImageButton);

        newPostButtonsLayout = (RelativeLayout)findViewById(R.id.newPostButtonsLayout);

        storageHandler = new StorageHandler(this);
        wallPostsHandler = new WallPostsHandler(storageHandler);

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
        storageHandler.SaveWallPostData(new WallPost.SerializableWallPost(wallPost));
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
        // Check which request we're responding to
        if (requestCode == ImageRequestCodeEnum.LEFT) {
            // Make sure the request was successful
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
        AppImage appImage = storageHandler.loadImagesFromStorage(imageId);
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

        for (WallPost.SerializableWallPost serializableWallPost : storageHandler.LoadAllWallPostsFromStorage())
        {
            WallPost wallPost = wallPostsHandler.ConvertSerializableToWallPost(serializableWallPost);
            wallPosts.add(wallPost);
        }

        WallPostAdapter wallPostAdapter = new WallPostAdapter(getBaseContext(), wallPosts);
        wallPostsListView.setAdapter(wallPostAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_swedish) {
            setLocale("sv");
        }
        else if (id == R.id.action_english)
        {
            setLocale("en");
        }

        return super.onOptionsItemSelected(item);
    }

    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
