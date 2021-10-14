package ru.skillbox.socialnetwork.data.dto.Posts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class PostDto {
    private Long id;
    private Long timestamp;

    private Author author;

    private String title;

    @JsonProperty("post_text")
    private String text;

    @JsonProperty("is_blocked")
    private boolean isBlocked;

    private int likes;

    private List<Comment> comments;

    private PostType type;
}
