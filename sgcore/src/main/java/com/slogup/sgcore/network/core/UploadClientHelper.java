package com.slogup.sgcore.network.core;

import android.content.Context;
import android.util.Log;

import com.slogup.sgcore.CoreAPIMeta;
import com.slogup.sgcore.model.ImageInfo;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sngjoong on 2016. 12. 8..
 */

public class UploadClientHelper {

    private static final String LOG_TAG = UploadClientHelper.class.getSimpleName();

    public static void uploadImages(Context context, String folder, ArrayList<File> files, final RestClient.RestListener listener) {

        JSONObject params = new JSONObject();

        try {
            params.put(CoreAPIMeta.Image.FOLDER, folder);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestClient restClient = new RestClient(context);

        restClient.requestUploadFiles(CoreAPIMeta.Image.URL, params, files, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                listener.onBefore();
            }

            @Override
            public void onSuccess(Object response) {

                if (response instanceof JSONObject) {

                    Log.i(LOG_TAG, response.toString());
                    JSONObject json = (JSONObject)response;

                    ArrayList<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
                    try {
                        JSONArray rows = json.getJSONArray(CoreAPIMeta.Image.IMAGES);
                        for (int i = 0; i < rows.length(); i++) {

                            JSONObject imageInfoJson = rows.getJSONObject(i);
                            ImageInfo imageInfo = new ImageInfo(imageInfoJson);
                            imageInfos.add(imageInfo);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listener.onSuccess(imageInfos);
                }
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
