package me.stogram.android.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devspark.robototextview.widget.RobotoTextView;
import com.makeramen.roundedimageview.RoundedImageView;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.stogram.android.R;
import me.stogram.android.application.App;
import me.stogram.android.io.api.client.RestClient;
import me.stogram.android.model.comment.Comment;
import me.stogram.android.model.comment.request.SendComment;
import me.stogram.android.model.comment.response.ResponseComments;
import me.stogram.android.model.post.Like;
import me.stogram.android.model.post.Post;
import me.stogram.android.ui.adapter.CommentsListViewAdapter;
import me.stogram.android.util.Constants;
import me.stogram.android.util.Utils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Daniil Celikin on 08.11.2015.
 */
public class PostActivity extends AppCompatActivity {
    @Bind(R.id.scrollView)
    NestedScrollView scrollView;
    @Bind(R.id.userTitle)
    RobotoTextView userTitle;
    @Bind(R.id.userIcon)
    RoundedImageView userIcon;
    @Bind(R.id.createdPost)
    RobotoTextView createdPost;
    @Bind(R.id.imagePost)
    ImageView imagePost;
    @Bind(R.id.commentBody)
    RobotoTextView commentBody;
    @Bind(R.id.likeCounts)
    RobotoTextView likeCounts;
    @Bind(R.id.iconLike)
    ImageView iconLike;
    @Bind(R.id.commentsList)
    ListView commentsListView;
    @Bind(R.id.commentButton)
    Button commentButton;
    @Bind(R.id.commentTextEdit)
    EditText commentTextEdit;
    Post post;
    CommentsListViewAdapter commentsListViewAdapter;
    Drawable transparentDrawable;
    ArrayList<Comment> commentList = new ArrayList<>();

    @OnClick(R.id.commentButton)
    void onClickCommentSend() {
        if (commentTextEdit.getText().toString().length() > 1) {
            String body = commentTextEdit.getText().toString();
            if (post != null) {
                addComment(body, post.getId());
                commentTextEdit.getText().clear();
            }
        } else {
            Toast.makeText(this, "Заполните поле!", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.iconLike)
    void onClickIconLike() {
        if (post != null) {
            setLike(post.getId());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            post = Parcels.unwrap(getIntent().getParcelableExtra("POST_ITEM"));
            if (post != null && post.getComments() != null) {
                commentList = post.getComments();
            }
            commentBody.setText(post.getBody()!= null ? post.getBody() : "");
            userTitle.setText(post.getUser().getLastName() + " " + post.getUser().getFirstName());
            createdPost.setText(getHumanDate((long) post.getCreated()));
            likeCounts.setText(String.valueOf(post.getLikesCount()));
            if (TextUtils.isEmpty(post.getUser().getThumbPhoto())) {
                if (transparentDrawable == null)
                    transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
                userIcon.setImageDrawable(transparentDrawable);
            } else {
                Glide.with(this).load(Constants.API.HOST_URL + post.getUser().getThumbPhoto())
                        .centerCrop().
                        diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into(userIcon);
            }

            Glide.with(this).load(Constants.API.HOST_URL + post.getOriginImg())
                    .centerCrop().
                    diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(imagePost);
            commentsListViewAdapter = new CommentsListViewAdapter(this, commentList);
            commentsListView.setAdapter(commentsListViewAdapter);
            commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            commentsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    return false;
                }
            });
            if (post.getIsLiked() != null)
                iconLike.setImageResource(post.getIsLiked() ? R.drawable.ic_favorite_red_600_18dp : R.drawable.ic_favorite_outline_grey_400_18dp);
            Utils.setListViewHeightBasedOnChildren(commentsListView);

            scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    scrollView.fullScroll(View.FOCUS_UP);

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getHumanDate(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        long postTime = timestamp * 1000;
        int daysDiff = Utils.daysBetween(postTime, System.currentTimeMillis());
        if (daysDiff > 0) {
            return dateFormat.format(new Date(postTime));
        } else {
            return timeFormat.format(new Date(postTime));
        }
    }

    private void addComment(String body, String postId) {
        RestClient.APIService apiService = RestClient.getClient();
        Call<ResponseComments> call = apiService.addComment(
                postId, new SendComment(body), App.getToken()
        );
        call.enqueue(new Callback<ResponseComments>() {
            @Override
            public void onResponse(Response<ResponseComments> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getComments() != null) {
                        commentsListViewAdapter.setComments(response.body().getComments());
                        Utils.setListViewHeightBasedOnChildren(commentsListView);

                        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                    scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                } else {
                                    scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(PostActivity.this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLike(String postId) {
        RestClient.APIService apiService = RestClient.getClient();
        Call<Like> call = apiService.setLike(postId, App.getToken());
        call.enqueue(new Callback<Like>() {
            @Override
            public void onResponse(Response<Like> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    iconLike.setImageResource(response.body().getIsLiked() ? R.drawable.ic_favorite_red_600_18dp : R.drawable.ic_favorite_outline_grey_400_18dp);
                    likeCounts.setText(String.valueOf(response.body().getLikesCount()));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(PostActivity.this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            }
        });
    }


}
