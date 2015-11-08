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
import android.widget.BaseAdapter;
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
import retrofit.http.POST;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
public class PostListViewAdapter extends BaseAdapter {

    ArrayList<Post> postList;
    Context context;
    Drawable transparentDrawable;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    LayoutInflater lInflater;

    public PostListViewAdapter(Context context,ArrayList<Post> postList) {
        this.postList = postList;
        this.context = context;
        this.lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return postList.size();
    }


    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPosts(ArrayList<Post> postList){
        this.postList =  postList;
        notifyDataSetChanged();
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.post_card, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        Post post = postList.get(position);
        viewHolder.userTitle.setText(post.getUser().getLastName() + " " + post.getUser().getFirstName());
        viewHolder.createdPost.setText(getHumanDate((long) post.getCreated()));
        viewHolder.likeCounts.setText(String.valueOf(post.getLikesCount()));
        if (TextUtils.isEmpty(post.getUser().getThumbPhoto())) {
            if (transparentDrawable == null)
                transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
            viewHolder.userIcon.setImageDrawable(transparentDrawable);
        } else {
            Glide.with(context).load(Constants.API.HOST_URL + post.getUser().getThumbPhoto())
                    .centerCrop().
                    diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.userIcon);
        }

        Glide.with(context).load(Constants.API.HOST_URL + post.getOriginImg())
                .centerCrop().
                diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.imagePost);
        if (post.getIsLiked() != null)
            viewHolder.iconLike.setImageResource(post.getIsLiked() ? R.drawable.ic_favorite_red_600_18dp : R.drawable.ic_favorite_outline_grey_400_18dp);

        return view;
    }

    class ViewHolder  {
        private RobotoTextView userTitle;
        private RoundedImageView userIcon;
        private RobotoTextView createdPost;
        private ImageView imagePost;
        private ImageView iconLike;
        private RobotoTextView likeCounts;

        public ViewHolder(View itemView) {
            userTitle = (RobotoTextView) itemView.findViewById(R.id.userTitle);
            userIcon = (RoundedImageView) itemView.findViewById(R.id.userIcon);
            iconLike = (ImageView) itemView.findViewById(R.id.iconLike);
            createdPost = (RobotoTextView) itemView.findViewById(R.id.createdPost);
            imagePost = (ImageView) itemView.findViewById(R.id.imagePost);
            likeCounts = (RobotoTextView) itemView.findViewById(R.id.likeCounts);
        }
    }

    private String getHumanDate(long timestamp) {
        long postTime = timestamp * 1000;
        int daysDiff = Utils.daysBetween(postTime, System.currentTimeMillis());
        if (daysDiff > 0) {
            return dateFormat.format(new Date(postTime));
        } else {
            return timeFormat.format(new Date(postTime));
        }
    }

}
