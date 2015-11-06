package me.stogram.android.model.comment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import me.stogram.android.model.BaseRequest;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveComment extends BaseRequest{
    @JsonProperty("comment_id")
    String commentId;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        return "RemoveComment{" +
                "commentId='" + commentId + '\'' +
                '}';
    }
}
