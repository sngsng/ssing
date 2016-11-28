package slogup.ssing.Model;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import slogup.ssing.SsingAPIMeta;

/**
 * Created by sngjoong on 2016. 11. 28..
 */

public class PostSearchFilter {

    private String mSearchItem;
    private ArrayList<String> mTags;
    private String mGender;
    private double mLat = -1.0;
    private double mLng = -1.0;
    private double mRadius;
    private String mOrderBy;
    private String mSort;
    private long mLast = -1;
    private int mSize = -1;

    public PostSearchFilter() {
    }

    public PostSearchFilter(@Nullable  String mSearchItem,
                            @Nullable  ArrayList<String> mTags,
                            @Nullable String mGender,
                            double mLat, double mLng,
                            double mRadius,
                            @Nullable String mOrderBy,
                            @Nullable String mSort, long last, int size) {

        this.mSearchItem = mSearchItem;
        this.mTags = mTags;
        this.mGender = mGender;
        this.mLat = mLat;
        this.mLng = mLng;
        this.mRadius = mRadius;
        this.mOrderBy = mOrderBy;
        this.mSort = mSort;
        this.mLast = last;
        this.mSize = size;
    }

    public static PostSearchFilter create(long last) {

        PostSearchFilter postSearchFilter = new PostSearchFilter();
        postSearchFilter.setmLast(last);

        return postSearchFilter;
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();

        try {
            // 검색어
            if (mSearchItem != null)
                jsonObject.put(SsingAPIMeta.Posts.Request.SEARCH_ITEM, mSearchItem);

            // 태그 검색
           if (mTags != null && !mTags.isEmpty())
                jsonObject.put(SsingAPIMeta.Posts.Request.TAGS, toTagsParam(mTags));

            // 성별 필터
            if (mGender != null)
                jsonObject.put(SsingAPIMeta.Posts.Request.GENDER, mGender);

            // 위도, 경도
            if (mLat > 0 && mLng > 0) {

                jsonObject.put(SsingAPIMeta.Posts.Request.LAT, Double.toString(mLat));
                jsonObject.put(SsingAPIMeta.Posts.Request.LNG, Double.toString(mLng));
            }

            // 정렬기준
            if (mOrderBy != null)
                jsonObject.put(SsingAPIMeta.Posts.Request.ORDER_BY, mOrderBy);

            // 정렬방식
            if (mSort != null)
                jsonObject.put(SsingAPIMeta.Posts.Request.SORT, mSort);

            // 마지막값 (페이징)
            if (mLast > 0)
                jsonObject.put(SsingAPIMeta.Posts.Request.LAST, Long.toString(mLast));

            // 불러올 갯수
            if (mSize > 0) {
                jsonObject.put(SsingAPIMeta.Posts.Request.SIZE, Integer.toString(mSize));
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public String getmSearchItem() {
        return mSearchItem;
    }

    public void setmSearchItem(String mSearchItem) {
        this.mSearchItem = mSearchItem;
    }

    public ArrayList<String> getmTags() {
        return mTags;
    }

    public void setmTags(ArrayList<String> mTags) {
        this.mTags = mTags;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public double getmLat() {
        return mLat;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public double getmLng() {
        return mLng;
    }

    public void setmLng(double mLng) {
        this.mLng = mLng;
    }

    public double getmRadius() {
        return mRadius;
    }

    public void setmRadius(double mRadius) {
        this.mRadius = mRadius;
    }

    public String getmOrderBy() {
        return mOrderBy;
    }

    public void setmOrderBy(String mOrderBy) {
        this.mOrderBy = mOrderBy;
    }

    public String getmSort() {
        return mSort;
    }

    public void setmSort(String mSort) {
        this.mSort = mSort;
    }

    public long getmLast() {
        return mLast;
    }

    public void setmLast(long mLast) {
        this.mLast = mLast;
    }

    public int getmSize() {
        return mSize;
    }

    public void setmSize(int mSize) {
        this.mSize = mSize;
    }

    private String toTagsParam(ArrayList<String> tags) {

        String tagParams = "";

        for (int i = 0; i < tags.size(); i++) {

            String tag = tags.get(i);

            if (!tagParams.isEmpty()) tagParams = tagParams + "," + tag;
            else tagParams = tag;

        }

        return tagParams;
    }
}
