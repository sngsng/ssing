package slogup.ssing.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.aprilapps.easyphotopicker.EasyImage;
import slogup.ssing.Model.Tag;
import slogup.ssing.R;
import slogup.ssing.View.TagSettingsActivityViewHolder;

/**
 * Created by sngjoong on 2016. 12. 8..
 */

public class TagSettingsDialogFragment extends DialogFragment {

    private static final String LOG_TAG = TagSettingsDialogFragment.class.getSimpleName();
    private static final String ARGS_TAG = "argsTag";
    private Tag mTag;

    private Button mAddImageButton;
    private Button mRemoveImageButton;
    private Button mRemoveTagButton;
    private TagSettingsDialogActionCallback mActionCallback;

    public static TagSettingsDialogFragment newInstance(Tag tag) {

        TagSettingsDialogFragment dialogFragment = new TagSettingsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_TAG, tag);
        dialogFragment.setArguments(bundle);

        return dialogFragment;

    }

    public void setActionCallback(TagSettingsDialogActionCallback actionCallback) {
        mActionCallback = actionCallback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            mTag = getArguments().getParcelable(ARGS_TAG);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_fragment_tag_settings, container, false);
        bindWidgets(rootView);
        setUpActions();
        return rootView;
    }

    private void bindWidgets(View rootView) {

        mAddImageButton = (Button) rootView.findViewById(R.id.tag_settings_add_image_button);
        mRemoveImageButton = (Button) rootView.findViewById(R.id.tag_settings_remove_image_button);
        mRemoveTagButton = (Button) rootView.findViewById(R.id.tag_settings_remove_tag_button);

        mRemoveTagButton.setVisibility(View.GONE);

        if (mTag.getImageInfo() != null) {

            mRemoveImageButton.setVisibility(View.VISIBLE);
            mAddImageButton.setVisibility(View.GONE);
        }

        else {
            mRemoveImageButton.setVisibility(View.GONE);
            mAddImageButton.setVisibility(View.VISIBLE);
        }


    }

    private void setUpActions() {

        mAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mActionCallback != null) {

                    mActionCallback.onImageAddButtonClick();
                }
                dismiss();

            }
        });

        mRemoveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mActionCallback != null) {

                    mActionCallback.onRemoveImageButtonClick();
                }
                dismiss();

            }
        });

        mRemoveTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mActionCallback != null) {

                    mActionCallback.onRemoveTagButtonClick();
                }
                dismiss();

            }
        });
    }

    public interface TagSettingsDialogActionCallback {

        void onImageAddButtonClick();
        void onRemoveImageButtonClick();
        void onRemoveTagButtonClick();
    }

}