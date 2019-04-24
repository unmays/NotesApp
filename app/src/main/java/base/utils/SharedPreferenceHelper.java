package base.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {

    private static SharedPreferenceHelper mInstance;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    private SharedPreferenceHelper(Context context) {
        pref = context.getSharedPreferences("NotesApp", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public static SharedPreferenceHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferenceHelper(context);
        }
        return mInstance;
    }

    public void putString(String key, String val) {
        editor.putString(key, val);
        editor.commit();
    }

    public String getString(String key, String defaultVal) {
        return pref.getString(key, defaultVal);
    }

    public void putInt(String key, int val) {
        editor.putInt(key, val);
        editor.commit();
    }

    public int getInt(String key, int defaultVal) {
        return pref.getInt(key, defaultVal);
    }

}
