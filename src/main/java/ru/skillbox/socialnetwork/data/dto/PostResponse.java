package ru.skillbox.socialnetwork.data.dto;

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
public class PostResponse {

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


        @lombok.Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(NON_NULL)
        public static class Author {
            private Long id;

            @JsonProperty("first_name")
            private String firstName;

            @JsonProperty("last_name")
            private String lastName;

            @JsonProperty("reg_date")
            private Long regDate;

            @JsonProperty("birth_date")
            private Long birthDate;

            private String email;

            private String phone;
            private String photo;
            private String about;

            private Town town;
            private Country country;

            @JsonProperty("messages_permission")
            private MessagePermission messagePermission;

            @JsonProperty("last_online_time")
            private Long lastOnlineTime;

            @JsonProperty("is_blocked")
            private Boolean isBlocked;

        }

        @lombok.Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(NON_NULL)
        public static class Comment {

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
    }
}
