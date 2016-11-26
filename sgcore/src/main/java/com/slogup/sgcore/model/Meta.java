package com.slogup.sgcore.model;

import android.content.Context;

import com.slogup.sgcore.manager.CoreManager;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by sngjoong on 16. 8. 22..
 */
public class Meta extends BaseModel {

    public static final String KEY_STD = "std";

    public static final String KEY_CDN = "cdn";
    public static final String KEY_CDN_ROOT_URL = "rootUrl";
    public static final String KEY_CDN_STATICS_URL = "staticsUrl";

    public static final String KEY_USER = "user";
    public static final String KEY_USER_ENUM_JOIN_FIELD = "enumJoinField";
    public static final String VAL_USER_JOIN_FIELD_AID = "aid";
    public static final String VAL_USER_JOIN_FIELD_EMAIL = "email";
    public static final String VAL_USER_JOIN_FIELD_NICK = "nick";
    public static final String VAL_USER_JOIN_FIELD_GENDER = "gender";
    public static final String VAL_USER_JOIN_FIELD_BIRTH = "birth";

    public static final String KEY_USER_RECOMMENDER_CASH_AMOUNT = "recommenderCashAmount";
    public static final String KEY_USER_ENUM_PHONES = "enumPhones";
    public static final String KEY_USER_PHONE_IOS = "phoneIOS";
    public static final String KEY_USER_PHONE_ANDROID = "phoneAndroid";
    public static final String KEY_USER_ENUM_GENDERS = "enumGenders";
    public static final String KEY_USER_GENDER_MALE = "genderMale";
    public static final String KEY_USER_GENDER_FEMALE = "genderFemale";
    public static final String KEY_USER_ENUM_AUTH_TYPES = "enumAuthTypes";
    public static final String KEY_USER_ENUM_AUTH_PHONE_TYPES = "enumAuthPhoneTypes";
    public static final String KEY_USER_ENUM_AUTH_EMAIL_TYPES = "enumAuthEmailTypes";
    public static final String KEY_USER_AUTH_EMAIL_SIGNUP = "authEmailSignup";
    public static final String KEY_USER_AUTH_EMAIL_ADDING = "authEmailAdding";
    public static final String KEY_USER_AUTH_EMAIL_FIND_PASS = "authEmailFindPass";
    public static final String KEY_USER_AUTH_PHONE_SIGNUP = "authPhoneSignup";
    public static final String KEY_USER_AUTH_PHONE_ADDING = "authPhoneAdding";
    public static final String KEY_USER_AUTH_PHONE_FIND_PASS = "authPhoneFindPass";
    public static final String KEY_USER_AUTH_PHONE_LOGIN = "authPhoneLogin";
    public static final String KEY_USER_ENUM_SIGNUP_TYPES = "enumSignUpTypes";
    public static final String KEY_USER_SIGNUP_TYPE_EMAIL = "signUpTypeEmail";
    public static final String KEY_USER_SIGNUP_TYPE_PHONE = "signUpTypePhone";
    public static final String KEY_USER_SIGNUP_TYPE_PHONE_ID = "signUpTypePhoneId";
    public static final String KEY_USER_SIGNUP_TYPE_NORMAL_ID = "signUpTypeNormalId";
    public static final String KEY_USER_SIGNUP_TYPE_SOCIAL = "signUpTypeSocial";
    public static final String KEY_USER_DEFAULT_SIGNUP_TYPE = "defaultSignUpType";

    public static final String KEY_USER_ENUM_PROVIDERS = "enumProviders";
    public static final String KEY_USER_PROVIDER_FACEBOOK = "providerFacebook";
    public static final String KEY_USER_PROVIDER_TWITTER = "providerTwitter";
    public static final String KEY_USER_PROVIDER_GOOGLE = "providerGoogle";
    public static final String KEY_USER_PROVIDER_KAKAO = "providerKakao";
    public static final String KEY_USER_ENUM_DEVICE_TYPES = "enumDeviceTypes";
    public static final String KEY_USER_DEVICE_TYPE_IOS = "deviceTypeIOS";
    public static final String KEY_USER_DEVICE_TYPE_ANDROID = "deviceTypeAndroid";
    public static final String KEY_USER_ENUM_ROLES = "enumRoles";
    public static final String KEY_USER_ENUM_ASSIGN_ROLES = "enumAssignRoles";
    public static final String KEY_USER_ROLE_UNAUTHORIZED_USER = "roleUnauthorizedUser";
    public static final String KEY_USER_ROLE_USER = "roleUser";
    public static final String KEY_USER_ROLE_HEAVY_USER = "roleHeavyUser";
    public static final String KEY_USER_ROLE_ADMIN = "roleAdmin";
    public static final String KEY_USER_ROLE_SUPER_ADMIN = "roleSuperAdmin";
    public static final String KEY_USER_ROLE_ULTRA_ADMIN = "roleUltraAdmin";
    public static final String KEY_USER_ROLE_SUPER_ULTRA_ADMIN = "roleSuperUltraAdmin";
    public static final String KEY_USER_ROLE_SUPERVISOR = "roleSupervisor";

