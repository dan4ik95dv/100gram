package me.stogram.android.model.comment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendComment {
    @JsonProperty("comment")
    String comment;
    @JsonProperty("id")
    String id;

    public SendComment(String comment, String postId) {
        this.comment = comment;
        this.id = postId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SendComment{" +
                "comment='" + comment + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
