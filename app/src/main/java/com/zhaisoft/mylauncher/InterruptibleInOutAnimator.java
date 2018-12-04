/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhaisoft.mylauncher;

import android.animation.ValueAnimator;

/**
 * A convenience class for two-way animations, e.g. a fadeIn/fadeOut animation.
 * With a regular ValueAnimator, if you call reverse to show the 'out' animation, you'll get
 * a frame-by-frame mirror of the 'in' animation -- i.e., the interpolated values will
 * be exactly reversed. Using this class, both the 'in' and the 'out' animation use the
 * interpolator in the same direction.
 */
public class InterruptibleInOutAnimator {
    private long mOriginalDuration;
    private float mOriginalFromValue;
    private float mOriginalToValue;
    private ValueAnimator mAnimator;

    private boolean mFirstRun = true;

    private Object mTag = null;

    private static final int IN = 1;
    private static final int OUT = 2;

    public InterruptibleInOutAnimator(long duration, float fromValue, float toValue) {
        mAnimator = LauncherAnimUtils.ofFloat(fromValue, toValue).setDuration(duration);
        mOriginalDuration = duration;
        mOriginalFromValue = fromValue;
        mOriginalToValue = toValue;
    }

    private void animate(int direction) {
        final long currentPlayTime = mAnimator.getCurrentPlayTime();
        final float toValue = (direction == IN) ? mOriginalToValue : mOriginalFromValue;
        final float startValue = mFirstRun ? mOriginalFromValue :
                (Float) mAnimator.getAnimatedValue();

        // Make sure it's stopped before we modify any values
        cancel();

        // Ensure we don't calculate a non-sensical duration
        long duration = mOriginalDuration - currentPlayTime;
        mAnimator.setDuration(Math.max(0, Math.min(duration, mOriginalDuration)));

        mAnimator.setFloatValues(startValue, toValue);
        mAnimator.start();
        mFirstRun = false;
    }

    public void cancel() {
        mAnimator.cancel();
    }

    public void end() {
        mAnimator.end();
    }

    /**
     * This is the equivalent of calling Animator.start(), except that it can be called when
     * the animation is running in the opposite direction, in which case we reverse
     * direction and animate for a correspondingly shorter duration.
     */
    public void animateIn() {
        animate(IN);
    }

    /**
     * This is the roughly the equivalent of calling Animator.reverse(), except that it uses the
     * same interpolation curve as animateIn(), rather than mirroring it. Also, like animateIn(),
     * if the animation is currently running in the opposite direction, we reverse
     * direction and animate for a correspondingly shorter duration.
     */
    public void animateOut() {
        animate(OUT);
    }

    public void setTag(Object tag) {
        mTag = tag;
    }

    public Object getTag() {
        return mTag;
    }

    public ValueAnimator getAnimator() {
        return mAnimator;
    }
}