    public static final String KEY_USER_ENUM_LINK_ID_PASS_TYPES = "enumLinkIdPassTypes";
    public static final String KEY_USER_LINK_ID_PASS_EMAIL = "linkIdPassEmail";
    public static final String KEY_USER_LINK_ID_PASS_NORMAL = "linkIdPassNormal";

    public static final String KEY_USER_MIN_NICK_LENGTH = "minNickLength";
    public static final String KEY_USER_MAX_NICK_LENGTH = "maxNickLength";
    public static final String KEY_USER_MIN_NAME_LENGTH = "minNameLength";
    public static final String KEY_USER_MAX_NAME_LENGTH = "maxNameLength";
    public static final String KEY_USER_MIN_SECRET_LENGTH = "minSecretLength";
    public static final String KEY_USER_GOOD_SECRET_LENGTH = "goodSecretLength";
    public static final String KEY_USER_MAX_SECRET_LENGTH = "maxSecretLength";
    public static final String KEY_USER_EMAIL_TOKEN_LENGTH = "emailTokenLength";
    public static final String KEY_USER_PHONE_TOKEN_LENGTH = "phoneTokenLength";
    public static final String KEY_USER_MIN_PHONE_NUM_LENGTH = "minPhoneNumLength";
    public static final String KEY_USER_MAX_PHONE_NUM_LENGTH = "maxPhoneNumLength";
    public static final String KEY_USER_MIN_ID_LENGTH = "minIdLength";
    public static final String KEY_USER_MAX_ID_LENGTH = "maxIdLength";
    public static final String KEY_USER_EXPIRED_EMAIL_TOKEN_MINUTES = "expiredEmailTokenMinutes";
    public static final String KEY_USER_EXPIRED_PHONE_TOKEN_MINUTES = "expiredPhoneTokenMinutes";
    public static final String KEY_USER_MAX_COMMENT_LENGTH = "maxCommentLength";
    public static final String KEY_USER_MIN_COMMENT_LENGTH = "minCommentLength";

    public static final String KEY_USER_ENABLE_PROVIDER = "enableProvider";
    public static final String KEY_USER_DEFAULT_AGREED_EMAIL = "defaultAgreedEmail";
    public static final String KEY_USER_DEFAULT_AGREED_PHONE_NUM = "defaultAgreedPhoneNum";
    public static final String KEY_USER_ENUM_ORDERS = "enumOrders";
    public static final String KEY_USER_ORDER_CREATE = "orderCreate";
    public static final String KEY_USER_ORDER_UPDATE = "orderUpdate";
    public static final String KEY_USER_ENUM_SEARCH_FIELDS = "enumSearchFields";
    public static final String KEY_USER_NICK = "nick";
    public static final String KEY_USER_GENDER = "gender";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_PHONE_NUM = "phoneNum";
    public static final String KEY_USER_BIRTH = "birth";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_DEFAULT_PFIMG = "defaultPfImg";
    public static final String KEY_USER_DELETED_USER_STORING_DAY = "deletedUserStoringDay";


    public Meta(Context context) {

        super(context);
    }

