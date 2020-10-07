package com.example.kandyloginwithretrofit;

import android.app.Application;

import com.rbbn.cpaas.mobile.utilities.Globals;

public class KandyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Globals.setApplicationContext(this);
    }
}
