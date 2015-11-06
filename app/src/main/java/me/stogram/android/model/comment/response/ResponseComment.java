package me.stogram.android.model.comment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import me.stogram.android.model.BaseResponse;
import me.stogram.android.model.comment.Comment;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseComment extends BaseResponse {
    @JsonProperty("comment")
    Comment comment;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ResponseComment{" +
                "comment=" + comment +
                '}';
    }
}
