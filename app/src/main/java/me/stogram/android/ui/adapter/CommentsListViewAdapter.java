package me.stogram.android.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
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
import me.stogram.android.model.comment.Comment;
import me.stogram.android.model.post.Post;
import me.stogram.android.util.Constants;
import me.stogram.android.util.Utils;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
public class CommentsListViewAdapter extends BaseAdapter {

    ArrayList<Comment> commentsList;
    Context context;
    Drawable transparentDrawable;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    LayoutInflater lInflater;

    public CommentsListViewAdapter(Context context, ArrayList<Comment> commentsList) {
        this.commentsList = commentsList;
        this.context = context;
        this.lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return commentsList.size();
    }


    @Override
    public Object getItem(int position) {
        return commentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setComments(ArrayList<Comment> commentsList){
        this.commentsList =  commentsList;
        notifyDataSetChanged();
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.comment_item, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        Comment comment = commentsList.get(position);
        viewHolder.userTitle.setText(comment.getUser().getLastName() + " " + comment.getUser().getFirstName());
        viewHolder.createdComment.setText(getHumanDate((long) comment.getCreated()));
        viewHolder.body.setText(comment.getBody());
        if (TextUtils.isEmpty(comment.getUser().getThumbPhoto())) {
            if (transparentDrawable == null)
                transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
            viewHolder.userIcon.setImageDrawable(transparentDrawable);
        } else {
            Glide.with(context).load(Constants.API.HOST_URL + comment.getUser().getThumbPhoto())
                    .centerCrop().
                    diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(viewHolder.userIcon);
        }

        return view;
    }

    class ViewHolder  {
        private RobotoTextView userTitle;
        private RoundedImageView userIcon;
        private RobotoTextView createdComment;
        private RobotoTextView body;

        public ViewHolder(View itemView) {
            userTitle = (RobotoTextView) itemView.findViewById(R.id.userTitle);
            userIcon = (RoundedImageView) itemView.findViewById(R.id.userIcon);
            createdComment = (RobotoTextView) itemView.findViewById(R.id.createdComment);
            body = (RobotoTextView) itemView.findViewById(R.id.body);
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
