package com.slogup.sgcore.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sngjoong on 16. 8. 22..
 */
public class BaseModel {

    private Context mContext;
    private JSONObject mJson;

    public BaseModel(Context context) {

        mContext = context;
    }

    public BaseModel() {

    }

    protected void setJson(JSONObject json) {
        mJson = json;
    }

    protected JSONObject getJson() {
        return mJson;
    }

    protected Context getContext() {
        return mContext;
    }


}
