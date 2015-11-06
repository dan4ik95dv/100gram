package me.stogram.android.model.profile.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import me.stogram.android.model.BaseRequest;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestProfile extends BaseRequest{
    @JsonProperty("user_id")
    String userId;
}
