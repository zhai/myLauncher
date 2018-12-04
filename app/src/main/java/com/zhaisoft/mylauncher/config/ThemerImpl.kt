package com.zhaisoft.mylauncher.config

import android.content.Context
import com.zhaisoft.mylauncher.Utilities
import com.zhaisoft.mylauncher.allapps.theme.AllAppsBaseTheme
import com.zhaisoft.mylauncher.allapps.theme.AllAppsVerticalTheme
import com.zhaisoft.mylauncher.allapps.theme.IAllAppsThemer
import com.zhaisoft.mylauncher.popup.theme.IPopupThemer
import com.zhaisoft.mylauncher.popup.theme.PopupBaseTheme
import com.zhaisoft.mylauncher.popup.theme.PopupCardTheme

open class ThemerImpl : IThemer {

    var allAppsTheme: IAllAppsThemer? = null
    var popupTheme: IPopupThemer? = null

    override fun allAppsTheme(context: Context): IAllAppsThemer {
        val useVerticalLayout = Utilities.getPrefs(context).verticalDrawerLayout
        if (allAppsTheme == null ||
                (useVerticalLayout && allAppsTheme !is AllAppsVerticalTheme) ||
                (!useVerticalLayout && allAppsTheme is AllAppsVerticalTheme))
            allAppsTheme = if (useVerticalLayout) AllAppsVerticalTheme(context) else AllAppsBaseTheme(context)
        return allAppsTheme!!
    }

    override fun popupTheme(context: Context): IPopupThemer {
        val useCardTheme = Utilities.getPrefs(context).popupCardTheme
        if (popupTheme == null ||
                (useCardTheme && popupTheme !is PopupCardTheme) ||
                (!useCardTheme && popupTheme !is PopupBaseTheme)) {
            popupTheme = if (useCardTheme) PopupCardTheme() else PopupBaseTheme()
        }
        return popupTheme!!
    }
}