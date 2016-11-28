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

import slogup.ssing.Model.Post;
import slogup.ssing.R;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder>{

    private ArrayList<Post> mPosts = new ArrayList<>();
    private Context mContext;

    public PostListAdapter(Context context, ArrayList<Post> posts) {

        mContext = context;
        mPosts = posts;

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


    }

    @Override
    public int getItemCount() {

        return mPosts.size();
    }

    class PostListViewHolder extends RecyclerView.ViewHolder {

        ImageView mAuthorIconImageView;
        TextView mAuthorNickNameTextView;
        TextView mPostDateTextView;
        TextView mPostBodyTextView;
        TextView mTagCountTextView;
        Button mPostDetailButton;

        PostListViewHolder(View itemView) {

            super(itemView);

            mAuthorIconImageView = (ImageView)itemView.findViewById(R.id.post_item_icon_imageview);
            mAuthorNickNameTextView = (TextView)itemView.findViewById(R.id.post_item_nick_name_textview);
            mPostDateTextView = (TextView)itemView.findViewById(R.id.post_item_date_textview);
            mPostBodyTextView = (TextView)itemView.findViewById(R.id.post_item_body_textview);
            mTagCountTextView = (TextView)itemView.findViewById(R.id.post_item_tag_count_textview);
            mPostDetailButton = (Button)itemView.findViewById(R.id.post_item_detail_button);


        }
    }



}
