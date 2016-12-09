package slogup.ssing.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slogup.sgcore.manager.AccountManager;
import com.slogup.sgcore.model.User;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;

import java.util.ArrayList;

import slogup.ssing.Activity.BaseActivity;
import slogup.ssing.Activity.PostDetailActivity;
import slogup.ssing.Model.Post;
import slogup.ssing.Model.PostSearchFilter;
import slogup.ssing.R;
import slogup.ssing.View.PostListFragmentViewHolder;

import static slogup.ssing.Fragment.PostListFragment.PostListOrderType.Latest;
import static slogup.ssing.Fragment.PostListFragment.PostListOrderType.MyActivity;

public class PostListFragment extends Fragment {

    public enum PostListOrderType {

        Latest,
        MyActivity,
        Search

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
    public void onStart() {

        super.onStart();

//        if (mListOrderType == Latest ||
//                mListOrderType == PostListOrderType.MyActivity) {
//            syncCurrentList();
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_post_list, container, false);

        setUpViews(rootView);

        switch (mListOrderType) {

            case Latest:
                mFragmentViewHolder.setEnabledRefreshLayout(true);
                if (mPosts.isEmpty()) loadPosts(true, false, null);
                break;

            case MyActivity:
                updateMyActivityList(getActivity());
                break;

            case Search:
                break;
        }


        return rootView;
    }

    public void searchPost(String searchQuery, boolean isBodySearch) {

        mPosts.clear();
        mFragmentViewHolder.updateList(mPosts);
        loadPosts(true, false, PostSearchFilter.create(searchQuery, isBodySearch));

    }

    public void updateMyActivityList(Context context) {

        if (mFragmentViewHolder != null) {

            if (AccountManager.getInstance().isLoggedIn(context)) {
                mFragmentViewHolder.setEnabledRefreshLayout(true);
                User curUser = AccountManager.getInstance().getUser();
                PostSearchFilter postSearchFilter = new PostSearchFilter();
                postSearchFilter.setAuthorId(Integer.toString(curUser.getId()));
                if (mPosts.isEmpty()) loadPosts(false, false, postSearchFilter);

            } else {

                mFragmentViewHolder.finishRefresh();
                mFragmentViewHolder.setEnabledRefreshLayout(false);
                mPosts.clear();
                mFragmentViewHolder.updateList(mPosts);
                mFragmentViewHolder.showEmptyView(getString(R.string.title_required_login));

            }
        }
    }

    private void syncCurrentList() {

        if (mFragmentViewHolder != null) {

            mPosts.clear();

            if (mListOrderType == Latest) {

                loadPosts(false, true, null);
            }
            else if (mListOrderType == MyActivity) {

                updateMyActivityList(getActivity());
            }


        }
    }

