package slogup.ssing.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import slogup.ssing.Model.Post;
import slogup.ssing.R;
import slogup.ssing.Util.TimeUtils;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder>{

    private ArrayList<Post> mPosts = new ArrayList<>();
    private ListItemButtonCallback mListItemButtonCallback;
    private Context mContext;

    public PostListAdapter(Context context, ArrayList<Post> posts) {

        mContext = context;
        mPosts = posts;

    }

    public void setListItemButtonCallback(ListItemButtonCallback listItemButtonCallback) {
        mListItemButtonCallback = listItemButtonCallback;
    }

    public void setmPosts(ArrayList<Post> mPosts) {
        this.mPosts = mPosts;
    }

    @Override
    public PostListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        return new PostListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostListViewHolder holder, int position) {

        Post post = mPosts.get(position);

        holder.mPostBodyTextView.setText(post.getPostBody());
        holder.mAuthorNickNameTextView.setText(post.getAuthorNickName());

        String voteCountFormatString = String.format(mContext.getString(R.string.format_vote_count), post.getTotalVoteCount());
        holder.mTotalVoteCountTextView.setText(voteCountFormatString);

        String pastTimeString = TimeUtils.toSimplePastTimeStringFormat(post.getCreatedTime());
        holder.mPostDateTextView.setText(pastTimeString);

    }

    @Override
    public int getItemCount() {

        return mPosts.size();
    }

    class PostListViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mAuthorIconImageView;
        TextView mAuthorNickNameTextView;
        TextView mPostDateTextView;
        TextView mPostBodyTextView;
        TextView mTotalVoteCountTextView;
        Button mPostDetailButton;

        PostListViewHolder(View itemView) {

            super(itemView);

            mAuthorIconImageView = (CircleImageView)itemView.findViewById(R.id.post_header_icon_imageview);
            mAuthorNickNameTextView = (TextView)itemView.findViewById(R.id.post_header_nick_name_textview);
            mPostDateTextView = (TextView)itemView.findViewById(R.id.post_header_date_textview);
            mPostBodyTextView = (TextView)itemView.findViewById(R.id.post_item_body_textview);
            mTotalVoteCountTextView = (TextView)itemView.findViewById(R.id.post_item_tag_count_textview);
            mPostDetailButton = (Button)itemView.findViewById(R.id.post_item_detail_button);


            mPostDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mListItemButtonCallback != null) {

                        mListItemButtonCallback.onPostDetailButtonClick(getAdapterPosition());
                    }
                }
            });


        }
    }


    public interface ListItemButtonCallback {

        void onPostDetailButtonClick(int position);
    }

}
