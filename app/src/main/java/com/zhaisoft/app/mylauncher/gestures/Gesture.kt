package com.zhaisoft.app.mylauncher.gestures

import android.view.MotionEvent

interface Gesture {

    val isEnabled: Boolean

    fun onTouchEvent(ev: MotionEvent): Boolean
}
