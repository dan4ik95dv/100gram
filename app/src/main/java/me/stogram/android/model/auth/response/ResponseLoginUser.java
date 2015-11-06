package me.stogram.android.model.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import me.stogram.android.model.BaseResponse;


/**
 * Created by Daniil Celikin on 06.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseLoginUser extends BaseResponse{
    @JsonProperty("token")
    String token;

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

