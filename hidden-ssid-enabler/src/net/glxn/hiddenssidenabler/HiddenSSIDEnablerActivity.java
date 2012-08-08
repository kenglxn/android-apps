package net.glxn.hiddenssidenabler;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HiddenSSIDEnablerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new NetworkListingAdapter(this, getPotentiallyHiddenNetworks()));
    }

    private List<WifiConfiguration> getPotentiallyHiddenNetworks() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

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