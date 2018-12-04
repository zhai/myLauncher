package com.zhaisoft.mylauncher.config

import android.content.Context
import com.zhaisoft.mylauncher.allapps.theme.IAllAppsThemer
import com.zhaisoft.mylauncher.popup.theme.IPopupThemer

interface IThemer {

    fun allAppsTheme(context: Context): IAllAppsThemer
    fun popupTheme(context: Context): IPopupThemer
}