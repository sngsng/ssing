package com.slogup.sgcore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.slogup.sgcore.network.social.SocialClientHelper;

public class CoreActivity extends AppCompatActivity {


    protected SocialClientHelper mSocialClientHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (mSocialClientHelper != null) {

            mSocialClientHelper.onActivityResult(requestCode, resultCode, data);
            mSocialClientHelper = null;
        }

    }


    // 소셜 로그인
    protected void loginWithSocial(SocialClientHelper socialClientHelper, SocialClientHelper.SocialResponseCallback responseCallback) {

        mSocialClientHelper = socialClientHelper;
        mSocialClientHelper.login(this, responseCallback);
    }
}
