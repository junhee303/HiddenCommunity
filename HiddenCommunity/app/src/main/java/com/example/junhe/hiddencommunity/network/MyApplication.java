package com.example.junhe.hiddencommunity.network;

import android.app.Application;
import android.content.Context;

/**
 * Created by HongjunLim on 11/27/2016.
 */

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
