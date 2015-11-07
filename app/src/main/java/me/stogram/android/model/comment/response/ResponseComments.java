package me.stogram.android.model.comment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


import me.stogram.android.model.comment.Comment;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseComments {
    @JsonProperty("comments")
    ArrayList<Comment> comments;

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ResponseComments{" +
                "comments=" + comments +
                '}';
    }
}
