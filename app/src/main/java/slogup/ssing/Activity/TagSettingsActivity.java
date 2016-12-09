package slogup.ssing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.slogup.sgcore.model.ImageInfo;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;
import com.slogup.sgcore.network.core.UploadClientHelper;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import pl.aprilapps.easyphotopicker.EasyImage;
import slogup.ssing.Fragment.TagSettingsDialogFragment;
import slogup.ssing.Model.Comment;
import slogup.ssing.Model.Post;
import slogup.ssing.Model.Tag;
import slogup.ssing.Network.SsingAPIMeta;
import slogup.ssing.R;
import slogup.ssing.Util.SsingUtils;
import slogup.ssing.View.TagSettingsActivityViewHolder;

public class TagSettingsActivity extends BaseActivity {

    public static final String EXTRA_ADD_TYPE = "extraAddType";
    public static final String EXTRA_POST = "extraPost";
    public static final String EXTRA_TAGS = "extraTags";
    public static final String EXTRA_BODY = "extraBody";

    private static final String LOG_TAG = TagSettingsActivity.class.getSimpleName();
    private ArrayList<String> mTagStrings;
    private ArrayList<Tag> mTags = new ArrayList<>();
    private AddType mAddType;
    private String mBody;
    private Post mPost;
    private int mSelectedTagIndex;
    private TagSettingsActivityViewHolder mTagSettingsViewHolder;
    private MenuItem mDoneMenuItem;

    public enum AddType {

        Post,
        Comment
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_settings);

        enableToolbar();
        setUpIntentExtras();
        setUpViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {

            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                Log.i(LOG_TAG, "onImagePicked : " + imageFile.getName() + ", source : " + source);
                uploadImage(imageFile);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_tag_settings, menu);
        mDoneMenuItem = menu.findItem(R.id.actions_finish);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.actions_finish:
                finishPosting();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpIntentExtras() {

        if (getIntent().hasExtra(EXTRA_ADD_TYPE))
            mAddType = (AddType) getIntent().getSerializableExtra(EXTRA_ADD_TYPE);

        if (getIntent().hasExtra(EXTRA_POST))
            mPost = getIntent().getParcelableExtra(EXTRA_POST);

        if (getIntent().hasExtra(EXTRA_TAGS)) {

            mTagStrings = getIntent().getStringArrayListExtra(EXTRA_TAGS);
            mTags = convertStringsToTag(mTagStrings);
        }

        if (getIntent().hasExtra(EXTRA_BODY))
            mBody = getIntent().getStringExtra(EXTRA_BODY);
    }


    private ArrayList<Tag> convertStringsToTag(ArrayList<String> tagStrings) {

        ArrayList<Tag> tags = new ArrayList<>();

        for (int i = 0; i < tagStrings.size(); i++) {

            String tagString = tagStrings.get(i);
            Tag tag = new Tag(tagString);
            tag.setBackgroundColor(SsingUtils.getTagBackgroundColor(this, i));
            tag.setId(i);
            tags.add(tag);

        }

        return tags;
    }

    private void setUpViews() {

        mTagSettingsViewHolder = new TagSettingsActivityViewHolder(this);
        mTagSettingsViewHolder.setUpList(mTags);
        mTagSettingsViewHolder.setActionCallback(new TagSettingsActivityViewHolder.TagSettingsActionCallback() {
            @Override
            public void onTagButtonClick(int selectedIndex) {

                mSelectedTagIndex = selectedIndex;
                showTagSettingsDialog(mTags.get(selectedIndex));
            }
        });


    }

    private void showTagSettingsDialog(Tag tag) {

        TagSettingsDialogFragment dialogFragment = TagSettingsDialogFragment.newInstance(tag);
        dialogFragment.setActionCallback(new TagSettingsDialogFragment.TagSettingsDialogActionCallback() {
            @Override
            public void onImageAddButtonClick() {

                EasyImage.openGallery(TagSettingsActivity.this, 0);
            }

            @Override
            public void onRemoveImageButtonClick() {

            }

            @Override
            public void onRemoveTagButtonClick() {

            }
        });
        dialogFragment.show(getSupportFragmentManager(), LOG_TAG);
    }

    private void finishPosting() {


        switch (mAddType) {

            case Comment:
                addComment();
                break;

            case Post:
                addPost();
                break;
        }
    }

