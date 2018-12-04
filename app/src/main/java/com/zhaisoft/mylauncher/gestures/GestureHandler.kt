package com.zhaisoft.mylauncher.gestures

import com.zhaisoft.mylauncher.Launcher

abstract class GestureHandler(val launcher: Launcher) {

    abstract fun onGestureTrigger()
}