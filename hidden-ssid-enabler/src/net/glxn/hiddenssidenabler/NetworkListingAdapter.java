package net.glxn.hiddenssidenabler;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class NetworkListingAdapter extends ArrayAdapter<WifiConfiguration> {
    private final Context context;
    private final List<WifiConfiguration> networks;

    public NetworkListingAdapter(Context context, List<WifiConfiguration> networks) {
        super(context, R.layout.list_row, networks);
        this.context = context;
        this.networks = networks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        rowView.setClickable(true);
        rowView.setOnClickListener(createListener());

        TextView ssidView = (TextView) rowView.findViewById(R.id.ssid);
        TextView statusView = (TextView) rowView.findViewById(R.id.status);
        ImageButton toggleView = (ImageButton) rowView.findViewById(R.id.toggle);

        WifiConfiguration network = networks.get(position);

        ssidView.setText(network.SSID);
        statusView.setText("" + WifiConfiguration.Status.strings[network.status]);
        toggleView.setImageResource(network.hiddenSSID ? R.drawable.on : R.drawable.off);

        rowView.setTag(network);
        return rowView;
    }

    private View.OnClickListener createListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiConfiguration network = (WifiConfiguration) view.getTag();
                network.hiddenSSID = !network.hiddenSSID;

                WifiManager wifi = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
                wifi.updateNetwork(network);
                wifi.saveConfiguration();

                notifyDataSetChanged();
            }
        };
    }


} 