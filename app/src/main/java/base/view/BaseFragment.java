package base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unmayshroff.notes.R;

import base.controller.ResponseError;
import base.listeners.BaseListener;
import base.utils.NetworkUtil;
import base.utils.SharedPreferenceHelper;

/**
 * Created by sumit.verma90775 on 4/24/2018.
 * this class contain all common method for fragments
 */

public abstract class BaseFragment extends Fragment implements BaseListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        updateTitle();
        updateActionBar();
        updateBottomBar();
    }

    public void updateTitle() {

    }


    /*
    Override below method and call bottombar update methods inside
    For example:
    @Override
    public void updateBottomBar() {
        updateActivityHandler.showPayEMIFab(true);
        updateActivityHandler.showBottomBar(true);
        updateActivityHandler.updateBottomBar(true);
    }
    */
    public void updateBottomBar() {

    }

    /*
    Override below method and call actionbar/drawer update methods inside
    For example:
    @Override
    public void updateActionBar() {
        updateActivityHandler.changeDrawerState(true);
        updateActivityHandler.removeToolbarElevation();
    }
    */
    public void updateActionBar() {

    }

    protected abstract int getLayoutResId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showErrorDialog(String message) {
        /*DialogUtils.getRedesignAlertDialog(getActivity(), null, message, false, getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, null, null);*/
    }

    @Override
    public void showErrorDialog(int reqCode, ResponseError error, String message) {
        /*DialogUtils.getRedesignAlertDialog(getActivity(), null, message, false, getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onErrorDialogClick(reqCode, error);
            }
        }, null, null);*/
    }

    public void showProgressDialog() {
        showProgressDialog(R.string.loading);
    }

    public void showProgressDialog(int msgResId) {
        showProgressDialog(msgResId, false);
    }

    public void showProgressDialog(boolean isCancellable) {
        showProgressDialog(R.string.loading, isCancellable);
    }

    public void showProgressDialog(int msgResId, boolean isCancellable) {
        showProgressDialog(getString(msgResId), isCancellable);
    }

    public void showProgressDialog(String msg) {
        showProgressDialog(msg, false);
    }

    public void showProgressDialog(String message, boolean isCancelable) {
        CustomProgressDialog.showProgressDialog(getActivity(), message, isCancelable);
    }

    @Override
    public void dismissProgressDialog() {
        CustomProgressDialog.dismissDialog();
    }

    @Override
    public void forceDismissProgressDialog() {
        CustomProgressDialog.forceDismissProgressDialog();
    }

    @Override
    public boolean hasInternetConnection() {
        boolean internetConnection = NetworkUtil.checkInternetConnection(getActivity().getApplicationContext());
//        if (!internetConnection) {
//            AndroidCommonUtils.showNoConnectionSnackBar(getActivity(), Snackbar.LENGTH_LONG);
//            Intent intent = new Intent(getActivity(), NetworkErrorActivity.class);
//            intent.putExtra("title", getActivity().getTitle());
//            startActivityForResult(intent, REQUEST_CODE);
//        }
        return internetConnection;
    }

    @Override
    public void handleNoInternetConnection() {

    }

    @Override
    public String getSharedPrefStringValue(String key) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getActivity());
        return sharedPreferenceHelper.getString(key, "");
    }

    @Override
    public String getSharedPrefStringValue(String key, String defaultVal) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getActivity());
        return sharedPreferenceHelper.getString(key, defaultVal);
    }

    @Override
    public int getSharedPrefIntValue(String key) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getActivity());
        return sharedPreferenceHelper.getInt(key, -1);
    }

    @Override
    public int getSharedPrefIntValue(String key, int defaultVal) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getActivity());
        return sharedPreferenceHelper.getInt(key, defaultVal);
    }

    @Override
    public void putSharedPrefIntValue(String key, int val) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getActivity());
        sharedPreferenceHelper.putInt(key, val);
    }

    @Override
    public void putSharedPrefStringValue(String key, String val) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getActivity());
        sharedPreferenceHelper.putString(key, val);
    }

}
