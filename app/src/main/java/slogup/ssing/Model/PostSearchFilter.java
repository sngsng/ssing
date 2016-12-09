package slogup.ssing.Model;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import slogup.ssing.Network.SsingAPIMeta;

/**
 * Created by sngjoong on 2016. 11. 28..
 */

public class PostSearchFilter {

    private String mAuthorId;
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
        postSearchFilter.setLast(last);

        return postSearchFilter;
    }

    public static PostSearchFilter create(String searchQuery, boolean isBodySearch) {

        PostSearchFilter searchFilter = new PostSearchFilter();

        if (isBodySearch) {

            searchFilter.setSearchItem(searchQuery);
        }
        else {

            ArrayList<String> tags = new ArrayList<>();
            tags.add(searchQuery);
            searchFilter.setTags(tags);
        }

        return searchFilter;
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();

        try {

            // 작성자 아이디
            if (mAuthorId != null) {
                jsonObject.put(SsingAPIMeta.Posts.Request.AUTHOR_ID, mAuthorId);
            }

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

    public String getAuthorId() {
        return mAuthorId;
    }

    public void setAuthorId(String authorId) {
        mAuthorId = authorId;
    }

    public String getSearchItem() {
        return mSearchItem;
    }

    public void setSearchItem(String searchItem) {
        mSearchItem = searchItem;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

    public void setTags(ArrayList<String> tags) {
        mTags = tags;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLng() {
        return mLng;
    }

    public void setLng(double lng) {
        mLng = lng;
    }

    public double getRadius() {
        return mRadius;
    }

    public void setRadius(double radius) {
        mRadius = radius;
    }

    public String getOrderBy() {
        return mOrderBy;
    }

    public void setOrderBy(String orderBy) {
        mOrderBy = orderBy;
    }

    public String getSort() {
        return mSort;
    }

    public void setSort(String sort) {
        mSort = sort;
    }

    public long getLast() {
        return mLast;
    }

    public void setLast(long last) {
        mLast = last;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
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
