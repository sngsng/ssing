package slogup.ssing.Model;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import slogup.ssing.SsingAPIMeta;

/**
 * Created by sngjoong on 2016. 11. 27..
 */



public class Post extends ListModel{


    private static final String LOG_TAG = Post.class.getSimpleName();
    private int mPostId;
    private int mAuthorId;
    private String mPostBody;

    private String mAuthorNickName;
    private String mAuthorGender;
    private String mAuthorBirth;
    private int mBirthStart;
    private int mBirthEnd;
    private int mTotalVoteCount;

    public Post(JSONObject jsonObject) {

        try {
            mPostId = jsonObject.getInt(SsingAPIMeta.Posts.Response.ID);
            mAuthorId = jsonObject.getInt(SsingAPIMeta.Posts.Response.AUTHOR_ID);
            mPostBody = jsonObject.getString(SsingAPIMeta.Posts.Response.BODY);

            mCreatedTime = jsonObject.getLong(SsingAPIMeta.Posts.Response.CREATED_AT);
            mUpdatedTime = jsonObject.getLong(SsingAPIMeta.Posts.Response.UPDATED_AT);

            mTotalVoteCount = jsonObject.getInt(SsingAPIMeta.Posts.Response.TOTAL_COUNT);

            JSONObject authorJson = jsonObject.getJSONObject(SsingAPIMeta.Posts.Response.AUTHOR);
            mAuthorNickName = authorJson.getString(SsingAPIMeta.Posts.Response.NICK);
            mAuthorGender = authorJson.getString(SsingAPIMeta.Posts.Response.GENDER);
            mAuthorBirth = authorJson.getString(SsingAPIMeta.Posts.Response.BIRTH);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void findOne() {

    }

    public static void findAll(Context context, @Nullable PostSearchFilter searchFilter, final RestClient.RestListener listener) {

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

                listener.onBefore();
            }

            @Override
            public void onSuccess(Object response) {

                Log.i(LOG_TAG, response.toString());
                ArrayList<Post> posts = new ArrayList<>();

                if (response instanceof JSONObject) {

                    try {
                        JSONArray rows = ((JSONObject) response).getJSONArray(SsingAPIMeta.Posts.Response.ROWS);

                        for (int i = 0; i < rows.length(); i++) {

                            JSONObject row = rows.getJSONObject(i);
                            Post post = new Post(row);
                            posts.add(post);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.onSuccess(posts);
            }

            @Override
            public void onFail(CoreError error) {

                if (error.statusCode == 404) {

                    listener.onSuccess(new ArrayList<Post>());
                }
                else {

                    listener.onFail(error);
                }

            }

            @Override
            public void onError(CoreError error) {

                listener.onError(error);
            }
        });
    }

    public static void addPost() {

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
        return mAuthorGender;
    }

    public void setGender(String gender) {
        mAuthorGender = gender;
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

    public String getAuthorGender() {
        return mAuthorGender;
    }

    public void setAuthorGender(String authorGender) {
        mAuthorGender = authorGender;
    }

    public String getAuthorBirth() {
        return mAuthorBirth;
    }

    public void setAuthorBirth(String authorBirth) {
        mAuthorBirth = authorBirth;
    }

    public int getTotalVoteCount() {
        return mTotalVoteCount;
    }

    public void setTotalVoteCount(int totalVoteCount) {
        mTotalVoteCount = totalVoteCount;
    }


}
