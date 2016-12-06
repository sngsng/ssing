package slogup.ssing.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import slogup.ssing.Network.SsingAPIMeta;

/**
 * Created by sngjoong on 2016. 12. 4..
 */

public class Comment implements Parcelable {

    private int mAuthorId;
    private int mPostId;
    private String mBody;
    private int mId;
    private String mAuthorGender;
    private String mAuthorNickName;
    private ArrayList<Tag> mTags;
    private long mCreatedTime;
    private long mUpdatedTime;
    private long mDeletedTime;

    public Comment(JSONObject jsonObject) {


        try {

            // Post 상세 요청에 달린 댓글
            mId = jsonObject.getInt(SsingAPIMeta.Posts.Response.ID);
            mAuthorId = jsonObject.getInt(SsingAPIMeta.Posts.Response.AUTHOR_ID);
            mPostId = jsonObject.getInt(SsingAPIMeta.Posts.Response.POST_ID);

            if (jsonObject.getString(SsingAPIMeta.Posts.Response.BODY) != null) {

                mBody = jsonObject.getString(SsingAPIMeta.Posts.Response.BODY);
            }

            mCreatedTime = jsonObject.getLong(SsingAPIMeta.Posts.Response.CREATED_AT);
            mUpdatedTime = jsonObject.getLong(SsingAPIMeta.Posts.Response.UPDATED_AT);

            JSONObject authorJson = jsonObject.getJSONObject(SsingAPIMeta.Posts.Response.AUTHOR);

            mAuthorNickName = authorJson.getString(SsingAPIMeta.Posts.Response.NICK);
            mAuthorGender = authorJson.getString(SsingAPIMeta.Posts.Response.GENDER);

            JSONArray tagsJson = jsonObject.getJSONArray(SsingAPIMeta.Posts.Response.TAGS);
            ArrayList<Tag> tags = new ArrayList<>();
            for (int i = 0; i < tagsJson.length(); i++) {

                JSONObject tagJson = tagsJson.getJSONObject(i);
                Tag tag = new Tag(tagJson);
                tags.add(tag);
            }
            mTags = tags;


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getAuthorGender() {
        return mAuthorGender;
    }

    public void setAuthorGender(String authorGender) {
        mAuthorGender = authorGender;
    }

    public String getAuthorNickName() {
        return mAuthorNickName;
    }

    public void setAuthorNickName(String authorNickName) {
        mAuthorNickName = authorNickName;
    }

    public ArrayList<Tag> getTags() {
        return mTags;
    }

    public void setTags(ArrayList<Tag> tags) {
        mTags = tags;
    }

    public int getAuthorId() {
        return mAuthorId;
    }

    public void setAuthorId(int authorId) {
        mAuthorId = authorId;
    }

    public int getPostId() {
        return mPostId;
    }

    public void setPostId(int postId) {
        mPostId = postId;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mAuthorId);
        dest.writeInt(this.mPostId);
        dest.writeString(this.mBody);
        dest.writeInt(this.mId);
        dest.writeString(this.mAuthorGender);
        dest.writeString(this.mAuthorNickName);
        dest.writeTypedList(this.mTags);
        dest.writeLong(this.mCreatedTime);
        dest.writeLong(this.mUpdatedTime);
        dest.writeLong(this.mDeletedTime);
    }

    protected Comment(Parcel in) {
        this.mAuthorId = in.readInt();
        this.mPostId = in.readInt();
        this.mBody = in.readString();
        this.mId = in.readInt();
        this.mAuthorGender = in.readString();
        this.mAuthorNickName = in.readString();
        this.mTags = in.createTypedArrayList(Tag.CREATOR);
        this.mCreatedTime = in.readLong();
        this.mUpdatedTime = in.readLong();
        this.mDeletedTime = in.readLong();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
