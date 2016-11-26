package com.slogup.sgcore.network;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.slogup.sgcore.CoreAPIContants;
import com.slogup.sgcore.R;
import com.slogup.sgcore.util.Utils;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sngjoong on 16. 8. 22..
 */
public class RestClient {

    private static final String LOG_TAG = RestClient.class.getSimpleName();
    private static final String SET_COOKIE = "set-cookie";
    private static final String COOKIE = "Cookie";
    private static final int TIME_OUT_MS = 5000;

    private Context mContext;

    public enum Method {

        GET,
        POST,
        PUT,
        DELETE
    }

    public interface RestListener {

        void onBefore();

        void onSuccess(Object response);

        void onFail(CoreError error);

        void onError(CoreError error);
    }

    public RestClient(Context context) {

        mContext = context;
    }

    public void requestUploadFiles(String subUrl, final JSONObject params, final ArrayList<File> files, final RestListener listener) {

        String url = CoreAPIContants.RootUrl + CoreAPIContants.RootUrlPostFix + subUrl;
        MultipartRequest multipartRequest = new MultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                checkError(error, listener);
            }
        }) {

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {


                Map<String, DataPart> dataMap = new HashMap<>();

                for (int i = 0; i < files.size(); i++) {

                    try {

                        File file = files.get(i);
                        byte[] content = FileUtils.readFileToByteArray(file);
                        dataMap.put("file" + i, new DataPart(file.getName(), content));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return dataMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                try {
                    return Utils.jsonToMap(params.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return makeHeaderFields();
            }


            @Override
            protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {

                saveCookie(response);
                return super.parseNetworkResponse(response);
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueManager.getInstance(mContext).getRequestQueue().add(multipartRequest);
    }

    public void request(Method method, String subUrl, @Nullable JSONObject params, final RestListener listener) {

        listener.onBefore();

        int volleyMethod = Request.Method.GET;

        switch (method) {

            case GET:
                volleyMethod = Request.Method.GET;
                break;
            case POST:
                volleyMethod = Request.Method.POST;
                break;
            case PUT:
                volleyMethod = Request.Method.PUT;
                break;
            case DELETE:
                volleyMethod = Request.Method.DELETE;
                break;
        }

        String url = CoreAPIContants.RootUrl + CoreAPIContants.RootUrlPostFix + subUrl;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(volleyMethod, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                listener.onSuccess(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                checkError(error, listener);
            }

        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return makeHeaderFields();
            }
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                saveCookie(response);

                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    Log.d(LOG_TAG, jsonString);
                    if (jsonString.isEmpty()) {

                        return Response.success(new JSONObject(),
                                HttpHeaderParser.parseCacheHeaders(response));
                    } else {

                        return Response.success(new JSONObject(jsonString),
                                HttpHeaderParser.parseCacheHeaders(response));
                    }

                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueManager.getInstance(mContext).getRequestQueue().add(jsonObjectRequest);
    }

    private void checkError(VolleyError error, RestListener listener) {

        // 서버에서 응답을 주는 에러
        if (error.networkResponse != null) {

            int statusCode = error.networkResponse.statusCode;

            // 클라이언트 에러
            if (statusCode >= 400 && statusCode < 500) {

                String jsonString = null;

                try {
                    jsonString = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
                }
                String errMsg = CoreError.getErrorMsg(mContext, statusCode, jsonString);
                CoreError coreErr = new CoreError(statusCode, errMsg);
                listener.onFail(coreErr);
                Log.d(LOG_TAG, "onErrorResponse : " + coreErr.errorMsg);
            }
            // 서버 에러
            else {

                CoreError coreErr = new CoreError(statusCode, mContext.getString(R.string.err_server));
                listener.onError(coreErr);
                Log.d(LOG_TAG, "onFailResponse : " + coreErr.errorMsg);
            }
        }
        // 아닐 경우
        else {

            String errMsg = "";
            if (error instanceof TimeoutError)
                errMsg = mContext.getString(R.string.err_timeout);
            else if (error instanceof NoConnectionError)
                errMsg = mContext.getString(R.string.err_no_connection);
            else if (error instanceof ServerError)
                errMsg = mContext.getString(R.string.err_server);
            else if (error instanceof NetworkError)
                errMsg = mContext.getString(R.string.err_network);
            else
                errMsg = mContext.getString(R.string.err_unexpected);

            CoreError coreErr = new CoreError(0, errMsg);
            listener.onError(coreErr);
        }
    }

    private Map<String, String> makeHeaderFields() {

        Map<String, String> reqHeaders = new HashMap<>();

        Log.d(LOG_TAG, "getHeaders");
        CookieStore cookieStore = CookieStore.getInstance(mContext);

        if (cookieStore.hasCookie()) {

            reqHeaders.put(COOKIE, cookieStore.getCookie());

            Log.d(LOG_TAG, "Request Cookie : " + cookieStore.getCookie());
        }

        return reqHeaders;
    }

    private void saveCookie(NetworkResponse response) {

        Map<String, String> respHeaders = response.headers;

        if (respHeaders.containsKey(SET_COOKIE)) {

            CookieStore.getInstance(mContext).setCookie(respHeaders.get(SET_COOKIE));
            Log.d(LOG_TAG, "쿠키 저장");
        }
    }

}
