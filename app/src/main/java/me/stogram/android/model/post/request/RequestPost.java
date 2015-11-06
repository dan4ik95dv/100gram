package me.stogram.android.model.post.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import me.stogram.android.model.BaseRequest;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestPost extends BaseRequest{
    @JsonProperty("post_id")
    String postId;

    public RequestPost(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "RequestPost{" +
                "postId='" + postId + '\'' +
                '}';
    }
}
