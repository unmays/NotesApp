package base.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.unmayshroff.notes.R;

/**
 * Created by unmay.shroff91257 on 9/11/2018.
 */

public class CustomProgressDialog {

    private static final String TAG = CustomProgressDialog.class.getSimpleName();
    private static Dialog dialog;
    private static int apiCalls;

    public static void showRedesignProgressDialog(Context context) {
        showProgressDialog(context, context.getString(R.string.please_wait), false);
    }

    public static void showProgressDialog(Context context) {
        showProgressDialog(context, context.getString(R.string.loading), false);
    }

    public static void showProgressDialog(Context context, String message) {
        showProgressDialog(context, message, false);
    }

    public static void showProgressDialog(Context context, boolean cancelable) {
        showProgressDialog(context, context.getString(R.string.loading), cancelable);
    }

    public static void showProgressDialog(Context context, String message, boolean cancelable) {
        apiCalls++;
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.setCancelable(cancelable);
            dialog.show();
        }
        Log.d(TAG, "showProgressDialog apiCalls = " + apiCalls);
    }

    public static void dismissDialog() {
        if (apiCalls > 0) {
            apiCalls--;
        }
        if (dialog != null && dialog.isShowing() && areApiCallsCompleted()) {
            dialog.dismiss();
            dialog = null;
        }
        Log.d(TAG, "dismissDialog apiCalls = " + apiCalls);
    }

    public static void forceDismissProgressDialog() {
        if (dialog != null) {
            apiCalls = 0;
            dialog.dismiss();
            dialog = null;
        }
    }

    public static boolean areApiCallsCompleted() {
        return apiCalls == 0;
    }
}
