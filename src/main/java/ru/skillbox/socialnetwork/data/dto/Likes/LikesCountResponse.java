package ru.skillbox.socialnetwork.data.dto.Likes;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikesCountResponse {
    private String error;
    private long timestamp;
    private Data data;

    @lombok.Data
    @Builder
    @JsonInclude(NON_NULL)
    public static class Data {
        private long likes;
    }
}

