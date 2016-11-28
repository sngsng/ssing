package slogup.ssing.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;

import java.util.ArrayList;

import slogup.ssing.Model.Post;
import slogup.ssing.Model.PostSearchFilter;
import slogup.ssing.R;
import slogup.ssing.View.PostListFragmentViewHolder;

public class PostListFragment extends Fragment {

    public enum PostListOrderType {

        Latest,
        Distance

    }

    private static final String LOG_TAG = PostListFragment.class.getSimpleName();
    private static final String ARG_ORDER_TYPE = "argOrderType";
    private PostListOrderType mListOrderType;
    private PostListFragmentViewHolder mFragmentViewHolder;
    private ArrayList<Post> mPosts = new ArrayList<>();


    public PostListFragment() {

    }

    public static PostListFragment newInstance(PostListOrderType orderType) {

        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ORDER_TYPE, orderType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            mListOrderType = (PostListOrderType) getArguments().getSerializable(ARG_ORDER_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_post_list, container, false);

        setUpViews(rootView);

        if (mPosts.isEmpty()) loadPosts(true, false, null);
        return rootView;
    }

    private void setUpViews(View rootView) {

        mFragmentViewHolder = new PostListFragmentViewHolder(getActivity(), rootView, mPosts);
        mFragmentViewHolder.initializeView();

        mFragmentViewHolder.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadPosts(false, true, null);
            }
        });
    }

    private void loadPosts(final boolean isInitialLoad, final boolean isRefresh, @Nullable PostSearchFilter filter) {

        Post.findAll(getActivity(), filter, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                if (isInitialLoad) mFragmentViewHolder.startLoading();
            }

            @Override
            public void onSuccess(Object response) {


                if (isInitialLoad) mFragmentViewHolder.stopLoading();
                mFragmentViewHolder.finishRefresh();

                if (response instanceof ArrayList) {

                    if (isRefresh) {
                        mPosts.clear();
                    }

                    ArrayList<Post> responsePosts = (ArrayList) response;
                    mPosts.addAll(responsePosts);
                    mFragmentViewHolder.updateList(mPosts);

                }

                if (mPosts.isEmpty()) mFragmentViewHolder.showEmptyView();
                else mFragmentViewHolder.hideEmptyView();
            }


            @Override
            public void onFail(CoreError error) {

                if (isInitialLoad) mFragmentViewHolder.stopLoading();
                mFragmentViewHolder.finishRefresh();
            }

            @Override
            public void onError(CoreError error) {

                if (isInitialLoad) mFragmentViewHolder.stopLoading();
                mFragmentViewHolder.finishRefresh();
            }
        });
    }


}
