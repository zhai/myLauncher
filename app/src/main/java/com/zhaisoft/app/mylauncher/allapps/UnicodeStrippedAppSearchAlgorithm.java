package com.zhaisoft.app.mylauncher.allapps;

import java.util.List;

import com.zhaisoft.app.mylauncher.AppInfo;
import com.zhaisoft.app.mylauncher.LauncherAppState;
import com.zhaisoft.app.mylauncher.util.UnicodeFilter;

/**
 * A search algorithm that changes every non-ascii characters to theirs ascii equivalents and
 * then performs comparison.
 */
public class UnicodeStrippedAppSearchAlgorithm extends DefaultAppSearchAlgorithm {
    public UnicodeStrippedAppSearchAlgorithm(List<AppInfo> apps) {
        super(apps);
    }

    @Override
    protected boolean matches(AppInfo info, String query) {
        if (info.componentName.getPackageName().equals(LauncherAppState.getInstanceNoCreate().getContext().getPackageName()))
            return false;

        String title = UnicodeFilter.filter(info.title.toString().toLowerCase());
        String strippedQuery = UnicodeFilter.filter(query.trim());

        return super.matches(title, strippedQuery);
    }
}
