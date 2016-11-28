package com.slogup.sgcore.model;

import android.content.Context;
import android.support.annotation.Nullable;

import com.slogup.sgcore.CoreAPIContants;
import com.slogup.sgcore.manager.AccountManager;
import com.slogup.sgcore.manager.CoreManager;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;
import com.slogup.sgcore.network.core.SessionClientHelper;

import org.json.JSONArray;
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

    private String mAid;
    private String mEmail;
    private String mPhoneNum;
    private String mName;
    private String mNick;
    private String mRole;
    private String mGender;
    private JSONObject mBirth;
    private boolean mIsVerifiedEmail;
    private String mCountry;
    private String mLanguage;
    private boolean mIsReviewed;
    private boolean mIsAgreedEmail;
    private boolean mIsAgreedPhone;
    private long mAgreedTermsAt;
    private int mProfileId;
    private long mCreatedAt;
    private long mUpdatedAt;
    private long mDeletedAt;
    private long mPassUpdatedAt;
    private int mId;

    private JSONArray mProviders;
    private JSONArray mLoginHistories;
    private JSONArray mUserNotifications;
    private JSONArray mUserPublicNotifications;
    private JSONArray mUserImages;




    public User(Context context) {

        super(context);
    }

    public User() {

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

    public void addSocialUser(SocialProviderType socialProviderType, String requestToken, String accessToken, JSONObject optionalParams, RestClient.RestListener listener) {

        addUser(SignUpType.SOCIAL, socialProviderType, requestToken, accessToken, null, null, optionalParams, listener);
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

                        params.put(CoreAPIContants.User.AID, aid);
                        params.put(CoreAPIContants.User.APASSWORD, aPassword);
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
            params.put(CoreAPIContants.User.SIGNUP_TYPE, signUpTypeParam);

            if (providerParam != null)
                params.put(CoreAPIContants.User.PROVIDER, providerParam);

            params.put(CoreAPIContants.User.USER_ID, uid);
            params.put(CoreAPIContants.User.PASSWORD, password);

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
        restService.request(RestClient.Method.POST, CoreAPIContants.User.URL, params, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                listener.onBefore();
            }

            @Override
            public void onSuccess(Object response) {

                if (response instanceof JSONObject) {

                    JSONObject resJson = (JSONObject)response;
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


    public String getAid() {
        return mAid;
    }

    public void setAid(String aid) {
        mAid = aid;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhoneNum() {
        return mPhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        mPhoneNum = phoneNum;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNick() {
        return mNick;
    }

    public void setNick(String nick) {
        mNick = nick;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public JSONObject getBirth() {
        return mBirth;
    }

    public void setBirth(JSONObject birth) {
        mBirth = birth;
    }

    public boolean isVerifiedEmail() {
        return mIsVerifiedEmail;
    }

    public void setVerifiedEmail(boolean verifiedEmail) {
        mIsVerifiedEmail = verifiedEmail;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public boolean isReviewed() {
        return mIsReviewed;
    }

    public void setReviewed(boolean reviewed) {
        mIsReviewed = reviewed;
    }

    public boolean isAgreedEmail() {
        return mIsAgreedEmail;
    }

    public void setAgreedEmail(boolean agreedEmail) {
        mIsAgreedEmail = agreedEmail;
    }

    public boolean isAgreedPhone() {
        return mIsAgreedPhone;
    }

    public void setAgreedPhone(boolean agreedPhone) {
        mIsAgreedPhone = agreedPhone;
    }

    public long getAgreedTermsAt() {
        return mAgreedTermsAt;
    }

    public void setAgreedTermsAt(long agreedTermsAt) {
        mAgreedTermsAt = agreedTermsAt;
    }

    public int getProfileId() {
        return mProfileId;
    }

    public void setProfileId(int profileId) {
        mProfileId = profileId;
    }

    public long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(long createdAt) {
        mCreatedAt = createdAt;
    }

    public long getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public long getDeletedAt() {
        return mDeletedAt;
    }

    public void setDeletedAt(long deletedAt) {
        mDeletedAt = deletedAt;
    }

    public long getPassUpdatedAt() {
        return mPassUpdatedAt;
    }

    public void setPassUpdatedAt(long passUpdatedAt) {
        mPassUpdatedAt = passUpdatedAt;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public JSONArray getProviders() {
        return mProviders;
    }

    public void setProviders(JSONArray providers) {
        mProviders = providers;
    }

    public JSONArray getLoginHistories() {
        return mLoginHistories;
    }

    public void setLoginHistories(JSONArray loginHistories) {
        mLoginHistories = loginHistories;
    }

    public JSONArray getUserNotifications() {
        return mUserNotifications;
    }

    public void setUserNotifications(JSONArray userNotifications) {
        mUserNotifications = userNotifications;
    }

    public JSONArray getUserPublicNotifications() {
        return mUserPublicNotifications;
    }

    public void setUserPublicNotifications(JSONArray userPublicNotifications) {
        mUserPublicNotifications = userPublicNotifications;
    }

    public JSONArray getUserImages() {
        return mUserImages;
    }

    public void setUserImages(JSONArray userImages) {
        mUserImages = userImages;
    }
}
