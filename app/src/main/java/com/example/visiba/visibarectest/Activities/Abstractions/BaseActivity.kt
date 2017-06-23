package com.example.visiba.visibarectest.Activities.Abstractions

import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import com.example.papersoccer.visibarectest.R
import com.example.visiba.visibarectest.LanguageHandler

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_swedish) {
            LanguageHandler.setLocale("sv", baseContext)
            recreate()
        } else if (id == R.id.action_english) {
            LanguageHandler.setLocale("en", baseContext)
            recreate()
        }

        return super.onOptionsItemSelected(item)
    }
}
