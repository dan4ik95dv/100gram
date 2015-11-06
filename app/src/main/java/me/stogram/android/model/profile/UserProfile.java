package me.stogram.android.model.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile {
    @JsonProperty("thumb_photo")
    String thumbPhoto;
    @JsonProperty("origin_photo")
    String originPhoto;
    @JsonProperty("user_id")
    String userId;
    @JsonProperty("first_name")
    String firstName;
    @JsonProperty("last_name")
    String lastName;
    @JsonProperty("status")
    String status;
    @JsonProperty("url")
    String url;

    public String getOriginPhoto() {
        return originPhoto;
    }

    public void setOriginPhoto(String originPhoto) {
        this.originPhoto = originPhoto;
    }

    public String getThumbPhoto() {
        return thumbPhoto;
    }

    public void setThumbPhoto(String thumbPhoto) {
        this.thumbPhoto = thumbPhoto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "thumbPhoto='" + thumbPhoto + '\'' +
                ", originPhoto='" + originPhoto + '\'' +
                ", userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
