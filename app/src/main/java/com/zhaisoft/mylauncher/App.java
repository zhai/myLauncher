package com.zhaisoft.mylauncher;

import android.app.Application;

import com.zhaisoft.mylauncher.preferences.PreferenceImpl;
import com.zhaisoft.mylauncher.preferences.PreferenceProvider;

public class App extends Application {

     @Override
    public void onCreate() {
        super.onCreate();

        PreferenceProvider.INSTANCE.init(new PreferenceImpl(this));
    }
}
