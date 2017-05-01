package com.example.visiba.visibarectest;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by Admin on 2017-05-01.
 */

public class LanguageHandler
{
    public static void setLocale(String lang, Context context) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        context.getResources().updateConfiguration(configuration, null);
    }
}
