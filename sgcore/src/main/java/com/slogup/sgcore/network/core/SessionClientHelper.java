package com.slogup.sgcore.network.core;

import android.app.Activity;
import android.content.Context;

import com.slogup.sgcore.CoreAPIContants;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;
import com.slogup.sgcore.network.social.SocialClientHelper;

import org.json.JSONObject;

/**
 * Created by sngjoong on 2016. 11. 24..
 */

public class SessionClientHelper {

    public enum CoreLoginType {

        Email,
        Phone,
        PhoneID,
        NormalID,
        PhoneEmail
    }

    public enum SocialProviderType {

        FACEBOOK,
        TWITTER,
        GOOGLE,
        KAKAO,
        FABRIC
    }

    public enum SignUpType {

        EMAIL,
        PHONE,
        SOCIAL,
        PHONE_ID,
        NORMAL_ID
    }

    // 일반 로그인 (이메일, 휴대폰번호, 아이디, 휴대폰번호 + 아이디, 휴대폰번호 + 이메일)
    public static void login(CoreLoginType loginType, String userID, String password, JSONObject optionalParams, RestClient.RestListener listener) {

    }

    // 로그인된 유저 정보 억기
    public static void requestGETSession(RestClient.RestListener listener) {

    }

    // 로그인된 유저 정보 얻기 (최종 접속일 갱신)
    public static void checkLogin(Context context, final RestClient.RestListener listener) {

        RestClient restClient = new RestClient(context);
        restClient.request(RestClient.Method.PUT, CoreAPIContants.Session.URL, null, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                listener.onBefore();
            }

            @Override
            public void onSuccess(Object response) {

                listener.onSuccess(response);
            }

            @Override
            public void onFail(CoreError error) {

                listener.onFail(error);
            }

            @Override
            public void onError(CoreError error) {

                listener.onError(error);
            }
        });


    }

    // 로그아웃
    public static void logout(RestClient.RestListener listener) {

    }

    // 소셜 로그인
    public static void loginWithSocial(Activity activity, SocialClientHelper socialClientHelper, final RestClient.RestListener listener) {

        socialClientHelper.login(activity, new SocialClientHelper.SocialResponseCallback() {
            @Override
            public void onSuccess(String accessToken) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(String errorMsg) {

            }
        });

    }
}
