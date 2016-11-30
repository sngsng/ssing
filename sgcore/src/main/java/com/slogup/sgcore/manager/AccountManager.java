package com.slogup.sgcore.manager;

import android.content.Context;

import com.slogup.sgcore.CoreAPIContants;
import com.slogup.sgcore.model.User;
import com.slogup.sgcore.network.CookieStore;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sngjoong on 2016. 11. 29..
 */

public class AccountManager {

    private static AccountManager sAccountManager = new AccountManager();

    private User mUser;

    public static AccountManager getInstance() {

        return sAccountManager;
    }

    public User getUser() {

        return mUser;
    }

    public void createUser(JSONObject jsonObject) {

        User user = new User();

        try {
            String aid = jsonObject.getString(CoreAPIContants.User.AID);
            if (aid != null) user.setAid(aid);

            String email = jsonObject.getString(CoreAPIContants.User.EMAIL);
            if (email != null) user.setEmail(email);

            String phoneNum = jsonObject.getString(CoreAPIContants.User.PHONE_NUM);
            if (phoneNum != null) user.setPhoneNum(phoneNum);

            String name = jsonObject.getString(CoreAPIContants.User.NAME);
            if(name != null) user.setName(name);

            String nick = jsonObject.getString(CoreAPIContants.User.NICK);
            if (nick != null) user.setNick(nick);

            //Todo: Role

            String gender = jsonObject.getString(CoreAPIContants.User.GENDER);
            if (gender != null) user.setGender(gender);

            //Todo: Birth

            boolean isVerifiedEmail = jsonObject.getBoolean(CoreAPIContants.User.IS_VERIFIED_EMAIL);
            user.setVerifiedEmail(isVerifiedEmail);

            String country = jsonObject.getString(CoreAPIContants.User.COUNTRY);
            if (country != null) user.setCountry(country);

            String language = jsonObject.getString(CoreAPIContants.User.LANGUAGE);
            if (language != null) user.setLanguage(language);

            boolean isReviewed = jsonObject.getBoolean(CoreAPIContants.User.IS_REVIEWED);
            user.setReviewed(isReviewed);

            boolean agreedEmail = jsonObject.getBoolean(CoreAPIContants.User.AGREED_EMAIL);
            user.setAgreedEmail(agreedEmail);

            boolean agreedPhoneNum = jsonObject.getBoolean(CoreAPIContants.User.AGREED_PHONE_NUM);
            user.setAgreedPhone(agreedPhoneNum);

            int profileId = jsonObject.getInt(CoreAPIContants.User.PROFILE_ID);
            user.setProfileId(profileId);

            int id = jsonObject.getInt(CoreAPIContants.User.ID);
            user.setId(id);

            long createdAt = jsonObject.getLong(CoreAPIContants.User.CREATED_AT);
            user.setCreatedAt(createdAt);

            //Todo DeletedAt

            long updatedAt = jsonObject.getLong(CoreAPIContants.User.UPDATED_AT);
            user.setUpdatedAt(updatedAt);

            long agreedTermsAt = jsonObject.getLong(CoreAPIContants.User.AGREED_TERMS_AT);
            user.setAgreedTermsAt(agreedTermsAt);

            long passUpdatedAt = jsonObject.getLong(CoreAPIContants.User.PASS_UPDATED_AT);
            user.setPassUpdatedAt(passUpdatedAt);

            //Todo Profile

            //Todo Providers

            //Todo LoginHistories

            //Todo UserNotifications

            //Todo UserPublicNotifiations

            //Todo UserImages



        } catch (JSONException e) {
            e.printStackTrace();
        }

        setUser(user);
    }


    public void deleteUser() {

        setUser(null);
    }

    public boolean isLoggedIn(Context context) {

        if (CookieStore.getInstance().hasCookie(context)) {

            return mUser != null;
        }
        else {

            return false;
        }
    }

    private void setUser(User user) {
        mUser = user;
    }

}
