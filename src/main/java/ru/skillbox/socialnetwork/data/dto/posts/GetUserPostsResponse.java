package ru.skillbox.socialnetwork.data.dto.posts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserPostsResponse {

    String error;
    Long timestamp;
    long total;
    long offset;
    long perPage;
    @JsonProperty("data")
    List<PostDto> posts;
}
