package me.stogram.android.model.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.parceler.Parcel;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@Parcel
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class LimitedPost {
        @JsonProperty("thumb_img")
        String thumbImg;
        @JsonProperty("origin_img")
        String originImg;
        @JsonProperty("created")
        Integer created;
        @JsonProperty("updated")
        Integer updated;
        @JsonProperty("id")
        String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
