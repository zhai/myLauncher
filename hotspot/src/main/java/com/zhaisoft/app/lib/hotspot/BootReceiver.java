package com.zhaisoft.app.lib.hotspot;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class BootReceiver extends BroadcastReceiver {
    private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";


    static final int MY_PERMISSIONS_MANAGE_WRITE_SETTINGS = 100;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 69;

    private boolean mLocationPermission = false;
    private boolean mSettingPermission = true;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_BOOT.equals(intent.getAction())) {
            //Toast.makeText(context, R.string.bootup_receiver, Toast.LENGTH_SHORT).show();

            WifiAPUtils mWifiAPUtils = new WifiAPUtils(context);
            if (mWifiAPUtils.isWifiApEnable()) {
                //如果ap是启动的 不动
            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.System.canWrite(context.getApplicationContext())) {
                        mSettingPermission = false;
                        try {
                            Intent intent2 = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + context.getPackageName()));
                            context.startActivity(intent2);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                if (mWifiAPUtils.setAP(true)) {
                    Intent intent2 = new Intent();
                    intent2.setAction(WidgetProvider.CHANGE_WIDGET_ON);
                    context.sendBroadcast(intent2);
                }
            }
        }
    }
}
