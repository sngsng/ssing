package slogup.ssing.Model;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public class ListModel {

    protected long mCreatedTime;
    protected long mUpdatedTime;
    protected long mDeletedTime;

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
}
