package slogup.ssing.Model;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public class ListModel {

    protected double mCreatedTime;
    protected double mUpdatedTime;
    protected double mDeletedTime;

    public double getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(double createdTime) {
        mCreatedTime = createdTime;
    }

    public double getUpdatedTime() {
        return mUpdatedTime;
    }

    public void setUpdatedTime(double updatedTime) {
        mUpdatedTime = updatedTime;
    }

    public double getDeletedTime() {
        return mDeletedTime;
    }

    public void setDeletedTime(double deletedTime) {
        mDeletedTime = deletedTime;
    }
}
