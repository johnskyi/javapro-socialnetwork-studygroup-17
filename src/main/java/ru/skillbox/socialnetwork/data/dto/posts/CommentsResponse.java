package ru.skillbox.socialnetwork.data.dto.posts;

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
public class CommentsResponse {

    String error;
    Long timestamp;
    long total;
    long offset;
    long perPage;
    @JsonProperty("data")
    List<CommentDto> comments;
}