    private void setUpViews(View rootView) {

        mFragmentViewHolder = new PostListFragmentViewHolder(getActivity(), rootView, mPosts);
        mFragmentViewHolder.initializeView();

        mFragmentViewHolder.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // 리프레쉬 할경우 페이징 초기화
                mFragmentViewHolder.setPagingEnable(true);
                syncCurrentList();
            }
        });

        mFragmentViewHolder.setPostListActionCallback(new PostListFragmentViewHolder.PostListActionCallback() {
            @Override
            public void onPostDetailClick(Post post) {

                loadPostDetail(post.getId());
            }

            @Override
            public void onScrollBottom(boolean shouldLoadData) {

                if (shouldLoadData) {


                    if (!mPosts.isEmpty()) {

                        PostSearchFilter searchFilter = new PostSearchFilter();
                        Post lastPost = mPosts.get(mPosts.size() - 1);
                        searchFilter.setLast(lastPost.getCreatedTime());
                        switch (mListOrderType) {

                            case Latest:
                                loadPosts(false, false, searchFilter);
                                break;

                            case MyActivity:
                                if (AccountManager.getInstance().isLoggedIn(getActivity())) {

                                    User user = AccountManager.getInstance().getUser();
                                    searchFilter.setAuthorId(Integer.toString(user.getId()));
                                }
                                loadPosts(false, false, searchFilter);
                                break;

                            case Search:
                                break;


                        }


                    }
                }
            }
        });
    }

    private void loadPostDetail(int postId) {

        Post.findOne(getActivity(), postId, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                showProgress();
            }

            @Override
            public void onSuccess(Object response) {

                dismissProgress();
                if (response instanceof Post) {

                    Post post = (Post) response;
                    startPostDetailActivity(post);
                }
            }

            @Override
            public void onFail(CoreError error) {

                dismissProgress();
                showToast(error.errorMsg);

            }

            @Override
            public void onError(CoreError error) {

                dismissProgress();
                showToast(error.errorMsg);
            }
        });
    }

    private void loadPosts(final boolean isInitialLoad, final boolean isRefresh, @Nullable PostSearchFilter filter) {

        Post.findAll(getActivity(), filter, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                if (isInitialLoad) mFragmentViewHolder.startLoading();
                mFragmentViewHolder.setPagingEnable(false);
            }

            @Override
            public void onSuccess(Object response) {

                mFragmentViewHolder.setPagingEnable(false);
                if (isInitialLoad) mFragmentViewHolder.stopLoading();
                mFragmentViewHolder.finishRefresh();

                if (response instanceof ArrayList) {

                    if (isRefresh) {
                        mPosts.clear();
                    }

                    ArrayList<Post> responsePosts = (ArrayList) response;

                    // 404 Paging을 막음
                    if (responsePosts.isEmpty())
                        mFragmentViewHolder.setPagingEnable(false);
                    else
                        mFragmentViewHolder.setPagingEnable(true);

                    mPosts.addAll(responsePosts);
                    mFragmentViewHolder.updateList(mPosts);

                }

                if (mPosts.isEmpty()) mFragmentViewHolder.showEmptyView(getEmptyViewMsg());
                else mFragmentViewHolder.hideEmptyView();
            }


            @Override
            public void onFail(CoreError error) {

                if (isInitialLoad) mFragmentViewHolder.stopLoading();
                mFragmentViewHolder.finishRefresh();
                mFragmentViewHolder.setPagingEnable(true);
                showToast(error.errorMsg);
            }

            @Override
            public void onError(CoreError error) {

                if (isInitialLoad) mFragmentViewHolder.stopLoading();
                mFragmentViewHolder.finishRefresh();
                mFragmentViewHolder.setPagingEnable(true);
                showToast(error.errorMsg);
            }
        });
    }

    private void showProgress() {

        if (getActivity() instanceof BaseActivity) {

            BaseActivity baseActivity = (BaseActivity) getActivity();
            baseActivity.showProgressDialog();
        }
    }

    private void dismissProgress() {

        if (getActivity() instanceof BaseActivity) {

            BaseActivity baseActivity = (BaseActivity) getActivity();
            baseActivity.dismissProgressDialog();
        }
    }

    private void showToast(String msg) {

        if (getActivity() instanceof BaseActivity) {

            BaseActivity baseActivity = (BaseActivity) getActivity();
            baseActivity.showToast(msg);
        }
    }

    private void startPostDetailActivity(Post post) {

        Intent intent = new Intent(getActivity(), PostDetailActivity.class);
        intent.putExtra(PostDetailActivity.EXTRA_POST, post);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.scale_down);
    }

    private String getEmptyViewMsg() {

        switch (mListOrderType) {

            case Latest:
                return getActivity().getString(R.string.title_empty_post_list);

            case MyActivity:
                return getActivity().getString(R.string.title_empty_post_list_activity);

            case Search:
                return getActivity().getString(R.string.title_empty_post_list_search);

            default:
                return getActivity().getString(R.string.title_empty_post_list);
        }
    }


}
