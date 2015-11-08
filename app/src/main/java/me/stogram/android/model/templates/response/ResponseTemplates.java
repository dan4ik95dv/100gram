package me.stogram.android.model.templates.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import me.stogram.android.model.post.Post;
import me.stogram.android.model.templates.Template;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseTemplates {
    @JsonProperty("templates")
    ArrayList<Template> template;

    public ArrayList<Template> getTemplate() {
        return template;
    }

    public void setTemplate(ArrayList<Template> template) {
        this.template = template;
    }

}
