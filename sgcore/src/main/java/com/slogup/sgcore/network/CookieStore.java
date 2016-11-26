package com.slogup.sgcore.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.slogup.sgcore.CoreAPIContants;

/**
 * Created by sngjoong on 16. 8. 25..
 */
public class CookieStore {

    private static Context sContext;
    private static CookieStore sCookieStore;

    public CookieStore() {
    }

    public static CookieStore getInstance(Context context) {

        sContext = context;
        if (sCookieStore == null) {

            sCookieStore = new CookieStore();
        }
        return sCookieStore;
    }

    public boolean hasCookie() {

        return !getCookie().isEmpty();

    }

    public void setCookie(String cookie) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);
        sharedPreferences.edit().putString(CoreAPIContants.RootUrl, cookie).apply();
    }

    public void removeCookie() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);
        sharedPreferences.edit().remove(CoreAPIContants.RootUrl).apply();
    }

    public String getCookie() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);
        return sharedPreferences.getString(CoreAPIContants.RootUrl, "");
    }
}
