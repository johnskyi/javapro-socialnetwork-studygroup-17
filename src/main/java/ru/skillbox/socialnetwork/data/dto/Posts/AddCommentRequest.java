package ru.skillbox.socialnetwork.data.dto.Posts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCommentRequest {

    @JsonProperty("parent_id")
    private Long parentId;

    @JsonProperty("comment_text")
    private String text;
}
