package com.example.visiba.visibarectest.Activities.Abstractions;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.LanguageHandler;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_swedish) {
            LanguageHandler.setLocale("sv", getBaseContext());
            recreate();
        }
        else if (id == R.id.action_english)
        {
            LanguageHandler.setLocale("en", getBaseContext());
            recreate();
        }

        return super.onOptionsItemSelected(item);
    }
}
