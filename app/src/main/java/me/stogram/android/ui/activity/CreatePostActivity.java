package me.stogram.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import org.parceler.Parcels;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.stogram.android.R;
import me.stogram.android.application.App;
import me.stogram.android.io.api.client.RestClient;
import me.stogram.android.model.post.Post;
import me.stogram.android.model.post.response.ResponsePost;
import me.stogram.android.ui.base.BaseActivity;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Daniil Celikin on 08.11.2015.
 */
public class CreatePostActivity extends BaseActivity {

    String body;
    String pathFile;
    File fileImage;
    @Bind(R.id.postItButton)
    ImageView postItButton;

    @Bind(R.id.bodyText)
    EditText bodyText;

    @Bind(R.id.thumbPostImage)
    ImageView thumbPostImage;


    @OnClick(R.id.postItButton)
    void postItOnClick() {

        body = bodyText.getText().toString();
        sendPost(body, fileImage);
        showProgressDialog(getString(R.string.processing_wait));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pathFile = getIntent().getStringExtra("PATH_FILE");
            fileImage = new File(pathFile);
            if (fileImage.exists()) {
                Glide.with(this).load(fileImage).centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into(thumbPostImage);
            } else {
                Toast.makeText(CreatePostActivity.this, getResources().getString(R.string.error_read_image), Toast.LENGTH_LONG).show();
            }

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    private void sendPost(String bodyText, File file) {
        RestClient.APIService apiService = RestClient.getClient();
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), bodyText);
        RequestBody requestImage = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        Call<ResponsePost> call = apiService.addPost(
                requestImage, body, App.getToken());
        call.enqueue(new Callback<ResponsePost>() {
            @Override
            public void onResponse(Response<ResponsePost> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Post post = response.body().getPost();
                    if (post != null) {
                        Intent i = new Intent(CreatePostActivity.this, PostActivity.class);
                        i.putExtra("POST_ITEM", Parcels.wrap(post));
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(CreatePostActivity.this, getResources().getString(R.string.connection_error) + "("+response.code()+")", Toast.LENGTH_LONG).show();
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Throwable t) {
                dismissProgressDialog();
                Toast.makeText(CreatePostActivity.this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            }
        });
    }


}
