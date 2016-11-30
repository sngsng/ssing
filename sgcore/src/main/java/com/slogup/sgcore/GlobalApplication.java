package com.slogup.sgcore;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.slogup.sgcore.network.social.KakaoSDKAdapter;

/**
 * Created by sngjoong on 2016. 11. 30..
 */

public class GlobalApplication extends Application {
    private static volatile GlobalApplication sInstance = null;
    private static volatile Activity sCurrentActivity;


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        KakaoSDK.init(new KakaoSDKAdapter());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sInstance = null;
    }

    public static GlobalApplication getGlobalApplicationContext() {

        if(sInstance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return sInstance;
    }

    public static Activity getCurrentActivity() {
        return sCurrentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {


        GlobalApplication.sCurrentActivity = currentActivity;
    }

}
