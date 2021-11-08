package ru.skillbox.socialnetwork.data.dto.likes;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeUsersListResponse {
    private String error;
    private long timestamp;
    private Data data;

    @lombok.Data
    @Builder
    @JsonInclude(NON_NULL)
    public static class Data {
        private int likes;
        private List<Long> users;
    }
}
