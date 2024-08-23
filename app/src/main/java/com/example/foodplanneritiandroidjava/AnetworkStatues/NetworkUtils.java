package com.example.foodplanneritiandroidjava.AnetworkStatues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;


/*
    im using this class to check internetConnection
 */
public class NetworkUtils {

        public static boolean isConnectedToInternet(Context context) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    NetworkCapabilities networkCapabilities =
                            connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

                    return networkCapabilities != null &&
                            (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
                } else {
                    android.net.NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                    return activeNetwork != null && activeNetwork.isConnected();
                }
            }
            return false;
        }
    }


