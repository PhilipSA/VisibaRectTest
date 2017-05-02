package com.example.visiba.visibarectest.Activites.Abstractions;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.papersoccer.visibarectest.R;
import com.example.visiba.visibarectest.LanguageHandler;

public abstract class BaseActivity extends AppCompatActivity {
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
