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
public class Post {
    @JsonProperty("thumb_img")
    String thumbImg;
    @JsonProperty("origin_img")
    String originImg;
    @JsonProperty("created")
    Integer created;
    @JsonProperty("updated")
    Integer updated;
    @JsonProperty("comment")
    String comment;
    @JsonProperty("likes_count")
    Integer likesCount;
    @JsonProperty("is_liked")
    Boolean isLiked;
    @JsonProperty("id")
    String id;
    @JsonProperty("comments")
    ArrayList<Comment> comments;
    @JsonProperty("user")
    UserProfile user;

    public String getComment() {
        return comment;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getThumbImg() {
        return thumbImg;
    }

    public void setThumbImg(String thumbImg) {
        this.thumbImg = thumbImg;
    }

    public String getOriginImg() {
        return originImg;
    }

    public void setOriginImg(String originImg) {
        this.originImg = originImg;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }
}
