package com.zhaisoft.app.lib.hotspot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import hotchemi.android.rate.AppRate;

public class HotSpotActivity extends FragmentActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private static final String TAG = HotSpotActivity.class.getSimpleName();
    private Switch mSwitch;
    private WifiManager mWifiManager;
    private ImageView mTetheringImage;
    private TextView mDescription;
    private EditText ssidEditText;
    private EditText passwordEditText;
    private Spinner spinner;
    private CheckBox checkBox;
    private Button save;
    private WifiAPUtils mWifiAPUtils;
    private String ssid;
    private String securityType;
    private String password;
    private SharedPreferences mSharedPrefs;
    private boolean isReflectionOK = false;


    static final int MY_PERMISSIONS_MANAGE_WRITE_SETTINGS = 100;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 69;

    private boolean mLocationPermission = false;
    private boolean mSettingPermission = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getApplicationContext())) {
                mSettingPermission = false;
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, MY_PERMISSIONS_MANAGE_WRITE_SETTINGS);
            }
        }

        mSwitch = (Switch) findViewById(R.id.ap_button);
        mTetheringImage = (ImageView) findViewById(R.id.tethering_image);
        mDescription = (TextView) findViewById(R.id.description);
        ssidEditText = (EditText) findViewById(R.id.ssid_editText);
        passwordEditText = (EditText) findViewById(R.id.password_editText);
        spinner = (Spinner) findViewById(R.id.spinner);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        save = (Button) findViewById(R.id.save_button);

        mWifiAPUtils = new WifiAPUtils(this);

        mSharedPrefs = this.getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE);
        ssid = mSharedPrefs.getString(Constants.PREFS_SSID, mWifiAPUtils.ssid);
        securityType = mSharedPrefs.getString(Constants.PREFS_SECURITY, mWifiAPUtils.securityType);
        password = mSharedPrefs.getString(Constants.PREFS_PASSWORD, mWifiAPUtils.password);

        setSwitchImageState();
        mSwitch.setOnCheckedChangeListener(this);
        showSpinner();
        save.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(this);
        mTetheringImage.setOnClickListener(this);

        ssidEditText.setText(ssid);
        passwordEditText.setText(password);

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        setSwitchImageState();

        isReflectionOK = mSharedPrefs.getBoolean(Constants.PREFS_REFLECT_STATUS, false);
        if (isReflectionOK) {
            //Config for rating recomendation, Only show dialog when reflection ok
            AppRate.getInstance().setInstallDays(2).setLaunchTimes(5).monitor(this);
            AppRate.showRateDialogIfMeetsConditions(this);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == MY_PERMISSIONS_MANAGE_WRITE_SETTINGS) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                mSettingPermission = true;
                if (!mLocationPermission) {
                    //locationsPermission();
                }
            } else {
                //settingPermission();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //TODO Add more settings
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void setSwitchImageState() {
        if (mWifiAPUtils.isWifiApEnable()) {
            mSwitch.setChecked(true);
            mTetheringImage.setImageResource(R.drawable.wifi_enabled);
        } else {
            mSwitch.setChecked(false);
            mTetheringImage.setImageResource(R.drawable.wifi_disabled);
        }
    }

    Handler handler = new Handler();

    public boolean createAp(boolean isOpen) {
        //如果wifi已经打开则先关闭wifi
        //closeWifi();
        //  Method method1=null;
        try {

            WifiConfiguration netConfig = new WifiConfiguration();

            netConfig.SSID = "builk_zhai";
            netConfig.preSharedKey = "12345678";
            Log.d(TAG, "WifiPresenter：createAp----->netConfig.SSID:"
                    + netConfig.SSID + ",netConfig.preSharedKey:" + netConfig.preSharedKey + ",isOpen=" + isOpen);
            netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            if (isOpen) {
                netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            } else {
                netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            }
            netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);


            WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {

                //保存ap设置
                Method methodsave = mWifiManager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class);
                methodsave.invoke(mWifiManager, netConfig);
                //获取ConnectivityManager对象，便于后面使用
                ConnectivityManager connManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                Field field = connManager.getClass().getDeclaredField("TETHERING_WIFI");
                field.setAccessible(true);
                int mTETHERING_WIFI = (int) field.get(connManager);

                Field iConnMgrField = connManager.getClass().getDeclaredField("mService");
                iConnMgrField.setAccessible(true);
                Object iConnMgr = iConnMgrField.get(connManager);
                Class<?> iConnMgrClass = Class.forName(iConnMgr.getClass().getName());
                Method startTethering = iConnMgrClass.getMethod("startTethering", int.class, ResultReceiver.class, boolean.class);
                startTethering.invoke(iConnMgr, mTETHERING_WIFI, new ResultReceiver(handler) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        super.onReceiveResult(resultCode, resultData);
                    }
                }, true);
                return true;


            } else {
                Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
                return (boolean) method.invoke(mWifiManager, netConfig, true);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int i = buttonView.getId();
        if (i == R.id.ap_button) {
            if (isChecked) {
                //Turn on AP
                enableAP();

                // createAp(true);


            } else {
                //createAp(false);
                //Turn off AP
                disableAP();
            }

        } else if (i == R.id.checkBox) {
            if (!isChecked) {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        }
    }

    public boolean enableAP() {
        if (mWifiAPUtils.setAP(true)) {
            mTetheringImage.setImageResource(R.drawable.wifi_enabled);
            mDescription.setText(R.string.tethering_off);
            Intent intent = new Intent();
            intent.setAction(WidgetProvider.CHANGE_WIDGET_ON);
            this.sendBroadcast(intent);
            return true;
        }
        showMessage(R.string.failed_on);
        return false;
    }

    public boolean disableAP() {
        if (mWifiAPUtils.setAP(false)) {
            mTetheringImage.setImageResource(R.drawable.wifi_disabled);
            mDescription.setText(R.string.tethering_on);
            mWifiAPUtils.enableWifi();
            Intent intent = new Intent();
            intent.setAction(WidgetProvider.CHANGE_WIDGET_OFF);
            this.sendBroadcast(intent);
            return true;
        }
        showMessage(R.string.failed_off);
        return false;
    }


    private void showMessage(int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.message);
        builder.setMessage(message);
        builder.create().show();
    }

    private void showSpinner() {
        List<String> security = new ArrayList<String>();
        security.add(WifiAPUtils.SECURE_OPEN);
        //security.add(WifiAPUtils.SECURE_WPA);
        security.add(WifiAPUtils.SECURE_WPA2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, security);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int index = security.indexOf(securityType);
        if (index != -1) {
            spinner.setSelection(index);
        } else {
            spinner.setSelection(0);
        }
        spinner.setOnItemSelectedListener(new CustomOnItemSelected());
    }

    public class CustomOnItemSelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            LinearLayout pass_layout = (LinearLayout) findViewById(R.id.password_layout);
            LinearLayout checkBox_layout = (LinearLayout) findViewById(R.id.checkBox_layout);
            securityType = parent.getItemAtPosition(position).toString();
            if (WifiAPUtils.SECURE_OPEN.equals(securityType)) {
                pass_layout.setVisibility(View.GONE);
                checkBox_layout.setVisibility(View.GONE);
            } else {
                pass_layout.setVisibility(View.VISIBLE);
                checkBox_layout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.save_button) {
            ssid = ssidEditText.getText().toString();
            password = passwordEditText.getText().toString();

            if (TextUtils.isEmpty(ssid)) {
                ssidEditText.setError("Network SSID is empty");
                return;
            }
            if (TextUtils.isEmpty(password) || password.length() < WifiAPUtils.PASS_MIN_LENGTH) {
                passwordEditText.setError("You must have 8 characters in password");
                return;
            }

            SharedPreferences.Editor editor = mSharedPrefs.edit();
            editor.putString(Constants.PREFS_SSID, ssid);
            editor.putString(Constants.PREFS_SECURITY, securityType);
            editor.putString(Constants.PREFS_PASSWORD, password);
            editor.commit();

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isAcceptingText()) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            Toast.makeText(HotSpotActivity.this, "Network Info saved", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.tethering_image) {
            if (mSwitch.isChecked()) {
                mSwitch.setChecked(false);
            } else {
                mSwitch.setChecked(true);
            }

        }
    }
}
