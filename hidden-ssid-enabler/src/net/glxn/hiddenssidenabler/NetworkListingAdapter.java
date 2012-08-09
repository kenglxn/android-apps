package net.glxn.hiddenssidenabler;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class NetworkListingAdapter extends ArrayAdapter<WifiConfiguration> {

    private final Context context;

    public NetworkListingAdapter(Context context, List<WifiConfiguration> networks) {
        super(context, R.layout.list_row, networks);
        this.context = context;
        setNotifyOnChange(true);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WifiConfiguration network = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        TextView ssidView = (TextView) rowView.findViewById(R.id.ssid);
        TextView statusView = (TextView) rowView.findViewById(R.id.status);
        ImageButton toggleView = (ImageButton) rowView.findViewById(R.id.toggle);

        rowView.setClickable(true);
        rowView.setOnClickListener(createListener());

        ssidView.setText(network.SSID);
        statusView.setText(WifiConfiguration.Status.strings[network.status] + ", hiddenSSID:" + network.hiddenSSID);
        toggleView.setImageResource(isCurrentNetwork(network) ? R.drawable.on : R.drawable.off);

        rowView.setTag(network);
        return rowView;
    }

    private boolean isCurrentNetwork(WifiConfiguration network) {
        String connectedNetwork = getWifiManager().getConnectionInfo().getSSID();
        return network.SSID.equals("\"" + connectedNetwork + "\"");
    }

    private View.OnClickListener createListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiConfiguration network = (WifiConfiguration) view.getTag();
                WifiManager wifi = getWifiManager();

                network.hiddenSSID = !network.hiddenSSID;
                wifi.updateNetwork(network);
                wifi.saveConfiguration();
                if (isCurrentNetwork(network)) {
                    wifi.disableNetwork(network.networkId);
                } else {
                    wifi.enableNetwork(network.networkId, true);
                    waitForConnection(network);
                }

                rebuildList();
            }
        };
    }

    private void waitForConnection(WifiConfiguration network) {
        int count = 0;
        while (!isCurrentNetwork(network) && count != 10) {
            Log.i(NetworkListingAdapter.class.getName(), "waiting for connection...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }

    private void rebuildList() {
        clear();
        for (WifiConfiguration configuredNetwork : getWifiManager().getConfiguredNetworks()) {
            add(configuredNetwork);
        }
    }

    private WifiManager getWifiManager() {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }


} 