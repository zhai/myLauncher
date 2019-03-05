package com.zhaisoft.app.mylauncher;

import android.app.Application;

import com.zhaisoft.app.mylauncher.preferences.PreferenceImpl;
import com.zhaisoft.app.mylauncher.preferences.PreferenceProvider;

public class App extends Application {

     @Override
    public void onCreate() {
        super.onCreate();

        PreferenceProvider.INSTANCE.init(new PreferenceImpl(this));
    }
}
