package base.controller;

import android.support.annotation.NonNull;

public interface ResponseListener<T> {

    void onSuccess(T responseBody);

    void onError(@NonNull ResponseError error);

}
