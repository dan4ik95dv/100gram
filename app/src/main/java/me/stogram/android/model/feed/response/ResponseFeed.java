package me.stogram.android.model.feed.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import me.stogram.android.model.BaseResponse;
import me.stogram.android.model.post.Post;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseFeed extends BaseResponse{
    @JsonProperty("feed")
    ArrayList<Post> posts;

    public ArrayList<Post> getFeeds() {
        return posts;
    }

    public void setFeeds(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "ResponseFeed{" +
                "posts=" + posts +
                '}';
    }
}
