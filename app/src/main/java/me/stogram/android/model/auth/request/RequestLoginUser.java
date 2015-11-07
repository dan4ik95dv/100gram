package me.stogram.android.model.auth.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Daniil Celikin on 06.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class RequestLoginUser {

    @JsonProperty("access_key")
    String accessKey;
    @JsonProperty("email")
    String email;
    @JsonProperty("vk_id")
    String vkId;

    public RequestLoginUser(String accessKey, String email, String vkId) {
        this.accessKey = accessKey;
        this.email = email;
        this.vkId = vkId;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
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
                ", vkId='" + vkId + '\'' +
                '}';
    }
}

