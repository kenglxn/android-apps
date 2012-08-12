package net.glxn.hiddenssidenabler;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NetworkListingAdapter extends ArrayAdapter<WifiConfiguration> {

    private final Context context;
    final NetworkConfigureClickListener networkConfigureClickListener;

    public NetworkListingAdapter(Context context, List<WifiConfiguration> networks) {
        super(context, R.layout.list_row, networks);

        this.context = context;
        setNotifyOnChange(true);
        networkConfigureClickListener = new NetworkConfigureClickListener(this);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WifiConfiguration network = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        TextView ssidView = (TextView) rowView.findViewById(R.id.ssid);
        TextView statusView = (TextView) rowView.findViewById(R.id.status);
        ImageView toggleView = (ImageView) rowView.findViewById(R.id.toggle);

        rowView.setClickable(true);
        rowView.setOnClickListener(networkConfigureClickListener);
//        rowView.setOnLongClickListener(); TODO: give options here? e.g. delete

        ssidView.setText(network.SSID);
        statusView.setText(WifiConfiguration.Status.strings[network.status] + ", hiddenSSID:" + network.hiddenSSID);
        toggleView.setImageResource(isCurrentNetwork(network) ? R.drawable.steel_on : R.drawable.steel_off);

        rowView.setTag(network);
        return rowView;
    }

    boolean isCurrentNetwork(WifiConfiguration network) {
        String connectedNetwork = getWifiManager().getConnectionInfo().getSSID();
        return network.SSID.equals("\"" + connectedNetwork + "\"");
    }

    WifiManager getWifiManager() {
        return ((WifiManager) context.getSystemService(Context.WIFI_SERVICE));
    }

    public void makeToast(String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    void rebuildNetworkList() {
        clear();
        for (WifiConfiguration configuredNetwork : getWifiManager().getConfiguredNetworks()) {
            add(configuredNetwork);
        }
    }
}