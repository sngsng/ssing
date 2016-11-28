package com.slogup.sgcore.network.core;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.slogup.sgcore.CoreAPIContants;
import com.slogup.sgcore.manager.AccountManager;
import com.slogup.sgcore.network.CookieStore;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;
import com.slogup.sgcore.network.social.SocialClientHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sngjoong on 2016. 11. 24..
 */

public class SessionClientHelper {

    public interface SocialCallbackListener {

        void onBefore();
        void onSuccessLogin(Object response);
        void onFailLogin(String pid, String accessKey);
        void onCancel();
        void onError(CoreError error);
    }

    private static final String LOG_TAG = SessionClientHelper.class.getSimpleName();

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

                if (response instanceof JSONObject) {

                    JSONObject resJson = (JSONObject) response;
                    AccountManager.getInstance().createUser(resJson);
                }

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
    public static void logout(final Context context, final RestClient.RestListener listener) {

        RestClient restService = new RestClient(context);
        restService.request(RestClient.Method.DELETE, CoreAPIContants.Session.URL, null, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                listener.onBefore();
            }

            @Override
            public void onSuccess(Object response) {

                CookieStore.getInstance(context).removeCookie();
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

    // 소셜 로그인
    public static void loginWithSocial(final Activity activity, final SocialClientHelper socialClientHelper, final SocialCallbackListener listener) {

        listener.onBefore();

        socialClientHelper.login(activity, new SocialClientHelper.SocialResponseCallback() {
            @Override
            public void onSuccess(final String pid, final String accessToken) {


                JSONObject params = new JSONObject();
                try {
                    params.put(CoreAPIContants.SocialSesstion.Request.PROVIDER, socialClientHelper.getProvider());
                    params.put(CoreAPIContants.SocialSesstion.Request.PID, pid);
                    params.put(CoreAPIContants.SocialSesstion.Request.ACCESS_TOKEN, accessToken);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RestClient restClient = new RestClient(activity);
                restClient.request(RestClient.Method.POST, CoreAPIContants.SocialSesstion.URL, params, new RestClient.RestListener() {
                    @Override
                    public void onBefore() {

                    }

                    @Override
                    public void onSuccess(Object response) {

                        Log.i(LOG_TAG, response.toString());
                        if (response instanceof JSONObject) {

                            JSONObject resJson = (JSONObject) response;
                            AccountManager.getInstance().createUser(resJson);
                        }

                        listener.onSuccessLogin(response);
                    }

                    @Override
                    public void onFail(CoreError error) {

                        Log.i(LOG_TAG, "onFail : " + error.errorMsg);
                        listener.onFailLogin(pid, accessToken);
                    }

                    @Override
                    public void onError(CoreError error) {

                        Log.i(LOG_TAG, "onError : " + error.errorMsg);
                        listener.onError(error);
                    }
                });
            }

            @Override
            public void onCancel() {

                listener.onCancel();

            }

            @Override
            public void onError(String errorMsg) {

                CoreError error = new CoreError(-1, errorMsg);
                listener.onError(error);
            }
        });

    }
}
