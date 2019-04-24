package base.listeners;

import java.util.ArrayList;
import java.util.List;

import base.controller.ResponseError;

/**
 * Created by sumit.verma90775 on 5/3/2018.
 */

public interface BaseListener {

    default void showProgressDialog() {

    }

    default void showProgressDialog(String message) {

    }

    default void showProgressDialog(boolean cancelable) {

    }

    default void showProgressDialog(String message, boolean cancelable) {

    }

    default void dismissProgressDialog() {

    }

    default void onApiResponse(int reqCode, Object response) {

    }

    default void onApiError(int reqCode, ResponseError error) {

    }

    default void showError(String message) {

    }

    default void showErrorDialog(String message) {
    }

    /**
     * Calls onErrorDialogClick when user clicks 'Ok' on the dialog
     *
     * @param reqCode
     * @param error
     * @param message
     */
    default void showErrorDialog(int reqCode, ResponseError error, String message) {
    }

    /**
     * Called when user clicks 'Ok' from the API error dialog called from showErrorDialog(int reqCode, ResponseError error, String message)
     *
     * @param reqCode
     * @param error
     */
    default void onErrorDialogClick(int reqCode, ResponseError error) {

    }

    String getString(int stringResId);

    boolean hasInternetConnection();

    default List<String> getArray(int id) {
        return new ArrayList<String>();
    }

    void showProgressDialog(int msgResId);

    void showProgressDialog(int msgResId, boolean isCancellable);

    void handleNoInternetConnection();

    void forceDismissProgressDialog();

    String getSharedPrefStringValue(String key);

    String getSharedPrefStringValue(String key, String defaultVal);

    int getSharedPrefIntValue(String key);

    int getSharedPrefIntValue(String key, int defaultVal);

    void putSharedPrefIntValue(String key, int val);

    void putSharedPrefStringValue(String key, String val);

}
