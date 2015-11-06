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
    @JsonProperty("published")
    Integer published;
    @JsonProperty("comment")
    String comment;
    @JsonProperty("likes_count")
    Integer likesCount;
    @JsonProperty("is_liked")
    Boolean isLiked;
    @JsonProperty("post_id")
    String postId;
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



    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }



    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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
