package base.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.unmayshroff.notes.R;

import base.controller.ResponseError;
import base.listeners.BaseListener;
import base.listeners.FragmentBackPressedListener;
import base.utils.NetworkUtil;
import base.utils.SharedPreferenceHelper;

public abstract class BaseActivity extends AppCompatActivity implements
        FragmentManager.OnBackStackChangedListener, BaseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // reset "secure" flag on view, to make screenshots work again, when activity is in foreground
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    protected void onPause() {
        // make the application view "secure" to prevent thumbnail with sensitive data in recent screens
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);


        super.onPause();
    }

    /**
     * @param title: title of action bar
     */
    public void setActionBarTitle(@NonNull String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    /**
     * hide Action bar Back button
     */
    public void hideActionBarBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    /**
     * Adds to back stack by default
     *
     * @param fragment
     */
    public void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, fragment.getClass().getSimpleName(), true);
    }

    /**
     * Adds to back stack by default
     *
     * @param fragment
     * @param tag
     */
    public void replaceFragment(Fragment fragment, String tag) {
        replaceFragment(fragment, tag, true);
    }

    /**
     * @param fragment
     * @param tag
     * @param addToBackStack
     */
    public void replaceFragment(Fragment fragment, String tag, boolean addToBackStack) {
        addOrReplaceFragment(false, fragment, tag, addToBackStack);
    }

    /**
     * Adds to back stack by default
     *
     * @param fragment
     * @param tag
     */
    public void addFragment(Fragment fragment, String tag) {
        addFragment(fragment, tag, true);
    }

    /**
     * @param fragment
     * @param tag
     * @param addToBackStack
     */
    public void addFragment(Fragment fragment, String tag, boolean addToBackStack) {
        addOrReplaceFragment(true, fragment, tag, addToBackStack);
    }

    private void addOrReplaceFragment(boolean add, Fragment fragment, String tag, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isFragmentInBackstack(fragmentManager, fragment.getClass().getSimpleName())) {
            fragmentManager.popBackStack(fragment.getClass().getSimpleName(), 0);
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (add) {
                fragmentTransaction.add(R.id.framelayout, fragment, tag);
            } else {
                fragmentTransaction.replace(R.id.framelayout, fragment, tag);
            }
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commitAllowingStateLoss();
        }

    }

    @Override
    public void onBackStackChanged() {
        BaseFragment baseFragment = getFragment();
        if (baseFragment != null) {
            baseFragment.updateTitle();
            baseFragment.updateActionBar();
            baseFragment.updateBottomBar();
        }
    }

    protected BaseFragment getFragment() {
        return (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout);
    }

    protected abstract int getLayoutResId();

    @Override
    public void showProgressDialog(int msgResId) {
        showProgressDialog(msgResId, false);
    }

    @Override
    public void showProgressDialog(int msgResId, boolean isCancellable) {
        showProgressDialog(getString(msgResId), isCancellable);
    }

    @Override
    public void showProgressDialog() {
        CustomProgressDialog.showProgressDialog(this);
    }

    @Override
    public void showProgressDialog(String message) {
        CustomProgressDialog.showProgressDialog(this, message);
    }

    @Override
    public void showProgressDialog(boolean cancelable) {
        CustomProgressDialog.showProgressDialog(this, cancelable);
    }

    @Override
    public void showProgressDialog(String message, boolean cancelable) {
        CustomProgressDialog.showProgressDialog(this, message, cancelable);
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
    public void onApiResponse(int reqCode, Object response) {

    }

    @Override
    public void showError(String message) {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setMessage(message)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void showErrorDialog(String message) {
        /*DialogUtils.getRedesignAlertDialog(this, null, message, false, getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, null, null);*/
    }

    @Override
    public void showErrorDialog(int reqCode, ResponseError error, String message) {
        /*DialogUtils.getRedesignAlertDialog(this, null, message, false, getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onErrorDialogClick(reqCode, error);
            }
        }, null, null);*/
    }

    @Override
    public boolean hasInternetConnection() {
        return NetworkUtil.checkInternetConnection(getApplicationContext());
    }

    private boolean isFragmentInBackstack(final FragmentManager fragmentManager, final String tagName) {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            if (tagName.equals(fragmentManager.getBackStackEntryAt(i).getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleNoInternetConnection() {

    }

    @Override
    public String getSharedPrefStringValue(String key) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(BaseActivity.this);
        return sharedPreferenceHelper.getString(key, "");
    }

    @Override
    public String getSharedPrefStringValue(String key, String defaultVal) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getApplicationContext());
        return sharedPreferenceHelper.getString(key, defaultVal);
    }

    @Override
    public int getSharedPrefIntValue(String key) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getApplicationContext());
        return sharedPreferenceHelper.getInt(key, -1);
    }

    @Override
    public int getSharedPrefIntValue(String key, int defaultVal) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getApplicationContext());
        return sharedPreferenceHelper.getInt(key, defaultVal);
    }

    @Override
    public void putSharedPrefIntValue(String key, int val) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getApplicationContext());
        sharedPreferenceHelper.putInt(key, val);
    }

    @Override
    public void putSharedPrefStringValue(String key, String val) {
        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getApplicationContext());
        sharedPreferenceHelper.putString(key, val);
    }

    @Override
    public void onBackPressed() {
        if (!(getFragment() instanceof FragmentBackPressedListener) || !((FragmentBackPressedListener) getFragment()).onFragmentBackPressed()) {
            super.onBackPressed();
        }
    }
}