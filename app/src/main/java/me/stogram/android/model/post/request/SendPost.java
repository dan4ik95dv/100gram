package me.stogram.android.model.post.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendPost {
    @JsonProperty("image")
    String image;
    @JsonProperty("comment")
    String comment;

    public SendPost(String image, String comment) {
        this.image = image;
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "SendPost{" +
                "image='" + image + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
