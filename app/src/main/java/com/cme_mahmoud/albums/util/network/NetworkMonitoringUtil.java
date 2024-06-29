package com.cme_mahmoud.albums.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;


public class NetworkMonitoringUtil extends ConnectivityManager.NetworkCallback {
    public static final String TAG = NetworkMonitoringUtil.class.getSimpleName();

    private final NetworkRequest mNetworkRequest;
    private final ConnectivityManager mConnectivityManager;
    private final NetworkStateManager mNetworkStateManager;

    // Constructor
    public NetworkMonitoringUtil(Context context) {
        mNetworkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();

        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkStateManager = NetworkStateManager.getInstance();
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        mNetworkStateManager.setNetworkConnectivityStatus(true);
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        mNetworkStateManager.setNetworkConnectivityStatus(false);
    }

    /**
     * Registers the Network-Request callback
     * (Note: Register only once to prevent duplicate callbacks)
     */
    public void registerNetworkCallbackEvents() {
        mConnectivityManager.registerNetworkCallback(mNetworkRequest, this);
    }

    /**
     * Check current Network state
     */
    public void checkNetworkState() {
        try {
            NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
            mNetworkStateManager.setNetworkConnectivityStatus(networkInfo != null
                    && networkInfo.isConnected());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
