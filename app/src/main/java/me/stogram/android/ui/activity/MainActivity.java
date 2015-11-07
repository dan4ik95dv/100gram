package me.stogram.android.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.stogram.android.R;
import me.stogram.android.application.App;
import me.stogram.android.io.api.client.RestClient;
import me.stogram.android.model.feed.response.ResponseFeed;
import me.stogram.android.model.post.Post;
import me.stogram.android.ui.adapter.MainPagerAdapter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Daniil Celikin on 06.11.2015.
 */
public class MainActivity extends AppCompatActivity {
    @Bind(R.id.tabs)
    TabLayout mTabs;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    public static String POSITION = "POSITION";
    private MainPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewPager);
       getFeed(20, 0);

    }

    private void getFeed(Integer limit, Integer offset) {
        RestClient.APIService apiService = RestClient.getClient();
        Call<ResponseFeed> call = apiService.getFeed(limit, offset, App.getToken());
        call.enqueue(new Callback<ResponseFeed>() {
            @Override
            public void onResponse(Response<ResponseFeed> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ArrayList<Post> posts = response.body().getFeeds();
                    //Posts
                    //TODO Adapter fill
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            }
        });
    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, mTabs.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mViewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }
}