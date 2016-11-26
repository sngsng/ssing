package com.slogup.sgcore.network.core;

import android.content.Context;
import android.net.Uri;

import com.slogup.sgcore.CoreAPIContants;
import com.slogup.sgcore.manager.CoreManager;
import com.slogup.sgcore.model.Meta;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sngjoong on 2016. 11. 23..
 */

public class AccountClientHelper {


    public enum UniqueType {

        ID,
        PhoneNum,
        NickName,
        Email
    }

    public enum SenderPhoneType {

        Adding,
        Change,
        FindPassword,
        FindID,
        Login,
        SignUp
    }

    public enum AuthPhoneType {

        Adding,
        Change,
        FindPassword,
        FindID,
    }


    public static void requestGETUnique(Context context, UniqueType uniqueType, String value, final RestClient.RestListener listener) {

        String key;
        Meta meta = CoreManager.getInstance().getMeta();
        switch (uniqueType) {

            case Email:
                key = meta.getEmail();
                break;
            case PhoneNum:
                key = meta.getPhoneNum();
                break;
            case NickName:
                key = meta.getNick();
                break;
            case ID:
                key = CoreAPIContants.Unique.GET.Value.AID;
                break;
            default:
                key = CoreAPIContants.Unique.GET.Value.AID;

        }

        String url = Uri.parse(CoreAPIContants.Unique.URL).buildUpon()
                .appendQueryParameter(CoreAPIContants.Unique.GET.Key.KEY, key)
                .appendQueryParameter(CoreAPIContants.Unique.GET.Key.VALUE, value).build().toString();

        RestClient restService = new RestClient(context);
        restService.request(RestClient.Method.GET, url, null, new RestClient.RestListener() {
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

    // 휴대폰 인증번호 요청
    public static void requestPOSTSenderPhone(Context context, SenderPhoneType senderPhoneType, String phoneNum, final RestClient.RestListener listener) {

        String type;

        switch (senderPhoneType) {

            case SignUp:
                type = CoreAPIContants.SenderPhone.POST.Value.PHONE_SIGNUP;
                break;
            case FindPassword:
                type = CoreAPIContants.SenderPhone.POST.Value.PHONE_FIND_PASS;
                break;
            case FindID:
                type = CoreAPIContants.SenderPhone.POST.Value.PHONE_FIND_ID;
                break;
            case Adding:
                type = CoreAPIContants.SenderPhone.POST.Value.PHONE_ADDING;
                break;
            case Login:
                type = CoreAPIContants.SenderPhone.POST.Value.PHONE_LOGIN;
                break;
            case Change:
                type = CoreAPIContants.SenderPhone.POST.Value.PHONE_CHANGE;
                break;
            default:
                type = CoreAPIContants.SenderPhone.POST.Value.PHONE_SIGNUP;
        }

        JSONObject params = new JSONObject();

        try {
            params.put(CoreAPIContants.SenderPhone.POST.Key.TYPE, type);
            params.put(CoreAPIContants.SenderPhone.POST.Key.PHONE_NUM, phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestClient restService = new RestClient(context);
        restService.request(RestClient.Method.POST, CoreAPIContants.SenderPhone.URL, params, new RestClient.RestListener() {
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

    // 인증번호 확인
    public static void requestPOSTAuthPhone(Context context, AuthPhoneType authPhoneType, String authNum, final RestClient.RestListener listener) {

        String type;

        switch (authPhoneType) {

            case Adding:
                type = CoreAPIContants.AuthPhone.POST.Value.PHONE_ADDING;
                break;
            case Change:
                type = CoreAPIContants.AuthPhone.POST.Value.PHONE_CHANGE;
                break;
            case FindPassword:
                type = CoreAPIContants.AuthPhone.POST.Value.PHONE_FIND_PASS;
                break;
            case FindID:
                type = CoreAPIContants.AuthPhone.POST.Value.PHONE_FIND_ID;
                break;
            default:
                type = CoreAPIContants.AuthPhone.POST.Value.PHONE_ADDING;
        }

        JSONObject params = new JSONObject();

        try {
            params.put(CoreAPIContants.AuthPhone.POST.Key.TYPE, type);
            params.put(CoreAPIContants.AuthPhone.POST.Key.TOKEN, authNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestClient restService = new RestClient(context);
        restService.request(RestClient.Method.POST, CoreAPIContants.AuthPhone.URL, params, new RestClient.RestListener() {
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
