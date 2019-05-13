package com.hiteshsahu.slicedemo.domain.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

class WifiReceiver extends BroadcastReceiver {

    private WifiManager WifiManager;

    // This method call when number of wifi connections changed
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            WifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            List<ScanResult> mScanResults = WifiManager.getScanResults();
            // add your logic here
        }
    }

}