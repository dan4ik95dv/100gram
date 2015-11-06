package me.stogram.android.model.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Created by Daniil Celikin on 06.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseLoginUser {
    @JsonProperty("status")
    Integer status;
    @JsonProperty("token")
    String token;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ResponseLoginUser{" +
                "status=" + status +
                ", token='" + token + '\'' +
                '}';
    }
}

