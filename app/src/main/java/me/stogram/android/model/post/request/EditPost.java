package me.stogram.android.model.post.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import me.stogram.android.model.BaseRequest;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditPost extends BaseRequest{
    @JsonProperty("comment")
    String comment;
    @JsonProperty("post_id")
    String postId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "EditPost{" +
                "comment='" + comment + '\'' +
                ", postId='" + postId + '\'' +
                '}';
    }

}
