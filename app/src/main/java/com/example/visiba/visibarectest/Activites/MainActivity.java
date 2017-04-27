package com.example.visiba.visibarectest.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.Views.WallPostView;
import com.example.visiba.visibarectest.WallPost;
import com.example.visiba.visibarectest.Handlers.WallPostsHandler;

public class MainActivity extends AppCompatActivity {

    EditText newPostInput;
    LinearLayout newPostButtonsLayout;
    WallPostsHandler wallPostsHandler;
    ListView wallPostsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wallPostsHandler = new WallPostsHandler();

        newPostInput = (EditText)findViewById(R.id.newPostInput);
        wallPostsListView = (ListView)findViewById(R.id.listView);

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
                if(s.length() != 0) newPostButtonsLayout.setVisibility(View.INVISIBLE);
                else {
                    newPostButtonsLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void populateWallPostsListView()
    {
        for (WallPost wallpost : wallPostsHandler.GetAllWallPosts())
        {
            wallPostsListView.addView(new WallPostView(this, wallpost));
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
