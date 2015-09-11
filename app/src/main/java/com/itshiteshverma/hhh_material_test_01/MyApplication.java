package com.itshiteshverma.hhh_material_test_01;

import android.app.Application;
import android.content.Context;

/**
 * Created by Nilesh Verma on 9/8/2015.
 */

//Change in Manifest line no 11 (RED DOT)
public class MyApplication extends Application {

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }
    public static MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
