package slogup.ssing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.slogup.sgcore.manager.AccountManager;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;

import java.util.Locale;

import slogup.ssing.Fragment.SignUpDialogFragment;
import slogup.ssing.Model.Post;
import slogup.ssing.Model.Tag;
import slogup.ssing.Network.SsingClientHelper;
import slogup.ssing.R;
import slogup.ssing.View.PostDetailActivityViewHolder;

public class PostDetailActivity extends BaseActivity {

    public static final String EXTRA_POST = "extraPost";
    private static final String LOG_TAG = PostDetailActivity.class.getSimpleName();
    private Post mSelectedPost;
    private PostDetailActivityViewHolder mPostDetailViewHolder;
    private SignUpDialogFragment mSignUpDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        enableToolbar();

        if (getIntent().hasExtra(EXTRA_POST)) {

            mSelectedPost = getIntent().getParcelableExtra(EXTRA_POST);
            getSupportActionBar().setTitle(String.format(Locale.KOREA, getString(R.string.title_post_detail_header), mSelectedPost.getAuthorNickName()));
        }

        setUpViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {

            case android.R.id.home: {
                backToMain();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        backToMain();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPostDetailViewHolder != null) updatePostDetail();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (mSignUpDialogFragment != null)
            mSignUpDialogFragment.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpViews() {

        mPostDetailViewHolder = new PostDetailActivityViewHolder(this, mSelectedPost);
        mPostDetailViewHolder.setButtonCallback(new PostDetailActivityViewHolder.PostDetailButtonCallback() {
            @Override
            public void onVoteButtonClick(Tag tag) {

                if (isLoggedIn()) {

                    vote(tag.getName());
                }
                else {

                    showLogginDialog();
                }

            }

            @Override
            public void onAddCommentButtonClick() {

                if (isLoggedIn()) {

                    startAddCommentActivity();
                }
                else {

                    showLogginDialog();
                }
            }
        });

        mPostDetailViewHolder.updateView(mSelectedPost);
    }

    private void vote(final String tagName) {

        SsingClientHelper.vote(this, mSelectedPost.getId(), tagName, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                showProgressDialog();
            }

            @Override
            public void onSuccess(Object response) {

                dismissProgressDialog();
                updatePostDetail();
                String tag = getString(R.string.title_tag_prefix) + " " + tagName;
                showToast(String.format(Locale.KOREA,getString(R.string.title_finish_vote_format), tag));
            }

            @Override
            public void onFail(CoreError error) {

                dismissProgressDialog();
                showToast(error.errorMsg);
            }

            @Override
            public void onError(CoreError error) {

                dismissProgressDialog();
                showToast(error.errorMsg);
            }
        });
    }

    private void updatePostDetail() {

        Post.findOne(this, mSelectedPost.getId(), new RestClient.RestListener() {
            @Override
            public void onBefore() {

                showProgressDialog();
            }

            @Override
            public void onSuccess(Object response) {

                dismissProgressDialog();

                if (response instanceof Post) {

                    mSelectedPost = (Post)response;
                    mPostDetailViewHolder.updateView(mSelectedPost);
                }
            }

            @Override
            public void onFail(CoreError error) {

                dismissProgressDialog();
                showToast(error.errorMsg);
            }

            @Override
            public void onError(CoreError error) {

                dismissProgressDialog();
                showToast(error.errorMsg);
            }
        });
    }

    private boolean isLoggedIn() {

        if (AccountManager.getInstance().isLoggedIn(PostDetailActivity.this)) {

            return true;
        }
        else {

            return false;
        }
    }

    private void showLogginDialog() {

        mSignUpDialogFragment = SignUpDialogFragment.newInstance();
        mSignUpDialogFragment.setDismissCallback(new SignUpDialogFragment.DismissCallback() {
            @Override
            public void onDismiss() {

            }
        });
        mSignUpDialogFragment.show(getSupportFragmentManager(), SignUpDialogFragment.class.getSimpleName());
    }

    private void startAddCommentActivity() {

        Intent intent = new Intent(PostDetailActivity.this, AddPostActivity.class);
        intent.putExtra(AddPostActivity.EXTRA_ADD_TYPE, TagSettingsActivity.AddType.Comment);
        intent.putExtra(AddPostActivity.EXTRA_POST, mSelectedPost);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.scale_down);
    }

    private void backToMain() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.scale_up, R.anim.slide_out_to_right);
    }
}
