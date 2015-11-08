package me.stogram.android.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devspark.robototextview.widget.RobotoTextView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.stogram.android.R;
import me.stogram.android.model.post.Post;
import me.stogram.android.util.Constants;
import me.stogram.android.util.Utils;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
public class PostListRecyclerAdapter extends RecyclerView.Adapter<PostListRecyclerAdapter.ViewHolder> {

    ArrayList<Post> postList;
    Context context;
    Drawable transparentDrawable;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static final int EMPTY_VIEW = 10;
    public PostListRecyclerAdapter(Context context, ArrayList<Post> postList) {
        this.postList = postList;
        this.context = context;
        this.notifyDataSetChanged();
    }


    public void setPostList(ArrayList<Post> postList) {
        postList.clear();
        postList.addAll(postList);
        this.notifyItemRangeInserted(0, postList.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_view, viewGroup, false);
            EmptyViewHolder evh = new EmptyViewHolder(v);
            return evh;
        }
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Post post = postList.get(i);
        Log.d("Post", post.getUser().toString());
        viewHolder.userTitle.setText(post.getUser().getLastName() + " " + post.getUser().getFirstName());
        viewHolder.createdPost.setText(getHumanDate(post.getCreated() * 1000));
        viewHolder.likeCounts.setText(String.valueOf(post.getLikesCount()));
        if (TextUtils.isEmpty(post.getUser().getThumbPhoto())) {
            if (transparentDrawable == null)
                transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
            viewHolder.userIcon.setImageDrawable(transparentDrawable);
        } else {
            Glide.with(context).load(Constants.API.HOST_URL + post.getUser().getThumbPhoto())
                    .centerCrop().
                    diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(viewHolder.userIcon);
        }

        Glide.with(context).load(Constants.API.HOST_URL + post.getOriginImg())
                .centerCrop().
                diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(viewHolder.imagePost);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (postList.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }


    public class EmptyViewHolder extends ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RobotoTextView userTitle;
        private RoundedImageView userIcon;
        private RobotoTextView createdPost;
        private ImageView imagePost;
        private RobotoTextView likeCounts;

        public ViewHolder(View itemView) {
            super(itemView);
            userTitle = (RobotoTextView) itemView.findViewById(R.id.userTitle);
            userIcon = (RoundedImageView) itemView.findViewById(R.id.userIcon);
            createdPost = (RobotoTextView) itemView.findViewById(R.id.createdPost);
            imagePost = (ImageView) itemView.findViewById(R.id.imagePost);
            likeCounts = (RobotoTextView) itemView.findViewById(R.id.likeCounts);
        }
    }

    private String getHumanDate(Integer timestamp) {
        long postTime = timestamp * 1000;
        int daysDiff = Utils.daysBetween(postTime, System.currentTimeMillis());
        if (daysDiff > 0) {
            return dateFormat.format(new Date(postTime));
        } else {
            return timeFormat.format(new Date(postTime));
        }
    }

}
