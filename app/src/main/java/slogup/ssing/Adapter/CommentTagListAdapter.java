package slogup.ssing.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import slogup.ssing.Model.Tag;
import slogup.ssing.R;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public class CommentTagListAdapter extends RecyclerView.Adapter<CommentTagListAdapter.TagVoteListViewHolder> {

    private ArrayList<Tag> mTags = new ArrayList<>();
    private ListItemButtonCallback mListItemButtonCallback;
    private Context mContext;

    public CommentTagListAdapter(Context context, ArrayList<Tag> tags) {

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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment_tag, parent, false);
        return new TagVoteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagVoteListViewHolder holder, int position) {

        Tag tag = mTags.get(position);

        holder.mCommentTagTextView.setText(mContext.getString(R.string.title_tag_prefix) + " " + tag.getName());

    }

    @Override
    public int getItemCount() {

        return mTags.size();
    }

    class TagVoteListViewHolder extends RecyclerView.ViewHolder {

        TextView mCommentTagTextView;


        TagVoteListViewHolder(View itemView) {

            super(itemView);

            mCommentTagTextView = (TextView) itemView.findViewById(R.id.comment_tag_textview);
            mCommentTagTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mListItemButtonCallback != null)
                        mListItemButtonCallback.onTagVoteButtonClick(getAdapterPosition());
                }
            });


        }
    }


    public interface ListItemButtonCallback {

        void onTagVoteButtonClick(int position);
    }

}
