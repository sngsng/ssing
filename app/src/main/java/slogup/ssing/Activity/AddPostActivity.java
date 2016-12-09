package slogup.ssing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import slogup.ssing.Model.Comment;
import slogup.ssing.Model.Post;
import slogup.ssing.Model.Tag;
import slogup.ssing.R;
import slogup.ssing.View.AddPostActivityViewHolder;

public class AddPostActivity extends BaseActivity {



    public static final String EXTRA_ADD_TYPE = "extraAddType";
    public static final String EXTRA_POST = "extraPost";
    private static final String LOG_TAG = AddPostActivity.class.getSimpleName();
    private TagSettingsActivity.AddType mAddType;
    private Post mPost;
    private AddPostActivityViewHolder mAddPostViewHolder;
    private MenuItem mNextMenuButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        enableToolbar();

        if (getIntent().hasExtra(EXTRA_ADD_TYPE))
            mAddType = (TagSettingsActivity.AddType) getIntent().getSerializableExtra(EXTRA_ADD_TYPE);
        if (getIntent().hasExtra(EXTRA_POST))
            mPost = getIntent().getParcelableExtra(EXTRA_POST);


        setUpViews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_add_post, menu);
        mNextMenuButton = menu.findItem(R.id.actions_next);
        mNextMenuButton.setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.actions_next:
                finishPosting();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpViews() {


        switch (mAddType) {
            case Post:
                getSupportActionBar().setTitle(getString(R.string.title_add_post));
                break;
            case Comment:
                getSupportActionBar().setTitle(getString(R.string.title_add_comment));
                break;
        }


        mAddPostViewHolder = new AddPostActivityViewHolder(this);
        mAddPostViewHolder.setPostActionCallback(new AddPostActivityViewHolder.AddPostActionCallback() {
            @Override
            public void onPostBodyTextChaged(String string) {

                if (string.trim().equals(getString(R.string.title_hashtag))) mNextMenuButton.setEnabled(false);
                else mNextMenuButton.setEnabled(!string.isEmpty());

            }
        });

        if (mPost != null) {

            ArrayList<Tag> tags = mPost.getTags();

            if (tags != null && !tags.isEmpty())
                mAddPostViewHolder.setUpAddedTags(tags);
        }

    }

    private void finishPosting() {

        String input = mAddPostViewHolder.getBodyInputText();
        ArrayList<String> tags = getHashTagStrings(input);
        String body = getBodyStringByRemovingHashTags(input);

        // 태그가 있을경우 태그설정 액티비티로 이동
        if (!tags.isEmpty()) {

            Intent intent = new Intent(AddPostActivity.this, TagSettingsActivity.class);
            intent.putExtra(TagSettingsActivity.EXTRA_ADD_TYPE, mAddType);
            if (mPost != null)
                intent.putExtra(TagSettingsActivity.EXTRA_POST, mPost);

            intent.putExtra(TagSettingsActivity.EXTRA_BODY, body);
            intent.putExtra(TagSettingsActivity.EXTRA_TAGS, tags);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.scale_down);
        }
        // 없을 경우 등록 타입에 따라 글 등록
        else {

            switch (mAddType) {

                case Post:
                    addPost(body);
                    break;

                case Comment:
                    addComent(body);
                    break;
            }
        }
    }

    private ArrayList<String> getHashTagStrings(String string) {

        Pattern hashTagPattern = Pattern.compile("#(\\S+)(?=[ ,+]|$)");
        Matcher mat = hashTagPattern.matcher(string);
        Set<String> tags = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);

        while (mat.find()) {

            tags.add(mat.group(1));
        }

        ArrayList<String> tagList = new ArrayList<>(tags);
        Log.i(LOG_TAG, "HasTags : " + tagList.toString());

        return tagList;
    }

    private void addComent(String body) {

        Comment.addOne(this, mPost.getId(), body, null, null, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                showProgressDialog();
            }

            @Override
            public void onSuccess(Object response) {

                dismissProgressDialog();
                showToast(getString(R.string.title_finish_adding_comment));

                finish();
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

    private void addPost(String body) {

        Post.addOne(this, body, null, null, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                showProgressDialog();
            }

            @Override
            public void onSuccess(Object response) {

                dismissProgressDialog();

                if (response instanceof Post) {

                    final Post postInAddPost = (Post)response;
                    //Post Post때 응답이 다르기때문에 GET을 한번더함
                    Post.findOne(AddPostActivity.this, postInAddPost.getId(), new RestClient.RestListener() {
                        @Override
                        public void onBefore() {

                        }

                        @Override
                        public void onSuccess(Object response) {

                            if (response instanceof Post) {

                                dismissProgressDialog();
                                showToast(getString(R.string.title_finish_adding_post));
                                Intent intent = new Intent(AddPostActivity.this, PostDetailActivity.class);
                                intent.putExtra(PostDetailActivity.EXTRA_POST, (Post)response);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_from_right, R.anim.scale_down);
                                finish();
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
                else {
                    dismissProgressDialog();
                    showToast(getString(R.string.err_unexpected));
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

    private String getBodyStringByRemovingHashTags(String string) {

        return string.replaceAll("#[^\\\\s]+","");
    }

}
