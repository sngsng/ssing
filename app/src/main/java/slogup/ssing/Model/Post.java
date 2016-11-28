package slogup.ssing.Model;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

import slogup.ssing.SsingAPIMeta;

/**
 * Created by sngjoong on 2016. 11. 27..
 */



public class Post extends ListModel{


    private int mPostId;
    private int mAuthorId;
    private String mAuthorNickName;
    private String mPostBody;
    private String mGender;
    private int mBirthStart;
    private int mBirthEnd;
    private int mTagCount;


    public Post(JSONObject jsonObject) {

    }

    public int getPostId() {
        return mPostId;
    }

    public void setPostId(int postId) {
        mPostId = postId;
    }

    public int getAuthorId() {
        return mAuthorId;
    }

    public void setAuthorId(int authorId) {
        mAuthorId = authorId;
    }

    public String getAuthorNickName() {
        return mAuthorNickName;
    }

    public void setAuthorNickName(String authorNickName) {
        mAuthorNickName = authorNickName;
    }

    public String getPostBody() {
        return mPostBody;
    }

    public void setPostBody(String postBody) {
        mPostBody = postBody;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public int getBirthStart() {
        return mBirthStart;
    }

    public void setBirthStart(int birthStart) {
        mBirthStart = birthStart;
    }

    public int getBirthEnd() {
        return mBirthEnd;
    }

    public void setBirthEnd(int birthEnd) {
        mBirthEnd = birthEnd;
    }

    public static void findOne() {

    }

    private static void findAll(Context context, @Nullable PostSearchFilter searchFilter) {


        RestClient restClient = new RestClient(context);

        String url;

        // 파라미터가 있을경우
        if (searchFilter != null) {

            JSONObject searchQueries = searchFilter.toJson();

            Uri.Builder uriBuilder = Uri.parse(SsingAPIMeta.Posts.URL).buildUpon();

            for (Iterator iterator = searchQueries.keys(); iterator.hasNext(); ) {

                String key = (String) iterator.next();
                try {
                    Object value = searchQueries.get(key);
                    if (value instanceof String) {

                        String valueString = (String)value;
                        uriBuilder.appendQueryParameter(key, valueString);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            url = uriBuilder.build().toString();
        }
        // 없을경우
        else {

            url = SsingAPIMeta.Posts.URL;
        }

        restClient.request(RestClient.Method.GET, url, null, new RestClient.RestListener() {
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

    public static void addPost() {

    }
}
