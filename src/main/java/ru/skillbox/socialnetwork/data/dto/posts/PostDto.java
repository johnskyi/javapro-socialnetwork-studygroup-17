package ru.skillbox.socialnetwork.data.dto.posts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetwork.data.entity.Post;

import java.time.ZoneOffset;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class PostDto {
    private Long id;
    private Long time;

    private AuthorDto author;

    private String title;

    @JsonProperty("post_text")
    private String text;

    @JsonProperty("is_blocked")
    private boolean isBlocked;

    private int likes;

    private List<CommentDto> comments;

    private PostType type;

    public PostDto(Post post, int likes, List<CommentDto>comments) {
        this.id = post.getId();
        this.time = post.getTime().toEpochSecond(ZoneOffset.UTC);
        this.author = new AuthorDto(post.getAuthor());
        this.title = post.getTitle();
        this.text = post.getTextHtml();
        this.isBlocked = post.isBlocked();
        this.likes = likes;
        this.comments = comments;
        this.type = PostType.POSTED;
    }

    public PostDto(Post post) {
        this.id = post.getId();
        this.time = post.getTime().toEpochSecond(ZoneOffset.UTC);
        this.author = new AuthorDto(post.getAuthor());
        this.title = post.getTitle();
        this.text = post.getTextHtml();
        this.isBlocked = post.isBlocked();
        this.likes = post.getLikes().size();
        this.comments = CommentDto.getCommentDtoList(post.getComments());
    }
}
