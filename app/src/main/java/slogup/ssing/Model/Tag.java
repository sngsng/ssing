package slogup.ssing.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import slogup.ssing.Network.SsingAPIMeta;

/**
 * Created by sngjoong on 2016. 12. 4..
 */

public class Tag implements Parcelable {

    private int mId;
    private int mCount;
    private int mPostId;
    private int mCommentId;
    private String mName;
    private long mCreatedTime;
    private long mUpdatedTime;
    private long mDeletedTime;


    public Tag(JSONObject jsonObject) {

        try {

            mId = jsonObject.getInt(SsingAPIMeta.Posts.Response.ID);

            // Post 에 붙은 태그일시
            if (jsonObject.has(SsingAPIMeta.Posts.Response.TAG_NAME))
                mName = jsonObject.getString(SsingAPIMeta.Posts.Response.TAG_NAME);

            if (jsonObject.has(SsingAPIMeta.Posts.Response.COUNT))
                mCount = jsonObject.getInt(SsingAPIMeta.Posts.Response.COUNT);

            if (jsonObject.has(SsingAPIMeta.Posts.Response.POST_ID))
                mPostId = jsonObject.getInt(SsingAPIMeta.Posts.Response.POST_ID);


            // 댓글에 붙은 태그일시
            if (jsonObject.has(SsingAPIMeta.Posts.Response.NAME))
                mName = jsonObject.getString(SsingAPIMeta.Posts.Response.NAME);

            if (jsonObject.has(SsingAPIMeta.Posts.Response.COMMENT_ID))
                mCommentId = jsonObject.getInt(SsingAPIMeta.Posts.Response.COMMENT_ID);

            if (jsonObject.has(SsingAPIMeta.Posts.Response.CREATED_AT))
                mCreatedTime = jsonObject.getLong(SsingAPIMeta.Posts.Response.CREATED_AT);

            if (jsonObject.has(SsingAPIMeta.Posts.Response.UPDATED_AT))
                mUpdatedTime = jsonObject.getLong(SsingAPIMeta.Posts.Response.UPDATED_AT);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCommentId() {
        return mCommentId;
    }

    public void setCommentId(int commentId) {
        mCommentId = commentId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getPostId() {
        return mPostId;
    }

    public void setPostId(int postId) {
        mPostId = postId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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

    public Tag(int id, int count, int postId, int commentId, String name, long createdTime, long updatedTime, long deletedTime) {
        mId = id;
        mCount = count;
        mPostId = postId;
        mCommentId = commentId;
        mName = name;
        mCreatedTime = createdTime;
        mUpdatedTime = updatedTime;
        mDeletedTime = deletedTime;
    }

    public long getDeletedTime() {

        return mDeletedTime;
    }

    public void setDeletedTime(long deletedTime) {
        mDeletedTime = deletedTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mCount);
        dest.writeInt(this.mPostId);
        dest.writeInt(this.mCommentId);
        dest.writeString(this.mName);
        dest.writeLong(this.mCreatedTime);
        dest.writeLong(this.mUpdatedTime);
        dest.writeLong(this.mDeletedTime);
    }

    protected Tag(Parcel in) {
        this.mId = in.readInt();
        this.mCount = in.readInt();
        this.mPostId = in.readInt();
        this.mCommentId = in.readInt();
        this.mName = in.readString();
        this.mCreatedTime = in.readLong();
        this.mUpdatedTime = in.readLong();
        this.mDeletedTime = in.readLong();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel source) {
            return new Tag(source);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };
}
