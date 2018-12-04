package com.zhaisoft.mylauncher.allapps;

/**
 * The unicode stripped search controller.
 */
public class UnicodeStrippedAppSearchController extends AllAppsSearchBarController {

    public DefaultAppSearchAlgorithm onInitializeSearch() {
        return new UnicodeStrippedAppSearchAlgorithm(mApps.getUnfilteredApps());
    }
}
