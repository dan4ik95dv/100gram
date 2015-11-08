package me.stogram.android.model.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.parceler.Parcel;

import java.util.ArrayList;

import me.stogram.android.model.post.Post;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtendedProfile extends UserProfile{
    @JsonProperty("posts")
    ArrayList<Post> posts;

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "ExtendedProfile{" +
                "posts=" + posts +
                '}';
    }
}
