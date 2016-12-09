package slogup.ssing.View;

import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;

import slogup.ssing.Activity.TagSettingsActivity;
import slogup.ssing.Adapter.TagSettingsAdapter;
import slogup.ssing.Model.Tag;
import slogup.ssing.R;

/**
 * Created by sngjoong on 2016. 12. 8..
 */

public class TagSettingsActivityViewHolder {

    private TagSettingsActivity mTagSettingsActivity;
    private DragListView mDragListView;
    private TagSettingsAdapter mTagSettingsAdapter;
    private ArrayList<Tag> mTags = new ArrayList<>();
    private TagSettingsActionCallback mActionCallback;

    public TagSettingsActivityViewHolder(TagSettingsActivity tagSettingsActivity) {

        mTagSettingsActivity = tagSettingsActivity;
        bindWidgets();
        setUpActions();
    }

    public void setActionCallback(TagSettingsActionCallback actionCallback) {
        mActionCallback = actionCallback;
    }

    public void setDragEnable(boolean enable) {

        mDragListView.setDragEnabled(enable);
    }

    private void bindWidgets() {

        mTagSettingsAdapter = new TagSettingsAdapter(mTagSettingsActivity, mTags, R.layout.list_item_tag_settings, R.id.tag_settings_container, true);
        mDragListView = (DragListView) mTagSettingsActivity.findViewById(R.id.drag_list_view);
        mDragListView.setLayoutManager(new GridLayoutManager(mTagSettingsActivity, 4));
        mDragListView.setAdapter(mTagSettingsAdapter, false);
    }

    private void setUpActions() {

        mDragListView.setDragListListener(new DragListView.DragListListenerAdapter() {
            @Override
            public void onItemDragStarted(int position) {

            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
            }
        });

        mTagSettingsAdapter.setTagButtonCallback(new TagSettingsAdapter.TagButtonCallback() {
            @Override
            public void onTagButtonClick(int position) {

                if (mActionCallback != null) {

                    mActionCallback.onTagButtonClick(position);
                }
            }
        });

    }

    public void setUpList(ArrayList<Tag> tags) {

        mTags = tags;
        mTagSettingsAdapter.setItemList(mTags);
    }

    public interface TagSettingsActionCallback {

        void onTagButtonClick(int selectedIndex);
    }
}
