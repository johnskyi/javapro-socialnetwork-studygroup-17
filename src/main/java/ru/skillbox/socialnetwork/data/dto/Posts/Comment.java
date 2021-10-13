package ru.skillbox.socialnetwork.data.dto.Posts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class Comment {

    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("comment_text")
    private String text;
    private Long id;

    @JsonProperty("post_id")
    private String postId;
    private Long time;

    @JsonProperty("author_id")
    private Long authorId;

    @JsonProperty("is_blocked")
    private Boolean isBlocked;
}
