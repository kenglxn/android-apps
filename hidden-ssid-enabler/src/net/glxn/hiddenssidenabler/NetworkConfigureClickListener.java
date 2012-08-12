package net.glxn.hiddenssidenabler;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.Toast;

public class NetworkConfigureClickListener implements View.OnClickListener {
    private final NetworkListingAdapter networkListingAdapter;
    private final WifiManager wifiManager;

    public NetworkConfigureClickListener(NetworkListingAdapter networkListingAdapter) {
        this.networkListingAdapter = networkListingAdapter;
        wifiManager = networkListingAdapter.getWifiManager();
    }

    public void onClick(View view) {
        WifiConfiguration network = (WifiConfiguration) view.getTag();

        toggleHiddenSSID(network);

        boolean connectedToNetwork = isCurrentNetwork(network);
        if (connectedToNetwork) {
            wifiManager.disableNetwork(network.networkId);
        } else {
            forceConnection(network);
        }

        networkListingAdapter.rebuildNetworkList();
    }

    private void toggleHiddenSSID(WifiConfiguration network) {
        network.hiddenSSID = !network.hiddenSSID;
        wifiManager.updateNetwork(network);
        wifiManager.saveConfiguration();
    }

    private void forceConnection(WifiConfiguration network) {
        networkListingAdapter.makeToast("Connecting...", Toast.LENGTH_SHORT);

        wifiManager.enableNetwork(network.networkId, true);
        boolean reconnected = wifiManager.reconnect();

        if(!reconnected) {
            //TODO: IF was unable to connect, need a futuretask for this?
            networkListingAdapter.makeToast("Unable to connect to " + network.SSID + ". Please check SSID spelling and credentials", Toast.LENGTH_LONG);
        }
    }

    private boolean isCurrentNetwork(WifiConfiguration network) {
        return networkListingAdapter.isCurrentNetwork(network);
    }

}