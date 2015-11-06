package me.stogram.android.model.auth.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Daniil Celikin on 06.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestLoginUser {
    @JsonProperty("accessKey")
    String accessKey;
    @JsonProperty("email")
    String email;


    public RequestLoginUser(String accessKey, String email) {
        this.accessKey = accessKey;
        this.email = email;
    }


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RequestLoginUser{" +
                "accessKey='" + accessKey + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

