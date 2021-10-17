package ru.skillbox.socialnetwork.data.dto.Posts;

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

    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("comment_text")
    private String text;
    private Long id;

    @JsonProperty("post_id")
    private Long postId;
    private Long time;

    @JsonProperty("author_id")
    private Long authorId;

    @JsonProperty("is_blocked")
    private Boolean isBlocked;

    public CommentDto(PostComment postComment) {
        this.id = postComment.getId();
        if(postComment.getParent() != null) {
            this.parentId = postComment.getParent().getId();
        }
        this.text = postComment.getText();
        this.postId = postComment.getPost().getId();
        this.time = postComment.getTime().toEpochSecond(ZoneOffset.UTC);
        this.authorId = postComment.getAuthor().getId();
        this.isBlocked = postComment.isBlocked();
    }
}
