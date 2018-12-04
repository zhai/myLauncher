-keep,allowshrinking,allowoptimization class com.zhaisoft.mylauncher.** {
  *;
}

-keep class com.zhaisoft.mylauncher.BaseRecyclerViewFastScrollBar {
  public void setThumbWidth(int);
  public int getThumbWidth();
  public void setTrackWidth(int);
  public int getTrackWidth();
}

-keep class com.zhaisoft.mylauncher.BaseRecyclerViewFastScrollPopup {
  public void setAlpha(float);
  public float getAlpha();
}

-keep class com.zhaisoft.mylauncher.ButtonDropTarget {
  public int getTextColor();
}

-keep class com.zhaisoft.mylauncher.CellLayout {
  public float getBackgroundAlpha();
  public void setBackgroundAlpha(float);
}

-keep class com.zhaisoft.mylauncher.CellLayout$LayoutParams {
  public void setWidth(int);
  public int getWidth();
  public void setHeight(int);
  public int getHeight();
  public void setX(int);
  public int getX();
  public void setY(int);
  public int getY();
}

-keep class com.zhaisoft.mylauncher.dragndrop.DragLayer$LayoutParams {
  public void setWidth(int);
  public int getWidth();
  public void setHeight(int);
  public int getHeight();
  public void setX(int);
  public int getX();
  public void setY(int);
  public int getY();
}

-keep class com.zhaisoft.mylauncher.FastBitmapDrawable {
  public void setDesaturation(float);
  public float getDesaturation();
  public void setBrightness(float);
  public float getBrightness();
}

-keep class com.zhaisoft.mylauncher.PreloadIconDrawable {
  public float getAnimationProgress();
  public void setAnimationProgress(float);
}

-keep class com.zhaisoft.mylauncher.pageindicators.CaretDrawable {
  public float getCaretProgress();
  public void setCaretProgress(float);
}

-keep class com.zhaisoft.mylauncher.Workspace {
  public float getBackgroundAlpha();
  public void setBackgroundAlpha(float);
}

-keep class com.google.android.libraries.launcherclient.* {
  *;
}

-keep,allowshrinking,allowoptimization class me.jfenn.attribouter.** {
 *;
}

-dontwarn javax.**
-dontwarn org.codehaus.mojo.animal_sniffer.**

-keep class com.zhaisoft.mylauncher.DeferredHandler {
 *;
}

# Proguard will strip new callbacks in LauncherApps.Callback from
# WrappedCallback if compiled against an older SDK. Don't let this happen.
-keep class com.zhaisoft.mylauncher.compat.** {
  *;
}

-keep class com.zhaisoft.mylauncher.preferences.HiddenAppsFragment {
  *;
}

-keep class com.zhaisoft.mylauncher.preferences.ShortcutBlacklistFragment {
  *;
}