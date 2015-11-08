package me.stogram.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.stogram.android.R;
import me.stogram.android.application.App;
import me.stogram.android.io.api.client.RestClient;
import me.stogram.android.model.feed.response.ResponseFeed;
import me.stogram.android.model.post.Post;
import me.stogram.android.ui.activity.PostActivity;
import me.stogram.android.ui.adapter.PostListViewAdapter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class FeedFragment extends Fragment {


    @Bind(R.id.listRefresh)
    SwipeRefreshLayout mListRefresh;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.postListView)
    ListView mPostListView;

    //    PostListRecyclerAdapter postListRecyclerAdapter;
    PostListViewAdapter postListViewAdapter;

//    LinearLayoutManager mLinearLayoutManager;

    public static FeedFragment init() {
        return new FeedFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        mListRefresh.setOnRefreshListener(onRefreshListener);
        postListViewAdapter = new PostListViewAdapter(getContext(), new ArrayList<Post>());
        mPostListView.setAdapter(postListViewAdapter);
        mPostListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), PostActivity.class);
                i.putExtra("POST_ITEM", Parcels.wrap(postListViewAdapter.getItem(position)));
                startActivity(i);
            }
        });

        getFeed(20, 0);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        return view;
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getFeed(20, 0);
        }
    };

    private void getFeed(Integer limit, Integer offset) {
        RestClient.APIService apiService = RestClient.getClient();
        Call<ResponseFeed> call = apiService.getFeed(limit, offset, App.getToken());
        call.enqueue(new Callback<ResponseFeed>() {
            @Override
            public void onResponse(Response<ResponseFeed> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ArrayList<Post> posts = response.body().getFeeds();
                    if (posts != null) {
                        postListViewAdapter.setPosts(posts);
                    }
                }else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                }
                mListRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                mListRefresh.setRefreshing(false);
            }
        });
    }
}
