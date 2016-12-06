package slogup.ssing.View;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import slogup.ssing.Activity.PostDetailActivity;
import slogup.ssing.Adapter.CommentListAdapter;
import slogup.ssing.Adapter.TagVoteListAdapter;
import slogup.ssing.Model.Comment;
import slogup.ssing.Model.Post;
import slogup.ssing.Model.Tag;
import slogup.ssing.R;
import slogup.ssing.Util.TimeUtils;

/**
 * Created by sngjoong on 2016. 12. 4..
 */

public class PostDetailViewHolder {

    private PostDetailActivity mPostDetailActivity;
    private TextView mNickNameTextView;
    private TextView mPostDateTextView;
    private TextView mPostDetailBodyTextView;
    private ViewGroup mAddCommentContainer;

    private TagVoteListAdapter mTagVoteListAdapter;
    private CommentListAdapter mCommentListAdapter;
    private RecyclerView mTagVoteRecyclerView;
    private RecyclerView mCommentRecyclerView;
    private TextView mEmptyCommentTextView;

    private ArrayList<Comment> mComments;
    private ArrayList<Tag> mTagVotes;
    private Post mPost;
    private PostDetailButtonCallback mButtonCallback;

    public PostDetailViewHolder(PostDetailActivity postDetailActivity, Post post) {

        mPostDetailActivity = postDetailActivity;
        setPost(post);
        bindWidgets();
        setUpActions();
    }

    public void setPost(Post post) {

        mPost = post;
        mTagVotes = mPost.getTags();
        mComments = mPost.getComments();
    }

    public void setButtonCallback(PostDetailButtonCallback buttonCallback) {
        mButtonCallback = buttonCallback;
    }

    public void updateView(Post post) {

        setPost(post);

        mNickNameTextView.setText(post.getAuthorNickName());
        String timeFormat = TimeUtils.toSimplePastTimeStringFormat(post.getCreatedTime());
        mPostDateTextView.setText(timeFormat);
        mPostDetailBodyTextView.setText(post.getPostBody());
        updateVoteTagList();
        updateCommetList();
    }

    private void bindWidgets() {

        mNickNameTextView = (TextView) mPostDetailActivity.findViewById(R.id.post_header_nick_name_textview);
        mPostDateTextView = (TextView) mPostDetailActivity.findViewById(R.id.post_header_date_textview);
        mPostDetailBodyTextView = (TextView) mPostDetailActivity.findViewById(R.id.post_detail_body_textview);

        mAddCommentContainer = (ViewGroup) mPostDetailActivity.findViewById(R.id.post_detail_add_comment_container);
        mTagVoteRecyclerView = (RecyclerView) mPostDetailActivity.findViewById(R.id.post_detail_tag_recyclerview);
        mCommentRecyclerView = (RecyclerView) mPostDetailActivity.findViewById(R.id.post_detail_comment_recyclerview);
        mEmptyCommentTextView = (TextView) mPostDetailActivity.findViewById(R.id.post_detail_empty_comment_textview);
        mTagVoteListAdapter = new TagVoteListAdapter(mPostDetailActivity, mTagVotes);
        mCommentListAdapter = new CommentListAdapter(mPostDetailActivity, mComments);
    }

    private void setUpActions() {

        mTagVoteListAdapter.setListItemButtonCallback(new TagVoteListAdapter.ListItemButtonCallback() {
            @Override
            public void onTagVoteButtonClick(int position) {

                if (mButtonCallback != null) {

                    Tag selectedTag = mTagVotes.get(position);
                    mButtonCallback.onVoteButtonClick(selectedTag);
                }

            }
        });

        mAddCommentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mButtonCallback != null) {

                    mButtonCallback.onAddCommentButtonClick();
                }
            }
        });
    }

    private void updateVoteTagList() {

        if (mTagVotes != null) {

            mTagVoteListAdapter.setTags(mTagVotes);
            mTagVoteRecyclerView.setAdapter(mTagVoteListAdapter);

            if (mTagVoteRecyclerView.getLayoutManager() == null)
                mTagVoteRecyclerView.setLayoutManager(new LinearLayoutManager(mPostDetailActivity, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    private void updateCommetList() {

        if (mComments != null) {

            mCommentListAdapter.setComments(mComments);
            mCommentRecyclerView.setAdapter(mCommentListAdapter);
            if (mCommentRecyclerView.getLayoutManager() == null)
                mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(mPostDetailActivity, LinearLayoutManager.VERTICAL, false));
        }
    }




    public interface PostDetailButtonCallback {

        void onVoteButtonClick(Tag tag);

        void onAddCommentButtonClick();
    }
}
