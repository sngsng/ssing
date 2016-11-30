package com.slogup.sgcore.network.social;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.util.exception.KakaoException;
import com.slogup.sgcore.CoreAPIContants;
import com.slogup.sgcore.R;
import com.slogup.sgcore.manager.CoreManager;
import com.slogup.sgcore.model.Meta;

/**
 * Created by sngjoong on 2016. 11. 29..
 */

public class KakaoClientHelper extends SocialClientHelper {

    private static final String LOG_TAG = KakaoClientHelper.class.getSimpleName();
    private ISessionCallback mISessionCallback;

    public KakaoClientHelper() {
    }

    @Override
    public void login(final Activity activity, final SocialResponseCallback socialClientCallback) {

        mISessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {

                Log.i(LOG_TAG, "onSessionOpened : ");

                AuthService.requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {

                        clearCallback();
                        String errMsg = activity.getString(R.string.err_unexpected);
                        if (errorResult != null) errMsg = errorResult.getErrorMessage();
                        socialClientCallback.onError(errMsg);
                    }

                    @Override
                    public void onNotSignedUp() {

                        // 이콜백은 실행되지않음
                    }

                    @Override
                    public void onSuccess(AccessTokenInfoResponse result) {

                        clearCallback();
                        Session session = Session.getCurrentSession();

                        if (session != null) {

                            String accessToken = session.getAccessToken(); //AccessToken
                            String refreshToken = session.getRefreshToken(); //RefreshToken
                            long pid = result.getUserId();
                            String appKey = session.getAppKey();

                            socialClientCallback.onSuccess(Long.toString(pid), accessToken);

                            Log.i(LOG_TAG,
                                    "onSuccess : " +
                                            "userID : " + pid  + ", " +
                                            "accessToken : " + accessToken + ", " +
                                            "refreshToken : " + refreshToken + ", " +
                                            "appKey : " + appKey + ", " +
                                            result.getExpiresInMillis() / 1000 / 60 + "분 후 토큰만료");
                        }
                    }
                });

            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {

                clearCallback();
                String errMsg = activity.getString(R.string.err_unexpected);
                if (exception != null)  exception.getMessage();

                Log.i(LOG_TAG, "onSessionOpenFailed : " + exception);
                socialClientCallback.onError(errMsg);
            }
        };

        Session.getCurrentSession().open(AuthType.KAKAO_TALK, activity);
        Session.getCurrentSession().addCallback(mISessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    @Override
    public String getProvider() {

        Meta meta = CoreManager.getInstance().getMeta();

        if (meta != null) return meta.getProviderKakao();
        return "kakao";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {

            return;
        }
    }

    private void clearCallback() {

        Session.getCurrentSession().removeCallback(mISessionCallback);
    }
}
