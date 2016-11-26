package com.slogup.sgcore.model;

import android.content.Context;
import android.support.annotation.Nullable;

import com.slogup.sgcore.CoreAPIContants;
import com.slogup.sgcore.manager.CoreManager;
import com.slogup.sgcore.network.CookieStore;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by sngjoong on 16. 8. 22..
 */
public class User extends BaseModel {

    public enum NormalLoginType {

        EMAIL,
        PHONE,
        PHONE_ID,
        ID,
    }

    public enum SocialProviderType {

        FACEBOOK,
        TWITTER,
        GOOGLE,
        KAKAO
    }

    public enum SignUpType {

        EMAIL,
        PHONE,
        SOCIAL,
        PHONE_ID,
        NORMAL_ID
    }


    public User(Context context) {

        super(context);
    }

    public static void findOne(Context context, final RestClient.RestListener listener) {

        RestClient restService = new RestClient(context);
        restService.request(RestClient.Method.GET, "accounts/users/1", null, new RestClient.RestListener() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onSuccess(Object response) {

            }

            @Override
            public void onFail(CoreError error) {

            }

            @Override
            public void onError(CoreError error) {

            }
        });
    }

    public static void findAll(Context context, final RestClient.RestListener listener) {

        RestClient restService = new RestClient(context);
        restService.request(RestClient.Method.GET, "accounts/users", null, new RestClient.RestListener() {
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

    public static void loadCurrentUserInfo(Context context, final RestClient.RestListener listener) {

        RestClient restService = new RestClient(context);
        restService.request(RestClient.Method.GET, "accounts/session", null, new RestClient.RestListener() {
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

    public static void normalLogin(Context context, NormalLoginType loginType, String userID, String password, @Nullable JSONObject optionalParams, final RestClient.RestListener listener) {

        Meta meta = CoreManager.getInstance().getMeta();

        JSONObject params = new JSONObject();
        String type = meta.getSignUpTypeEmail();

        switch (loginType) {

            case EMAIL:
                type = meta.getSignUpTypeEmail();
                break;
            case PHONE:
                type = meta.getSignUpTypePhone();
                break;
            case PHONE_ID:
                type = meta.getSignUpTypePhoneId();
                break;
            case ID:
                type = meta.getSignUpTypeNormalId();
                break;
        }

        try {

            params.put(CoreAPIContants.SessionParams.POST.LOGIN_TYPE, type);
            params.put(CoreAPIContants.SessionParams.POST.USER_ID, userID);
            params.put(CoreAPIContants.SessionParams.POST.PASSWORD, password);

            if (optionalParams != null) {

                for (Iterator iterator = optionalParams.keys(); iterator.hasNext(); ) {

                    String key = (String) iterator.next();
                    Object value = optionalParams.get(key);
                    params.put(key, value);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestClient restService = new RestClient(context);
        restService.request(RestClient.Method.POST, "accounts/session", params, new RestClient.RestListener() {
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

    public static void socialLogin(Context context, final RestClient.RestListener listener) {

    }

    public static void logout(final Context context, final RestClient.RestListener listener) {

        RestClient restService = new RestClient(context);
        restService.request(RestClient.Method.DELETE, "accounts/session", null, new RestClient.RestListener() {
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

    public void addNormalIDUser(String userID, String password, JSONObject optionalParams, RestClient.RestListener listener) {

        addUser(SignUpType.NORMAL_ID, null, userID, password, null, null, optionalParams, listener);
    }

    public void addEmailIDUser(String emailID, String password, JSONObject optionalParams, RestClient.RestListener listener) {

        addUser(SignUpType.EMAIL, null, emailID, password, null, null, optionalParams, listener);
    }

    public void addPhoneUser(String phoneNum, String authNum, JSONObject optionalParams,RestClient.RestListener listener) {

        addUser(SignUpType.PHONE, null, phoneNum, authNum, null, null, optionalParams, listener);
    }

    public void addPhoneWithIdUser(String phoneNum, String authNum, String userID, String password, JSONObject optionalParams, RestClient.RestListener listener) {

        addUser(SignUpType.PHONE_ID, null, phoneNum, authNum, userID, password, optionalParams, listener);
    }

    public void addFacebookUser(String requestToken, String accessToken, JSONObject optionalParams, RestClient.RestListener listener) {

        addUser(SignUpType.SOCIAL, SocialProviderType.FACEBOOK, requestToken, accessToken, null, null, optionalParams, listener);
    }

    public void addKakaoUser(String requestToken, String accessToken, JSONObject optionalParams, RestClient.RestListener listener) {

        addUser(SignUpType.SOCIAL, SocialProviderType.KAKAO, requestToken, accessToken, null, null, optionalParams, listener);
    }


    private void addUser(SignUpType signUpType, SocialProviderType providerType, String uid, String password, String aid, String aPassword, JSONObject optionalParams, final RestClient.RestListener listener) {

        JSONObject params = new JSONObject();

        Meta meta = CoreManager.getInstance().getMeta();
        String signUpTypeParam = meta.getDefaultSignUpType();
        String providerParam = null;

        switch (signUpType) {

            case EMAIL:
                signUpTypeParam = meta.getSignUpTypeEmail();
                break;

            case PHONE:
                signUpTypeParam = meta.getSignUpTypePhone();
                break;

            case SOCIAL: {

                signUpTypeParam = meta.getSignUpTypeSocial();

                if (providerType != null) {

                    switch (providerType) {

                        case FACEBOOK:
                            providerParam = meta.getProviderFacebook();
                            break;
                        case GOOGLE:
                            providerParam = meta.getProviderGoogle();
                            break;
                        case TWITTER:
                            providerParam = meta.getProviderTwitter();
                            break;
                        case KAKAO:
                            providerParam = meta.getProviderKakao();
                            break;
                    }
                }
                break;
            }
            case PHONE_ID:
                signUpTypeParam = meta.getSignUpTypePhoneId();
                try {
                    if (aid != null && aPassword != null) {

                        params.put(CoreAPIContants.UserParams.POST.AID, aid);
                        params.put(CoreAPIContants.UserParams.POST.APASSWORD, aPassword);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case NORMAL_ID:
                signUpTypeParam = meta.getSignUpTypeNormalId();
                break;
        }

        try {
            params.put(CoreAPIContants.UserParams.POST.SIGNUP_TYPE, signUpTypeParam);

            if (providerParam != null)
                params.put(CoreAPIContants.UserParams.POST.PROVIDER, providerParam);

            params.put(CoreAPIContants.UserParams.POST.USER_ID, uid);
            params.put(CoreAPIContants.UserParams.POST.PASSWORD, password);

            if (optionalParams != null) {

                for (Iterator iterator = optionalParams.keys(); iterator.hasNext(); ) {

                    String key = (String) iterator.next();
                    Object value = optionalParams.get(key);
                    params.put(key, value);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestClient restService = new RestClient(getContext());
        restService.request(RestClient.Method.POST, "accounts/users", params, new RestClient.RestListener() {
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
}
