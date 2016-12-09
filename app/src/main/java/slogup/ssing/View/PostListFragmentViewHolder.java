package slogup.ssing.View;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
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
    private PostListActionCallback mPostListActionCallback;

    private LinearLayoutManager mLinearLayoutManager;
    private boolean mUserScrolledLast;
    private boolean mIsPagingEnabled = true;

    public PostListFragmentViewHolder(Activity activity, View rootView, ArrayList<Post> posts) {

        mCurrentActivity = activity;
        mRootView = rootView;
        mPosts = posts;

    }

    public void setPostListActionCallback(PostListActionCallback postListActionCallback) {
        mPostListActionCallback = postListActionCallback;
    }

    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    public void setEnabledRefreshLayout(boolean enable) {

        mSwipeRefreshLayout.setEnabled(enable);
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

    public void showEmptyView(String msg) {

        mEmptyListTextView.setText(msg);
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

    public void setPagingEnable(boolean enable) {

        mIsPagingEnabled = enable;
    }

    private void setUpRecyclerView() {

        mPostListAdapater = new PostListAdapter(mCurrentActivity, mPosts);
        mPostListAdapater.setListItemButtonCallback(new PostListAdapter.ListItemButtonCallback() {
            @Override
            public void onPostDetailButtonClick(int position) {

                if (mPostListActionCallback != null) {

                    Post post = mPosts.get(position);
                    mPostListActionCallback.onPostDetailClick(post);
                }
            }
        });

        mLinearLayoutManager = new LinearLayoutManager(mCurrentActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewVerticalSpaceDecoration(DEFAULT_VERTICAL_MARGIN));
        mRecyclerView.setAdapter(mPostListAdapater);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                //요청중이 아니고 && 마지막페이지를 스크롤하고 && 데이터 페이지가 마지막이아닐때만호출
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mUserScrolledLast && mIsPagingEnabled) {

                    Log.d("Test Paging", "추가 로드");

                    if (mPostListActionCallback != null) {

                        mPostListActionCallback.onScrollBottom(true);
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = mLinearLayoutManager.getChildCount();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

                mUserScrolledLast = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
        });

        mPostListAdapater.notifyDataSetChanged();
    }


    public interface PostListActionCallback {

        void onPostDetailClick(Post post);
        void onScrollBottom(boolean shouldLoadData);
    }

}
