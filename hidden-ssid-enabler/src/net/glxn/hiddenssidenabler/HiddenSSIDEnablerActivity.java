package net.glxn.hiddenssidenabler;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class HiddenSSIDEnablerActivity extends Activity {

    private static final String APP_NAME = "Hidden SSID Enabler";

    private NetworkListingAdapter adapter;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(APP_NAME, "recieved:"+intent);

            adapterInstance().rebuildNetworkList();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main);

        ((ListView) findViewById(R.id.list)).setAdapter(adapterInstance());

        super.onCreate(savedInstanceState);
    }

    private NetworkListingAdapter adapterInstance() {
        if(adapter == null) {
            adapter = new NetworkListingAdapter(this, ((WifiManager) getSystemService(Context.WIFI_SERVICE)).getConfiguredNetworks());
        }
        return adapter;
    }

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);

        ((ListView) findViewById(R.id.list)).setAdapter(adapterInstance());

        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_NAME)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}