package net.glxn.hiddenssidenabler;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

public class NetworkResolver {

    List<WifiConfiguration> getPotentiallyHiddenNetworks(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        List<WifiConfiguration> potentiallyHiddenNetworks = new ArrayList<WifiConfiguration>();
        for (WifiConfiguration configuredNetwork : wifi.getConfiguredNetworks()) {
            boolean potentiallyHidden = true;
            for (ScanResult scanResult : wifi.getScanResults()) {
                if (scanResult.SSID.equals(configuredNetwork.SSID)) {
                    potentiallyHidden = false;
                }
            }
            if (potentiallyHidden) {
                potentiallyHiddenNetworks.add(configuredNetwork);
            }
        }
        return potentiallyHiddenNetworks;
    }
}