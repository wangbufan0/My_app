package com.example.mytestapp.app;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {


    private static Context context = null;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}