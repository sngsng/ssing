package slogup.ssing.Model;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import slogup.ssing.Network.SsingAPIMeta;
import slogup.ssing.Util.CommonUtils;
import slogup.ssing.Util.SsingUtils;

/**
 * Created by sngjoong on 2016. 11. 27..
 */


public class Post implements Parcelable {

    private long mCreatedTime;
    private long mUpdatedTime;
    private long mDeletedTime;
    private static final String LOG_TAG = Post.class.getSimpleName();
    private int mId;
    private int mAuthorId;
    private String mPostBody;

    private String mAuthorNickName;
    private String mAuthorGender;
    private String mAuthorBirth;
    private int mBirthStart;
    private int mBirthEnd;
    private int mTotalVoteCount;

    private ArrayList<Tag> mTags;
    private ArrayList<Comment> mComments;



    public Post(Context context, JSONObject jsonObject) {

        try {
            mId = jsonObject.getInt(SsingAPIMeta.Posts.Response.ID);
            mAuthorId = jsonObject.getInt(SsingAPIMeta.Posts.Response.AUTHOR_ID);
            mPostBody = jsonObject.getString(SsingAPIMeta.Posts.Response.BODY);

            mCreatedTime = jsonObject.getLong(SsingAPIMeta.Posts.Response.CREATED_AT);
            mUpdatedTime = jsonObject.getLong(SsingAPIMeta.Posts.Response.UPDATED_AT);

            // Post GETS 응답때만 옴 (총 투표수)
            if (jsonObject.has(SsingAPIMeta.Posts.Response.TOTAL_COUNT)) {

                mTotalVoteCount = 0;

                if (!jsonObject.isNull(SsingAPIMeta.Posts.Response.TOTAL_COUNT)) {

                    mTotalVoteCount = jsonObject.getInt(SsingAPIMeta.Posts.Response.TOTAL_COUNT);
                }

            }


            JSONObject authorJson = jsonObject.getJSONObject(SsingAPIMeta.Posts.Response.AUTHOR);
            mAuthorNickName = authorJson.getString(SsingAPIMeta.Posts.Response.NICK);
            mAuthorGender = authorJson.getString(SsingAPIMeta.Posts.Response.GENDER);
            mAuthorBirth = authorJson.getString(SsingAPIMeta.Posts.Response.BIRTH);

            // POST GET 응답때만옴 (태그리스트)
            if (jsonObject.has(SsingAPIMeta.Posts.Response.TAG_COUNTS)) {

                JSONArray tagCountsJson = jsonObject.getJSONArray(SsingAPIMeta.Posts.Response.TAG_COUNTS);
                ArrayList<Tag> tags = new ArrayList<>();

                for (int i = 0; i < tagCountsJson.length(); i++) {

                    JSONObject tagJson = tagCountsJson.getJSONObject(i);
                    Tag tag = new Tag(tagJson);
                    tag.setBackgroundColor(SsingUtils.getTagBackgroundColor(context, i));
                    tags.add(tag);
                }

                mTags = tags;

            }

            // POST GET 응답때만옴 (댓글리스트)
            if (jsonObject.has(SsingAPIMeta.Posts.Response.COMMENTS)) {

                JSONArray commentsJson = jsonObject.getJSONArray(SsingAPIMeta.Posts.Response.COMMENTS);
                ArrayList<Comment> comments = new ArrayList<>();

                for (int i = 0; i < commentsJson.length(); i++) {

                    JSONObject commentJson = commentsJson.getJSONObject(i);
                    Comment comment = new Comment(context, commentJson);
                    comments.add(comment);
                }
                mComments = comments;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void findOne(final Context context, int postId, final RestClient.RestListener listener) {

        RestClient restClient = new RestClient(context);
        String url = SsingAPIMeta.Posts.URL + "/" + Integer.toString(postId);

        restClient.request(RestClient.Method.GET, url, null, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                listener.onBefore();
            }

            @Override
            public void onSuccess(Object response) {

                if (response instanceof JSONObject) {

                    JSONObject resJson = (JSONObject) response;
                    Post post = new Post(context, resJson);
                    listener.onSuccess(post);
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


    public static void findAll(final Context context, @Nullable PostSearchFilter searchFilter, final RestClient.RestListener listener) {

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

                        String valueString = (String) value;
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
                            Post post = new Post(context, row);
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
                } else {

                    listener.onFail(error);
                }

            }

            @Override
            public void onError(CoreError error) {

                listener.onError(error);
            }
        });
    }

    public static void addOne(final Context context, String postBody, @Nullable ArrayList<String>tags, @Nullable ArrayList<String>images, final RestClient.RestListener listener) {

        final JSONObject params = new JSONObject();

        Log.i(LOG_TAG, "Body : " + postBody);
        if (tags != null) Log.i(LOG_TAG, "Tags : " + tags.toString());
        if (images!= null) Log.i(LOG_TAG, "ImageIds : " + images.toString());


        try {
            params.put(SsingAPIMeta.Posts.Request.BODY, postBody);

            if (tags != null && !tags.isEmpty())
                params.put(SsingAPIMeta.Posts.Request.TAGS, CommonUtils.arrayParamToString(tags));

            if (images != null && !images.isEmpty())
                params.put(SsingAPIMeta.Posts.Request.IMAGE_IDS, CommonUtils.arrayParamToString(images));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestClient restClient = new RestClient(context);
        restClient.request(RestClient.Method.POST, SsingAPIMeta.Posts.URL, params, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                listener.onBefore();
            }

            @Override
            public void onSuccess(Object response) {

                Log.i(LOG_TAG, "AddPost : " + response.toString());

                if (response instanceof JSONObject) {

                    Post post = new Post(context, (JSONObject) response);
                    listener.onSuccess(post);
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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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

    public long getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(long createdTime) {
        mCreatedTime = createdTime;
    }

    public long getUpdatedTime() {
        return mUpdatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        mUpdatedTime = updatedTime;
    }

    public long getDeletedTime() {
        return mDeletedTime;
    }

    public void setDeletedTime(long deletedTime) {
        mDeletedTime = deletedTime;
    }

    public ArrayList<Tag> getTags() {
        return mTags;
    }

    public void setTags(ArrayList<Tag> tags) {
        mTags = tags;
    }

    public ArrayList<Comment> getComments() {
        return mComments;
    }

    public void setComments(ArrayList<Comment> comments) {
        mComments = comments;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mCreatedTime);
        dest.writeLong(this.mUpdatedTime);
        dest.writeLong(this.mDeletedTime);
        dest.writeInt(this.mId);
        dest.writeInt(this.mAuthorId);
        dest.writeString(this.mPostBody);
        dest.writeString(this.mAuthorNickName);
        dest.writeString(this.mAuthorGender);
        dest.writeString(this.mAuthorBirth);
        dest.writeInt(this.mBirthStart);
        dest.writeInt(this.mBirthEnd);
        dest.writeInt(this.mTotalVoteCount);
        dest.writeTypedList(this.mTags);
        dest.writeTypedList(this.mComments);
    }

    protected Post(Parcel in) {
        this.mCreatedTime = in.readLong();
        this.mUpdatedTime = in.readLong();
        this.mDeletedTime = in.readLong();
        this.mId = in.readInt();
        this.mAuthorId = in.readInt();
        this.mPostBody = in.readString();
        this.mAuthorNickName = in.readString();
        this.mAuthorGender = in.readString();
        this.mAuthorBirth = in.readString();
        this.mBirthStart = in.readInt();
        this.mBirthEnd = in.readInt();
        this.mTotalVoteCount = in.readInt();
        this.mTags = in.createTypedArrayList(Tag.CREATOR);
        this.mComments = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
