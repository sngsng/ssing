package com.slogup.sgcore.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.slogup.sgcore.CoreAPIContants;

/**
 * Created by sngjoong on 16. 8. 25..
 */
public class CookieStore {


    private static CookieStore sCookieStore = new CookieStore();


    public static CookieStore getInstance() {

        return sCookieStore;
    }

    public boolean hasCookie(Context context) {

        return !getCookie(context).isEmpty();

    }

    public void setCookie(Context context, String cookie) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(CoreAPIContants.RootUrl, cookie).apply();
    }

    public void removeCookie(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().remove(CoreAPIContants.RootUrl).apply();
    }

    public String getCookie(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(CoreAPIContants.RootUrl, "");
    }
}
