package me.stogram.android.model.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class LimitedPost {
        @JsonProperty("thumb_img")
        String thumbImg;
        @JsonProperty("origin_img")
        String originImg;
        @JsonProperty("published")
        Integer published;
        @JsonProperty("post_id")
        String postId;

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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
