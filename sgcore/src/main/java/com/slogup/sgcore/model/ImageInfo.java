package com.slogup.sgcore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.slogup.sgcore.CoreAPIMeta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sngjoong on 2016. 12. 8..
 */

public class ImageInfo implements Parcelable {

    private int mAuthorId;
    private String mFolder;
    private String mName;
    private boolean mIsAuthorized;
    private Long mCreatedAt;
    private Long mUpdatedAt;
    //Todo DeletedAt
    private int mId;


    public ImageInfo(JSONObject jsonObject) {

        try {
            mAuthorId = jsonObject.getInt(CoreAPIMeta.Image.AUTHOR_ID);
            mFolder = jsonObject.getString(CoreAPIMeta.Image.FOLDER);
            mName = jsonObject.getString(CoreAPIMeta.Image.NAME);
            mIsAuthorized = jsonObject.getBoolean(CoreAPIMeta.Image.AUTHORIZED);
            mCreatedAt = jsonObject.getLong(CoreAPIMeta.Image.CREATED_AT);
            mUpdatedAt = jsonObject.getLong(CoreAPIMeta.Image.UPDATED_AT);
            mId = jsonObject.getInt(CoreAPIMeta.Image.ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getImageUrl() {

        //Todo Meta로 수정해야함
        return CoreAPIMeta.RootUrl + "/" + "uploads" + "/" + mFolder + "/" + mName;
    }


    public int getAuthorId() {
        return mAuthorId;
    }

    public void setAuthorId(int authorId) {
        mAuthorId = authorId;
    }

    public String getFolder() {
        return mFolder;
    }

    public void setFolder(String folder) {
        mFolder = folder;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isAuthorized() {
        return mIsAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        mIsAuthorized = authorized;
    }

    public Long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Long createdAt) {
        mCreatedAt = createdAt;
    }

    public Long getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mAuthorId);
        dest.writeString(this.mFolder);
        dest.writeString(this.mName);
        dest.writeByte(this.mIsAuthorized ? (byte) 1 : (byte) 0);
        dest.writeValue(this.mCreatedAt);
        dest.writeValue(this.mUpdatedAt);
        dest.writeInt(this.mId);
    }

    protected ImageInfo(Parcel in) {
        this.mAuthorId = in.readInt();
        this.mFolder = in.readString();
        this.mName = in.readString();
        this.mIsAuthorized = in.readByte() != 0;
        this.mCreatedAt = (Long) in.readValue(Long.class.getClassLoader());
        this.mUpdatedAt = (Long) in.readValue(Long.class.getClassLoader());
        this.mId = in.readInt();
    }

    public static final Parcelable.Creator<ImageInfo> CREATOR = new Parcelable.Creator<ImageInfo>() {
        @Override
        public ImageInfo createFromParcel(Parcel source) {
            return new ImageInfo(source);
        }

        @Override
        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };
}
