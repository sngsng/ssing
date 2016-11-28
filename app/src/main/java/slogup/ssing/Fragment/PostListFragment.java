package slogup.ssing.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import slogup.ssing.Model.Post;
import slogup.ssing.R;
import slogup.ssing.View.PostListFragmentViewHolder;

public class PostListFragment extends Fragment {

    public enum PostListOrderType{

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
        mFragmentViewHolder = new PostListFragmentViewHolder(getActivity(), rootView, mPosts);
        mFragmentViewHolder.initializeView();
        return rootView;
    }

}
