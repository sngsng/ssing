package slogup.ssing.View;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import slogup.ssing.Adapter.PostListAdapter;
import slogup.ssing.Model.Post;
import slogup.ssing.R;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public class PostListFragmentViewHolder {

    private final int DEFAULT_VERTICAL_MARGIN = 20;

    private Activity mCurrentActivity;
    private View mRootView;
    private ProgressBar mProgressBar;
    private TextView mEmptyListTextView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PostListAdapter mPostListAdapater;
    private ArrayList<Post> mPosts;
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener;

    public PostListFragmentViewHolder(Activity activity, View rootView, ArrayList<Post> posts) {

        mCurrentActivity = activity;
        mRootView = rootView;
        mPosts = posts;

    }

    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    public void initializeView() {

        bindWidgets();
        setUpRecyclerView();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (mRefreshListener != null) mRefreshListener.onRefresh();
            }
        });
    }

    public void startLoading() {

        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void stopLoading() {

        if (mProgressBar.getVisibility() == View.VISIBLE) mProgressBar.setVisibility(View.GONE);
    }

    public void showEmptyView() {

        mEmptyListTextView.setVisibility(View.VISIBLE);
    }

    public void hideEmptyView() {

        mEmptyListTextView.setVisibility(View.GONE);
    }

    public void updateList(ArrayList<Post> posts) {

        mPosts = posts;
        mPostListAdapater.setmPosts(mPosts);
        mPostListAdapater.notifyDataSetChanged();
    }

    public void finishRefresh() {

        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);

    }


    private void bindWidgets() {

        mProgressBar = (ProgressBar)mRootView.findViewById(R.id.post_list_progress_bar);
        mEmptyListTextView = (TextView)mRootView.findViewById(R.id.post_list_empty_textview);
        mRecyclerView = (RecyclerView)mRootView.findViewById(R.id.post_list_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout)mRootView.findViewById(R.id.post_list_swipe_refresh_layout);
    }

    private void setUpRecyclerView() {

        mPostListAdapater = new PostListAdapter(mCurrentActivity, mPosts);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mCurrentActivity));
        mRecyclerView.addItemDecoration(new RecyclerViewVerticalSpaceDecoration(DEFAULT_VERTICAL_MARGIN));
        mRecyclerView.setAdapter(mPostListAdapater);

        mPostListAdapater.notifyDataSetChanged();
    }




}
