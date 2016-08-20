package com.tinker.chat;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * Created by tiankui on 16/8/20.
 * 在App中进行初始化;
 * 调用RongIM.init(context);
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        RongIM.init(this);
    }
}
