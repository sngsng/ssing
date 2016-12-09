package slogup.ssing.View;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import slogup.ssing.Activity.AddPostActivity;
import slogup.ssing.Adapter.CommentTagListAdapter;
import slogup.ssing.Model.Tag;
import slogup.ssing.R;

/**
 * Created by sngjoong on 2016. 12. 7..
 */

public class AddPostActivityViewHolder {

    private static final String LOG_TAG = AddPostActivityViewHolder.class.getSimpleName();
    private AddPostActivity mAddPostActivity;
    private EditText mBodyInputEditText;
    private Button mAppendHashTagButton;
    private AddPostActionCallback mPostActionCallback;
    private RecyclerView mAddedTagsRecyclerView;
    private CommentTagListAdapter mCommentTagListAdapter;
    private ArrayList<Tag> mTags = new ArrayList<>();

    public AddPostActivityViewHolder(AddPostActivity addPostActivity) {

        mAddPostActivity = addPostActivity;
        bindWidgets();
        setUpActions();
    }

    public void setPostActionCallback(AddPostActionCallback postActionCallback) {
        mPostActionCallback = postActionCallback;
    }

    public String getBodyInputText() {

        return mBodyInputEditText.getText().toString();
    }

    public void setUpAddedTags(ArrayList<Tag> tags) {

        mTags = tags;
        mCommentTagListAdapter = new CommentTagListAdapter(mAddPostActivity, tags);
        mCommentTagListAdapter.setListItemButtonCallback(new CommentTagListAdapter.ListItemButtonCallback() {
            @Override
            public void onTagVoteButtonClick(int position) {

                String tagName = mTags.get(position).getName();
                String hashTag = mAddPostActivity.getString(R.string.title_hashtag) + tagName + " ";
                mBodyInputEditText.append(hashTag);
            }
        });
        mAddedTagsRecyclerView.setAdapter(mCommentTagListAdapter);
        mAddedTagsRecyclerView.setLayoutManager(new LinearLayoutManager(mAddPostActivity, LinearLayoutManager.HORIZONTAL, false));
    }

    private void bindWidgets() {

        mBodyInputEditText = (EditText)mAddPostActivity.findViewById(R.id.add_post_edittext);
        mAppendHashTagButton = (Button)mAddPostActivity.findViewById(R.id.add_post_append_hashtag_button);
        mAddedTagsRecyclerView = (RecyclerView)mAddPostActivity.findViewById(R.id.add_post_recyclerview);

    }

    private void setUpActions() {

        mBodyInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String input = editable.toString();

                if (mPostActionCallback != null) {
                    mPostActionCallback.onPostBodyTextChaged(input);
                }
            }
        });

        mAppendHashTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = mBodyInputEditText.getText().toString();
                String hashTag = mAddPostActivity.getString(R.string.title_hashtag);

                if (!input.isEmpty() && input.substring(input.length() - 1).equals(hashTag)) return;

                mBodyInputEditText.append(mAddPostActivity.getString(R.string.title_hashtag));
            }
        });
    }

    public interface AddPostActionCallback {


        void onPostBodyTextChaged(String string);
    }


}
