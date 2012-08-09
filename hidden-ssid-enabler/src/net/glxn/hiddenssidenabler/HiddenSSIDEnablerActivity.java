package net.glxn.hiddenssidenabler;

import android.app.Activity;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class HiddenSSIDEnablerActivity extends Activity {

    private final NetworkResolver networkResolver = new NetworkResolver();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        ListView listView = (ListView) findViewById(R.id.list);
        List<WifiConfiguration> potentiallyHiddenNetworks = networkResolver.getPotentiallyHiddenNetworks(getApplicationContext());
        listView.setAdapter(new NetworkListingAdapter(this, potentiallyHiddenNetworks));
    }

}