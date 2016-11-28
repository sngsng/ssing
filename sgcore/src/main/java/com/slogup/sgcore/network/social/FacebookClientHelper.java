package com.slogup.sgcore.network.social;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.slogup.sgcore.manager.CoreManager;
import com.slogup.sgcore.model.Meta;

import java.util.Arrays;

/**
 * Created by sngjoong on 2016. 11. 26..
 */

public class FacebookClientHelper extends SocialClientHelper {

    private static final String LOG_TAG = FacebookClientHelper.class.getSimpleName();
    private static final String FB_PERMISSION_PROFILE = "public_profile";
    private static final String FB_PERMISSION_EMAIL = "email";
    private CallbackManager mCallbackManager;

    public FacebookClientHelper() {

        mCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void login(Activity activity, final SocialResponseCallback socialClientCallback) {

        if (!FacebookSdk.isInitialized()) FacebookSdk.sdkInitialize(activity.getApplicationContext());

        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(activity, Arrays.asList(FB_PERMISSION_PROFILE, FB_PERMISSION_EMAIL));
        loginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                String accessToken = loginResult.getAccessToken().getToken();
                String pid = loginResult.getAccessToken().getUserId();
                Log.i(LOG_TAG, "onSuccess : " + "pid : " + pid + "  accessToken : " + accessToken);
                socialClientCallback.onSuccess(pid, accessToken);
            }

            @Override
            public void onCancel() {

                socialClientCallback.onCancel();
            }

            @Override
            public void onError(FacebookException error) {

                socialClientCallback.onError(error.getLocalizedMessage());
            }
        });

    }


    @Override
    public String getProvider() {

        Meta meta = CoreManager.getInstance().getMeta();
        if (meta != null) return meta.getProviderFacebook();
        else return "facebook";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        mCallbackManager.onActivityResult(requestCode,resultCode, data);
    }
}
