package com.slogup.sgcore.network.social;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by sngjoong on 2016. 11. 26..
 */

public abstract class SocialClientHelper {


    public interface SocialResponseCallback {

        void onSuccess(String pid, String accessToken);
        void onCancel();
        void onError(String errorMsg);
    }

    public abstract void login(Activity activity, SocialResponseCallback socialClientCallback);
    public abstract String getProvider();
    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);
}
