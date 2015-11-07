package me.stogram.android.model.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import me.stogram.android.model.comment.Comment;
import me.stogram.android.model.profile.UserProfile;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Like {
    @JsonProperty("likes_count")
    Integer likesCount;
    @JsonProperty("is_liked")
    Boolean isLiked;

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public String toString() {
        return "Like{" +
                "likesCount=" + likesCount +
                ", isLiked=" + isLiked +
                '}';
    }
}
