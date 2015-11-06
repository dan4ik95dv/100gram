package me.stogram.android.model.profile.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import me.stogram.android.model.profile.UserProfile;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseProfile extends UserProfile {

}
