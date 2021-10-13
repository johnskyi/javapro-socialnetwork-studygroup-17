package ru.skillbox.socialnetwork.data.dto.Posts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetwork.data.entity.Country;
import ru.skillbox.socialnetwork.data.entity.MessagePermission;
import ru.skillbox.socialnetwork.data.entity.Town;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddNewPostResponse {

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
        private Long timestamp;

        private Author author;

        private String title;

        @JsonProperty("post_text")
        private String text;

        @JsonProperty("is_blocked")
        private boolean isBlocked;

        private int likes;

        private List<Comment> comments;
    }
}
