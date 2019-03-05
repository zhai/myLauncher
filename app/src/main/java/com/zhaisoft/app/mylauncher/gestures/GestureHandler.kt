package com.zhaisoft.app.mylauncher.gestures

import com.zhaisoft.app.mylauncher.Launcher

abstract class GestureHandler(val launcher: Launcher) {

    abstract fun onGestureTrigger()
}