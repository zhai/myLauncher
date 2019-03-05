package com.zhaisoft.app.mylauncher.config

import android.content.Context
import com.zhaisoft.app.mylauncher.allapps.theme.IAllAppsThemer
import com.zhaisoft.app.mylauncher.popup.theme.IPopupThemer

interface IThemer {

    fun allAppsTheme(context: Context): IAllAppsThemer
    fun popupTheme(context: Context): IPopupThemer
}