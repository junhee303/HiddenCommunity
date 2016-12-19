package com.example.junhe.hiddencommunity.network;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {
    private final String TAG = "MyApplication";
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
