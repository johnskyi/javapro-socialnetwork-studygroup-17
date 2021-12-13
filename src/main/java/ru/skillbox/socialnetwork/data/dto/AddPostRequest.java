package ru.skillbox.socialnetwork.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddPostRequest {
    private String title;

    @JsonProperty("post_text")
    private String text;

    private List<String> tags;

}

