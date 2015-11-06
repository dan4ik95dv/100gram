package me.stogram.android.model.comment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import me.stogram.android.model.BaseRequest;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestComments extends BaseRequest{
    @JsonProperty("limit")
    Integer limit;
    @JsonProperty("post_id")
    String postId;

    public RequestComments(Integer count, String postId) {
        this.limit = count;
        this.postId = postId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "RequestComments{" +
                "limit=" + limit +
                ", postId='" + postId + '\'' +
                '}';
    }
}
