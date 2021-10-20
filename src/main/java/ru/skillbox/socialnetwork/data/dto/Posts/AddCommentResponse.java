package ru.skillbox.socialnetwork.data.dto.Posts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentResponse {

    String error;
    Long timestamp;
    Data data;

    @lombok.Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(NON_NULL)
    public static class Data {
        private Long id;
        @JsonProperty("parent_id")
        private Long parentId;
        @JsonProperty("post_id")
        private String postId;
        private Long timestamp;

        @JsonProperty("author_id")
        private Long authorId;

        @JsonProperty("comment_text")
        private String commentText;

        @JsonProperty("is_blocked")
        private boolean isBlocked;
    }
}
