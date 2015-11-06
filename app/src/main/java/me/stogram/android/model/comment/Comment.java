package me.stogram.android.model.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import me.stogram.android.model.profile.UserProfile;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    @JsonProperty("comment_id")
    String commentId;
    @JsonProperty("comment")
    String comment;
    @JsonProperty("published")
    Integer published;
    @JsonProperty("user")
    UserProfile userProfile;


    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }



    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                ", published=" + published +
                '}';
    }
}
