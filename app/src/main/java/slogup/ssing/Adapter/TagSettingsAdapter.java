package slogup.ssing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.woxthebox.draglistview.DragItemAdapter;
import java.util.ArrayList;
import slogup.ssing.Model.Tag;
import slogup.ssing.R;
import slogup.ssing.Util.SsingUtils;

/**
 * Created by sngjoong on 2016. 12. 8..
 */

public class TagSettingsAdapter extends DragItemAdapter<Tag, TagSettingsAdapter.ViewHolder> {

    private static final String LOG_TAG = TagSettingsAdapter.class.getSimpleName();
    private Context mContext;
    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    private TagButtonCallback mTagButtonCallback;


    public TagSettingsAdapter(Context context, ArrayList<Tag> tags, int layoutId, int grabHandleId, boolean dragOnLongPress) {

        mContext = context;
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        setHasStableIds(true);
        setItemList(tags);
    }

    public void setTagButtonCallback(TagButtonCallback tagButtonCallback) {
        mTagButtonCallback = tagButtonCallback;
    }

    @Override
    public TagSettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        super.onBindViewHolder(holder,position);
        Tag tag = mItemList.get(position);

        holder.mTagNameTextView.setText(mContext.getString(R.string.title_tag_prefix) + " " + tag.getName());

        if (tag.getImageInfo() != null) {

            String imageUrl = tag.getImageInfo().getImageUrl();
            Picasso.with(mContext).load(imageUrl).resize(120, 120).into(holder.mTagBackgroundImageView);
            holder.mTagImageCoverView.setVisibility(View.VISIBLE);

        }
        else {

            holder.mTagContainer.setBackgroundColor(tag.getBackgroundColor());
            holder.mTagImageCoverView.setVisibility(View.GONE);
        }
    }

    @Override
    public long getItemId(int position) {
        return mItemList.get(position).getId();
    }

    public class ViewHolder extends DragItemAdapter.ViewHolder {


        TextView mTagNameTextView;
        ImageView mTagBackgroundImageView;
        ViewGroup mTagContainer;
        View mTagImageCoverView;

        public ViewHolder(View itemView) {

            super(itemView, mGrabHandleId, mDragOnLongPress);

            mTagImageCoverView = itemView.findViewById(R.id.tag_settings_image_cover_view);
            mTagNameTextView = (TextView)itemView.findViewById(R.id.tag_settings_name_textview);
            mTagBackgroundImageView = (ImageView) itemView.findViewById(R.id.tag_settings_background_imageview);
            mTagContainer = (ViewGroup) itemView.findViewById(R.id.tag_settings_container);

            mTagContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mTagButtonCallback != null) {

                        mTagButtonCallback.onTagButtonClick(getAdapterPosition());
                    }
                }
            });
        }


        @Override
        public boolean onItemLongClicked(View view) {

            return true;
        }
    }

    public interface TagButtonCallback {

        void onTagButtonClick(int position);
    }
}
