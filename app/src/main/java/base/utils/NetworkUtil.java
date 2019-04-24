package base.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtil {
    public static boolean checkInternetConnection(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        return conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected();
    }
}
