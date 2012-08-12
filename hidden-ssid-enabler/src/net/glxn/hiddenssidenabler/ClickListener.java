package net.glxn.hiddenssidenabler;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ClickListener implements View.OnClickListener {
    private final NetworkListingAdapter networkListingAdapter;
    private final WifiManager wifiManager;

    public ClickListener(NetworkListingAdapter networkListingAdapter) {
        this.networkListingAdapter = networkListingAdapter;
        wifiManager = networkListingAdapter.getWifiManager();
    }

    public void onClick(View view) {
        WifiConfiguration network = (WifiConfiguration) view.getTag();

        toggleHiddenSSID(network);

        boolean connectedToNetwork = networkListingAdapter.isCurrentNetwork(network);
        if (connectedToNetwork) {
            wifiManager.disableNetwork(network.networkId);
        } else {
            forceConnection(network);
        }

        rebuildNetworkList();
    }

    private void toggleHiddenSSID(WifiConfiguration network) {
        network.hiddenSSID = !network.hiddenSSID;
        wifiManager.updateNetwork(network);
        wifiManager.saveConfiguration();
    }

    private void forceConnection(WifiConfiguration network) {
        networkListingAdapter.makeToast("Connecting...", Toast.LENGTH_SHORT);
        wifiManager.enableNetwork(network.networkId, true);
        wifiManager.reconnect();
        waitForConnection(network);
    }

    private void waitForConnection(WifiConfiguration network) {
        int count = 0;
        while (!networkListingAdapter.isCurrentNetwork(network) && count != 20) {
            Log.i(NetworkListingAdapter.class.getName(), "waiting for connection to " + network.SSID + "count=" + count);
            sleep();

            count++;
            if(count == 20) {
                String message = "Unable to connect to " + network.SSID + ". Please check SSID spelling and credentials..";
                networkListingAdapter.makeToast(message, Toast.LENGTH_LONG);
            }
        }
    }

    private void sleep() {
        int ms = 100;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    void rebuildNetworkList() {
        networkListingAdapter.clear();
        for (WifiConfiguration configuredNetwork : wifiManager.getConfiguredNetworks()) {
            networkListingAdapter.add(configuredNetwork);
        }
    }

}