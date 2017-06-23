package com.example.visiba.visibarectest

import android.content.Context
import android.content.res.Configuration

import java.util.Locale

object LanguageHandler {
    fun setLocale(lang: String, context: Context) {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, null)
    }
}
