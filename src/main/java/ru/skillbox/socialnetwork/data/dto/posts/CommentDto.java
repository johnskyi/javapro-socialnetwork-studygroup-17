package ru.skillbox.socialnetwork.data.dto.posts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetwork.data.entity.PostComment;

import java.time.ZoneOffset;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class CommentDto {

    private Long id;
    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("comment_text")
    private String text;

    @JsonProperty("post_id")
    private String postId;
    private Long time;

    private Author author;

    @JsonProperty("is_blocked")
    private Boolean isBlocked;

    public CommentDto(PostComment postComment) {

        this.id = postComment.getId();
        if(postComment.getParent() != null) {
            this.parentId = postComment.getParent().getId();
        }
        this.text = postComment.getText();
        this.postId = String.valueOf(postComment.getPost().getId());
        this.time = postComment.getTime().toEpochSecond(ZoneOffset.UTC);
        this.author = Author.builder()
                .id(postComment.getAuthor().getId())
                .fullName(postComment.getAuthor().getFirstName() + " " +
                        postComment.getAuthor().getLastName())
                .photo(postComment.getAuthor().getPhoto())
                .build();
        this.isBlocked = postComment.isBlocked();
    }

    @lombok.Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Author{
        private Long id;
        private String fullName;
        private String photo;
    }

}
