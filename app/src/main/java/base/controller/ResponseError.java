package base.controller;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by unmay.shroff91257 on 9/26/2018.
 */

public class ResponseError implements Parcelable {

    private int statusCode;
    private int stringResId;
    private boolean hasException;
    private JSONObject responseBody;
    private boolean networkConnectionError;

    public ResponseError() {
        hasException = false;
        networkConnectionError = false;
    }

    protected ResponseError(Parcel in) {
        statusCode = in.readInt();
        stringResId = in.readInt();
        hasException = in.readByte() != 0;
        networkConnectionError = in.readByte() != 0;
    }

    public static final Creator<ResponseError> CREATOR = new Creator<ResponseError>() {
        @Override
        public ResponseError createFromParcel(Parcel in) {
            return new ResponseError(in);
        }

        @Override
        public ResponseError[] newArray(int size) {
            return new ResponseError[size];
        }
    };

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStringResId() {
        return stringResId;
    }

    public void setStringResId(int stringResId) {
        this.stringResId = stringResId;
    }

    public boolean hasException() {
        return hasException;
    }

    public void setHasException(boolean hasException) {
        this.hasException = hasException;
    }

    public JSONObject getRawResponseBody() {
        return responseBody;
    }

    public void setRawResponseBody(JSONObject responseBody) {
        this.responseBody = responseBody;
    }

    public void setNetworkConnectionError(boolean networkConnectionError) {
        this.networkConnectionError = networkConnectionError;
    }

    public boolean isNetworkConnectionError() {
        return networkConnectionError;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(statusCode);
        dest.writeInt(stringResId);
        dest.writeByte((byte) (hasException ? 1 : 0));
        dest.writeByte((byte) (networkConnectionError ? 1 : 0));
    }

}
