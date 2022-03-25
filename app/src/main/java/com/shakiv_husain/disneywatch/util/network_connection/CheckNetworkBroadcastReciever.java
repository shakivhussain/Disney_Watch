package com.shakiv_husain.disneywatch.util.network_connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shakiv_husain.disneywatch.activities.NoInternetConnectionActivity;

public class CheckNetworkBroadcastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean networkStatus;

        switch (intent.getAction()) {

            case "android.net.conn.CONNECTIVITY_CHANGE":
                networkStatus = NetworkUtil.mCheckNetworkStatus(context);
                if (networkStatus == false) {
                    Intent acivityIntent = new Intent(context.getApplicationContext(), NoInternetConnectionActivity.class);
                    acivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(acivityIntent);
                }
        }
    }
}
