package com.slogup.sgcore.network;

import android.content.Context;

import com.slogup.sgcore.R;
import com.slogup.sgcore.manager.CoreManager;
import com.slogup.sgcore.model.Meta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sngjoong on 16. 8. 22..
 */
public class CoreError {

    private static final String KEY_TRANSLATION = "translation";
    private static final String KEY_ERR_CODE = "code";
    private static final String KEY_KO = "ko";

    public int statusCode;
    public String errorMsg;

    public CoreError(int statusCode, String errMsg) {

        this.statusCode = statusCode;
        this.errorMsg = errMsg;
    }

    public static final String getErrorMsg(Context context, int statusCode, String jsonString) {

        Meta meta = CoreManager.getInstance().getMeta();
        String statusCodeString = String.format("%d", statusCode);

        // 메타데이터 체크
        if (meta != null) {

            // response body가 있음
            if (jsonString != null) {

                JSONObject jsonObject = null;

                try {

                    jsonObject = new JSONObject(jsonString);

                } catch (JSONException e) {
//                    e.printStackTrace();
                }

                if (jsonObject != null) {

                   return getResponseErrorMsg(meta, jsonObject, statusCodeString);

                } else {

                    // Array 확인
                    JSONArray jsonArray = null;
                    JSONObject firstJson = null;

                    try {
                        jsonArray = new JSONArray(jsonString);
                        if (jsonArray.length() > 0) firstJson = jsonArray.getJSONObject(0);

                    } catch (JSONException e) {
//                    e.printStackTrace();
                    }

                    if (firstJson != null)
                        return getResponseErrorMsg(meta, firstJson, statusCodeString);
                    else
                        return getStatusCodeErrorMsg(meta, statusCodeString);

                }
            }
            // response body가 없음
            else {

                return getStatusCodeErrorMsg(meta, statusCodeString);
            }

        }
        // 에러메시지를 조회할 메타데이터가 없음
        else {

            return context.getString(R.string.err_no_err_msg_info_reason_no_meta);
        }

    }

    private static String getStatusCodeErrorMsg(Meta meta, String statusCode) {

        String errMsg = "";

        try {

            if (meta.getCodes() != null) {

                JSONObject koCodes = meta.getCodes().getJSONObject(KEY_KO);

                if (koCodes != null && koCodes.has(statusCode))
                    errMsg = koCodes.getString(statusCode);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return errMsg;
    }

    private static String getResponseErrorMsg(Meta meta, JSONObject jsonObject, String statusCodeString) {

        // body에 translation이 있음
        if (jsonObject.has(KEY_TRANSLATION)) {


            try {
                return jsonObject.getString(KEY_TRANSLATION);

            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
        }
        // body에 custom code가 있음
        else if (jsonObject.has(KEY_ERR_CODE)) {


            try {
                return getStatusCodeErrorMsg(meta, jsonObject.getString(KEY_ERR_CODE));
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
        }
        else {

            return getStatusCodeErrorMsg(meta, statusCodeString);
        }
    }
}