    private void addComment() {

        Intent intent = new Intent(TagSettingsActivity.this, PostDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


        if (mPost != null) {
            Comment.addOne(this, mPost.getId(), mBody, getTagParams(), getImageIdParmas(), new RestClient.RestListener() {
                @Override
                public void onBefore() {

                    mDoneMenuItem.setEnabled(false);
                    showProgressDialog();
                }

                @Override
                public void onSuccess(Object response) {

                    mDoneMenuItem.setEnabled(true);
                    dismissProgressDialog();
                    showToast(getString(R.string.title_finish_adding_comment));

                    Intent intent = new Intent(TagSettingsActivity.this, PostDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.scale_up, R.anim.slide_out_to_right);
                }

                @Override
                public void onFail(CoreError error) {

                    mDoneMenuItem.setEnabled(true);
                    dismissProgressDialog();
                    showToast(error.errorMsg);
                }

                @Override
                public void onError(CoreError error) {

                    mDoneMenuItem.setEnabled(true);
                    dismissProgressDialog();
                    showToast(error.errorMsg);
                }
            });
        }
    }

    private void addPost() {

        Post.addOne(this, mBody, getTagParams(), getImageIdParmas(), new RestClient.RestListener() {
            @Override
            public void onBefore() {

                showProgressDialog();
            }

            @Override
            public void onSuccess(Object response) {


                if (response instanceof Post) {

                    Post postInAdd = (Post)response;
                    Post.findOne(TagSettingsActivity.this, postInAdd.getId(), new RestClient.RestListener() {
                        @Override
                        public void onBefore() {

                        }

                        @Override
                        public void onSuccess(Object response) {

                            dismissProgressDialog();
                            if (response instanceof Post) {

                                showToast(getString(R.string.title_finish_adding_post));
                                Intent intent = new Intent(TagSettingsActivity.this, PostDetailActivity.class);
                                intent.putExtra(PostDetailActivity.EXTRA_POST, (Post)response);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_from_right, R.anim.scale_down);
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

    private ArrayList<String> getTagParams() {

        ArrayList<String> tagParams = new ArrayList<>();

        for (Tag tag : mTags) {

            tagParams.add(tag.getName());
        }

        return tagParams;
    }

    private ArrayList<String> getImageIdParmas() {

        ArrayList<String> imageParams = new ArrayList<>();

        for (Tag tag : mTags) {

            if (tag.getImageInfo() != null) {

                imageParams.add(Integer.toString(tag.getImageInfo().getId()));
            }
        }

        return imageParams;
    }

    private void uploadImage(File file) {

        ArrayList<File> files = new ArrayList<>();
        files.add(file);
        UploadClientHelper.uploadImages(this, SsingAPIMeta.IMAGE_FOLDER, files, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                mTagSettingsViewHolder.setDragEnable(false);
                showProgressDialog();
            }

            @Override
            public void onSuccess(Object response) {

                dismissProgressDialog();

                if (response instanceof ArrayList) {

                    ArrayList<ImageInfo> imageInfos = (ArrayList<ImageInfo>) response;

                    if (!imageInfos.isEmpty()) {

                        ImageInfo imageInfo = imageInfos.get(0);
                        // 지워질 객체
                        Tag tempTag = mTags.get(mSelectedTagIndex);

                        // 카피
                        Tag tag = new Tag();
                        tag.setName(tempTag.getName());
                        tag.setImageInfo(imageInfo);
                        tag.setId(tempTag.getId());
                        tag.setBackgroundColor(tempTag.getBackgroundColor());

                        // 지워질 객체지움
                        mTags.remove(mSelectedTagIndex);

                        // 카피된객체 insert
                        mTags.add(0, tag);

                        // 어댑터 업데이트
                        mTagSettingsViewHolder.setUpList(mTags);
                    }
                }

                mTagSettingsViewHolder.setDragEnable(true);
            }

            @Override
            public void onFail(CoreError error) {

                dismissProgressDialog();
                mTagSettingsViewHolder.setDragEnable(true);
                showToast(error.errorMsg);
            }

            @Override
            public void onError(CoreError error) {

                dismissProgressDialog();
                mTagSettingsViewHolder.setDragEnable(true);
                showToast(error.errorMsg);
            }
        });
    }
}
