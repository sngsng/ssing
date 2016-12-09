package slogup.ssing.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import slogup.ssing.Model.Post;
import slogup.ssing.Model.Tag;
import slogup.ssing.R;
import slogup.ssing.Util.SsingUtils;
import slogup.ssing.Util.TimeUtils;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public class TagVoteListAdapter extends RecyclerView.Adapter<TagVoteListAdapter.TagVoteListViewHolder> {

    private ArrayList<Tag> mTags = new ArrayList<>();
    private ListItemButtonCallback mListItemButtonCallback;
    private Context mContext;

    public TagVoteListAdapter(Context context, ArrayList<Tag> tags) {

        mContext = context;
        mTags = tags;

    }

    public void setListItemButtonCallback(ListItemButtonCallback listItemButtonCallback) {
        mListItemButtonCallback = listItemButtonCallback;
    }

    public void setTags(ArrayList<Tag> tags) {
        mTags = tags;
    }

    @Override
    public TagVoteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tag_vote, parent, false);
        return new TagVoteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagVoteListViewHolder holder, int position) {

        Tag tag = mTags.get(position);

        holder.mTagVoteRankTextView.setText(String.format(Locale.KOREA, "%d", position + 1));
        holder.mTagVoteNameTextview.setText(mContext.getString(R.string.title_tag_prefix) + " " + tag.getName());
        String voteCountFormatted = NumberFormat.getNumberInstance(Locale.KOREA).format(tag.getCount());
        holder.mTagVoteCountTextView.setText(voteCountFormatted);


        if (tag.getImageInfo() != null) {

            String imageUrl = tag.getImageInfo().getImageUrl();
            Picasso.with(mContext).load(imageUrl).resize(120, 120).into(holder.mTagVoteBackgroundImageView);
            holder.mTagVoteImageCoverView.setVisibility(View.VISIBLE);
        }
        else {

            holder.mTagVoteImageCoverView.setVisibility(View.GONE);
            holder.mTagContainer.setBackgroundColor(tag.getBackgroundColor());
        }


    }

    @Override
    public int getItemCount() {

        return mTags.size();
    }


    class TagVoteListViewHolder extends RecyclerView.ViewHolder {

        TextView mTagVoteRankTextView;
        TextView mTagVoteNameTextview;
        TextView mTagVoteCountTextView;
        ImageView mTagVoteBackgroundImageView;
        View mTagVoteImageCoverView;
        Button mTagVoteButton;
        ViewGroup mTagContainer;

        TagVoteListViewHolder(View itemView) {

            super(itemView);

            mTagContainer = (ViewGroup) itemView.findViewById(R.id.tag_vote_container);
            mTagVoteRankTextView = (TextView) itemView.findViewById(R.id.tag_vote_rank_textview);
            mTagVoteNameTextview = (TextView) itemView.findViewById(R.id.tag_vote_name_textview);
            mTagVoteCountTextView = (TextView) itemView.findViewById(R.id.tag_vote_count_textview);
            mTagVoteBackgroundImageView = (ImageView) itemView.findViewById(R.id.tag_vote_background_imageview);
            mTagVoteImageCoverView = itemView.findViewById(R.id.tag_vote_image_cover_view);
            mTagVoteButton = (Button) itemView.findViewById(R.id.tag_vote_button);

            mTagVoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mListItemButtonCallback != null) {

                        mListItemButtonCallback.onTagVoteButtonClick(getAdapterPosition());
                    }
                }
            });

        }
    }


    public interface ListItemButtonCallback {

        void onTagVoteButtonClick(int position);
    }

}
