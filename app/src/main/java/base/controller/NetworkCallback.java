package base.controller;

/**
 * Created by unmay.shroff91257 on 9/27/2018.
 */
public interface NetworkCallback {

    boolean hasInternetConnection();

    void handleNoInternetConnection();

}
