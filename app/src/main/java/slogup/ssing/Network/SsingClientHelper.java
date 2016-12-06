package slogup.ssing.Network;

import android.content.Context;
import android.util.Log;

import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sngjoong on 2016. 12. 7..
 */

public class SsingClientHelper {

    private static final String LOG_TAG = SsingClientHelper.class.getSimpleName();

    public static void vote(Context context, int postId, String tagName, final RestClient.RestListener listener) {

        JSONObject params = new JSONObject();
        try {
            params.put(SsingAPIMeta.Vote.Request.POST_ID, postId);
            params.put(SsingAPIMeta.Vote.Request.TAG_NAME, tagName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestClient restClient = new RestClient(context);
        restClient.request(RestClient.Method.POST, SsingAPIMeta.Vote.URL, params, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                listener.onBefore();
            }

            @Override
            public void onSuccess(Object response) {

                Log.i(LOG_TAG, response.toString());
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
