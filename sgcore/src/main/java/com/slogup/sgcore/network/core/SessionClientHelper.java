package com.slogup.sgcore.network.core;

import com.slogup.sgcore.network.RestClient;

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
    public static void requestPOSTSession(CoreLoginType loginType, String userID, String password, JSONObject optionalParams, RestClient.RestListener listener) {

    }

    // 로그인된 유저 정보 억기
    public static void requestGETSession(RestClient.RestListener listener) {

    }

    // 로그인된 유저 정보 얻기 (최종 접속일 갱신)
    public static void requestPUTSession(RestClient.RestListener listener) {

    }

    // 로그아웃
    public static void requestDELETESession(RestClient.RestListener listener) {

    }
}
