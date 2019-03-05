-keep,allowshrinking,allowoptimization class com.zhaisoft.app.mylauncher.** {
  *;
}

-keep class com.zhaisoft.app.mylauncher.BaseRecyclerViewFastScrollBar {
  public void setThumbWidth(int);
  public int getThumbWidth();
  public void setTrackWidth(int);
  public int getTrackWidth();
}

-keep class com.zhaisoft.app.mylauncher.BaseRecyclerViewFastScrollPopup {
  public void setAlpha(float);
  public float getAlpha();
}

-keep class com.zhaisoft.app.mylauncher.ButtonDropTarget {
  public int getTextColor();
}

-keep class com.zhaisoft.app.mylauncher.CellLayout {
  public float getBackgroundAlpha();
  public void setBackgroundAlpha(float);
}

-keep class com.zhaisoft.app.mylauncher.CellLayout$LayoutParams {
  public void setWidth(int);
  public int getWidth();
  public void setHeight(int);
  public int getHeight();
  public void setX(int);
  public int getX();
  public void setY(int);
  public int getY();
}

-keep class com.zhaisoft.app.mylauncher.dragndrop.DragLayer$LayoutParams {
  public void setWidth(int);
  public int getWidth();
  public void setHeight(int);
  public int getHeight();
  public void setX(int);
  public int getX();
  public void setY(int);
  public int getY();
}

-keep class com.zhaisoft.app.mylauncher.FastBitmapDrawable {
  public void setDesaturation(float);
  public float getDesaturation();
  public void setBrightness(float);
  public float getBrightness();
}

-keep class com.zhaisoft.app.mylauncher.PreloadIconDrawable {
  public float getAnimationProgress();
  public void setAnimationProgress(float);
}

-keep class com.zhaisoft.app.mylauncher.pageindicators.CaretDrawable {
  public float getCaretProgress();
  public void setCaretProgress(float);
}

-keep class com.zhaisoft.app.mylauncher.Workspace {
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

-keep class com.zhaisoft.app.mylauncher.DeferredHandler {
 *;
}

# Proguard will strip new callbacks in LauncherApps.Callback from
# WrappedCallback if compiled against an older SDK. Don't let this happen.
-keep class com.zhaisoft.app.mylauncher.compat.** {
  *;
}

-keep class com.zhaisoft.app.mylauncher.preferences.HiddenAppsFragment {
  *;
}

-keep class com.zhaisoft.app.mylauncher.preferences.ShortcutBlacklistFragment {
  *;
}