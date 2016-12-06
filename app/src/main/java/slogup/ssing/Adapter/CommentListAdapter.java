package slogup.ssing.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import slogup.ssing.Model.Comment;
import slogup.ssing.Model.Tag;
import slogup.ssing.R;
import slogup.ssing.Util.TimeUtils;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentListViewHolder> {
    

    private ArrayList<Comment> mComments = new ArrayList<>();
    private ListItemButtonCallback mListItemButtonCallback;
    private ArrayList<CommentTagListAdapter> mCommentTagListAdapters = new ArrayList<>();
    private Context mContext;

    public CommentListAdapter(Context context, ArrayList<Comment> comments) {

        mContext = context;
        setComments(comments);


    }

    public void setListItemButtonCallback(ListItemButtonCallback listItemButtonCallback) {
        mListItemButtonCallback = listItemButtonCallback;
    }

    public void setComments(ArrayList<Comment> comments) {

        mComments = comments;
        mCommentTagListAdapters.clear();
        for (int i = 0; i < comments.size(); i++) {

            Comment comment = comments.get(i);
            CommentTagListAdapter adapter = new CommentTagListAdapter(mContext, comment.getTags());
            mCommentTagListAdapters.add(adapter);
        }
    }

    @Override
    public CommentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent, false);
        return new CommentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentListViewHolder holder, int position) {

        Comment comment = mComments.get(position);

        String pastTimeString = TimeUtils.toSimplePastTimeStringFormat(comment.getCreatedTime());

        holder.mAuthorNickNameTextView.setText(comment.getAuthorNickName());
        holder.mCommentDateTextView.setText(pastTimeString);

        if (comment.getBody() != null && !comment.getBody().equals("null")) {

            holder.mCommentBodyTextView.setText(comment.getBody());
            holder.mCommentBodyTextView.setVisibility(View.VISIBLE);
        }
        else {

            holder.mCommentBodyTextView.setVisibility(View.GONE);
        }

        setUpTagList(holder.mCommentTagRecyclerView, mCommentTagListAdapters.get(position));
        if (comment.getTags() != null & !comment.getTags().isEmpty()) {

            holder.mCommentTagRecyclerView.setVisibility(View.VISIBLE);
        }
        else {

            holder.mCommentTagRecyclerView.setVisibility(View.GONE);
        }


    }

    private void setUpTagList(RecyclerView recyclerView, CommentTagListAdapter adapter) {

        if (recyclerView.getLayoutManager() == null) recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return mComments.size();
    }


    class CommentListViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mAuthorIconImageView;
        TextView mAuthorNickNameTextView;
        TextView mCommentDateTextView;
        TextView mCommentBodyTextView;
        RecyclerView mCommentTagRecyclerView;


        CommentListViewHolder(View itemView) {

            super(itemView);
            mAuthorIconImageView = (CircleImageView)itemView.findViewById(R.id.post_header_icon_imageview);
            mAuthorNickNameTextView = (TextView)itemView.findViewById(R.id.post_header_nick_name_textview);
            mCommentDateTextView = (TextView)itemView.findViewById(R.id.post_header_date_textview);
            mCommentBodyTextView = (TextView)itemView.findViewById(R.id.comment_body_textview);
            mCommentTagRecyclerView = (RecyclerView)itemView.findViewById(R.id.comment_tag_recyclerview);


        }
    }


    public interface ListItemButtonCallback {

        void onTagVoteButtonClick(int position);
    }

}
