package com.zhaisoft.app.mylauncher.gestures.dt2s

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent

import com.zhaisoft.app.mylauncher.R

class SleepDeviceAdmin : DeviceAdminReceiver() {

    override fun onDisableRequested(context: Context, intent: Intent): CharSequence {
        return context.getString(R.string.dt2s_admin_warning)
    }
}