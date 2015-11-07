package me.stogram.android.model.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import me.stogram.android.model.profile.UserProfile;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    @JsonProperty("id")
    String id;
    @JsonProperty("comment")
    String comment;
    @JsonProperty("published")
    Integer published;
    @JsonProperty("user")
    UserProfile userProfile;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
