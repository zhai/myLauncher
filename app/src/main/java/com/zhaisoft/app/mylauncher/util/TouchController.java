package com.zhaisoft.app.mylauncher.util;

import android.view.MotionEvent;

public interface TouchController {

    boolean onControllerTouchEvent(MotionEvent ev);
    boolean onControllerInterceptTouchEvent(MotionEvent ev);
}