    public static void findOne(final Context context, final RestClient.RestListener listener) {

        RestClient restService = new RestClient(context);
        restService.request(RestClient.Method.GET, "etc/meta", null, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                listener.onBefore();
            }

            @Override
            public void onSuccess(Object response) {

                if (response instanceof JSONObject) {

                    // 매니져에 메타데이터를 저장
                    JSONObject jsonResponse = (JSONObject) response;
                    Meta meta = new Meta(context);
                    meta.setJson(jsonResponse);
                    CoreManager.getInstance().setMeta(meta);
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

    // Getter

    public JSONObject getCodes() {

        try {

            return getJson().getJSONObject("codes");

        } catch (JSONException e) {

            e.printStackTrace();

            return null;
        }
    }

    private JSONObject getStandard() {

        try {

            return getJson().getJSONObject(KEY_STD);

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    private JSONObject getCdn() {

        try {

            if (getStandard() != null) return getStandard().getJSONObject(KEY_CDN);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    private JSONObject getUser() {

        try {

            if (getStandard() != null) return getStandard().getJSONObject(KEY_USER);
            else return null;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getCdnRootUrl() {

        try {

            if (getCdn() != null) return getCdn().getString(KEY_CDN_ROOT_URL);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getCdnStatisUrl() {

        try {

            if (getCdn() != null) return getCdn().getString(KEY_CDN_STATICS_URL);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumJoinField() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_JOIN_FIELD);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumPhones() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_PHONES);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getPhoneiOS() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_PHONE_IOS);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getPhoneAndroid() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_PHONE_ANDROID);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumGenders() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_GENDERS);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getGenderMale() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_GENDER_MALE);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getGenderFemale() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_GENDER_FEMALE);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumAuthTypes() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_AUTH_TYPES);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumAuthPhoneTypes() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_AUTH_PHONE_TYPES);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumAuthEmailTypes() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_AUTH_EMAIL_TYPES);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getAuthEmailSignUp() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_AUTH_EMAIL_SIGNUP);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getAuthEmailAdding() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_AUTH_EMAIL_ADDING);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getAuthEmailFindPass() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_AUTH_EMAIL_FIND_PASS);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getAuthPhoneSignUp() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_AUTH_PHONE_SIGNUP);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getAuthPhoneAdding() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_AUTH_PHONE_ADDING);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getAuthPhoneFindPass() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_AUTH_PHONE_FIND_PASS);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getAuthPhoneLogin() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_AUTH_PHONE_LOGIN);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumSignUpTypes() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_SIGNUP_TYPES);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getSignUpTypeEmail() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_SIGNUP_TYPE_EMAIL);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getSignUpTypePhone() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_SIGNUP_TYPE_PHONE);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getSignUpTypePhoneId() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_SIGNUP_TYPE_PHONE_ID);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getSignUpTypeNormalId() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_SIGNUP_TYPE_NORMAL_ID);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getSignUpTypeSocial() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_SIGNUP_TYPE_SOCIAL);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getDefaultSignUpType() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_DEFAULT_SIGNUP_TYPE);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumProviders() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_PROVIDERS);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getProviderFacebook() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_PROVIDER_FACEBOOK);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getProviderTwitter() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_PROVIDER_TWITTER);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getProviderGoogle() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_PROVIDER_GOOGLE);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getProviderKakao() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_PROVIDER_KAKAO);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumDeviceTypes() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_DEVICE_TYPES);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getDeviceTypeiOS() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_DEVICE_TYPE_IOS);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getDeviceTypeAndroid() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_DEVICE_TYPE_ANDROID);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumRoles() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_ROLES);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumAssignRoles() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_ASSIGN_ROLES);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getRoleUnauthorized() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_ROLE_UNAUTHORIZED_USER);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getRoleUser() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_ROLE_USER);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getRoleHeavyUser() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_ROLE_HEAVY_USER);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getRoleAdmin() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_ROLE_ADMIN);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getRoleSuperAdmin() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_ROLE_SUPER_ADMIN);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getRoleUltraAdmin() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_ROLE_ULTRA_ADMIN);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getRoleSuperUltraAdmin() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_ROLE_SUPER_ULTRA_ADMIN);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getRoleSupervisor() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_ROLE_SUPERVISOR);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumLinkPassTypes() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_LINK_ID_PASS_TYPES);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getLinkIdPassEmail() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_LINK_ID_PASS_EMAIL);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getLinkIdPassNormal() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_LINK_ID_PASS_NORMAL);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public int getMinNickLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MIN_NICK_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getMaxNickLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MAX_NICK_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getMinNameLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MIN_NAME_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getMaxNameLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MAX_NAME_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getMinSecretLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MIN_SECRET_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getGoodSecretLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_GOOD_SECRET_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getMaxSecretLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MAX_SECRET_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getEmailTokenLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_EMAIL_TOKEN_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getPhoneTokenLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_PHONE_TOKEN_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getMinPhoneNumLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MIN_PHONE_NUM_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getMaxPhoneNumLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MAX_PHONE_NUM_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getMinIDLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MIN_ID_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getMaxIDLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MAX_ID_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getExpiredEmailTokenMinutes() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_EXPIRED_EMAIL_TOKEN_MINUTES);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getExpiredPhoneTokenMinutes() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_EXPIRED_PHONE_TOKEN_MINUTES);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getMinCommentLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MIN_COMMENT_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public int getMaxCommentLength() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_MAX_COMMENT_LENGTH);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

    public JSONArray getEnableProvider() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENABLE_PROVIDER);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public boolean getDefaultAgreedEmail() {

        try {

            if (getUser() != null) return getUser().getBoolean(KEY_USER_DEFAULT_AGREED_EMAIL);
            else return false;

        } catch (JSONException e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean getDefaultAgreedPhoneNum() {

        try {

            if (getUser() != null) return getUser().getBoolean(KEY_USER_DEFAULT_AGREED_PHONE_NUM);
            else return false;

        } catch (JSONException e) {

            e.printStackTrace();
            return false;
        }
    }

    public JSONArray getEnumOrders() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_ORDERS);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getOrderCreate() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_ORDER_CREATE);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getOrderUpdate() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_ORDER_UPDATE);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getEnumSearchFields() {

        try {

            if (getUser() != null) return getUser().getJSONArray(KEY_USER_ENUM_SEARCH_FIELDS);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getNick() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_NICK);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getGender() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_GENDER);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getEmail() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_EMAIL);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getPhoneNum() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_PHONE_NUM);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getBirth() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_BIRTH);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getName() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_NAME);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getDefaultPfImg() {

        try {

            if (getUser() != null) return getUser().getString(KEY_USER_DEFAULT_PFIMG);
            else return null;

        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }
    }

    public int getDeletedUserStoringDay() {

        try {

            if (getUser() != null) return getUser().getInt(KEY_USER_DELETED_USER_STORING_DAY);
            else return 0;

        } catch (JSONException e) {

            e.printStackTrace();
            return 0;
        }
    }

}
