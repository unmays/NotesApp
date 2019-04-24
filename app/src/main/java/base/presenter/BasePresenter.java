package base.presenter;

import base.controller.NetworkCallback;
import base.listeners.BaseListener;

public class BasePresenter<T extends BaseListener> implements NetworkCallback {

    /**
     * @param view A view which will attach
     */
    private T mView;

    public void attachView(T view) {
        mView = view;
    }

    /*This method  will use to detach current view before it get destroy*/
    public void detachView() {
        mView = null;
    }

    /* To check if view is attached*/
    public boolean isViewAttached() {
        return mView != null;
    }

    /*TO get refrence of current view for call backs*/
    public BaseListener getView() {
        return mView;
    }

    @Override
    public boolean hasInternetConnection() {
        return getView().hasInternetConnection();
    }

    @Override
    public void handleNoInternetConnection() {
        getView().handleNoInternetConnection();
    }
}
