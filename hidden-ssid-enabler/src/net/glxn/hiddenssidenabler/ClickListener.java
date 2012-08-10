package net.glxn.hiddenssidenabler;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;

public class ClickListener implements View.OnClickListener {
    private final NetworkListingAdapter networkListingAdapter;

    public ClickListener(NetworkListingAdapter networkListingAdapter) {
        this.networkListingAdapter = networkListingAdapter;
    }

    public void onClick(View view) {
        WifiConfiguration network = (WifiConfiguration) view.getTag();
        WifiManager wifi = networkListingAdapter.getWifiManager();

        network.hiddenSSID = !network.hiddenSSID;
        wifi.updateNetwork(network);
        wifi.saveConfiguration();
        boolean isCurrent = networkListingAdapter.isCurrentNetwork(network);
        if (isCurrent) {
            wifi.disableNetwork(network.networkId);
        } else {
            wifi.enableNetwork(network.networkId, true);
            wifi.reconnect();
        }
        waitForStateChange(network, isCurrent, networkListingAdapter);
        rebuildList(networkListingAdapter);
    }

    void rebuildList(NetworkListingAdapter networkListingAdapter) {
        networkListingAdapter.clear();
        for (WifiConfiguration configuredNetwork : networkListingAdapter.getWifiManager().getConfiguredNetworks()) {
            networkListingAdapter.add(configuredNetwork);
        }
    }

    void waitForStateChange(WifiConfiguration network, boolean current, NetworkListingAdapter networkListingAdapter) {
        int count = 0;
        while (networkListingAdapter.isCurrentNetwork(network) == current && count != 20) {
            Log.i(NetworkListingAdapter.class.getName(), "waiting for state change...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            count++;
        }
    }
}