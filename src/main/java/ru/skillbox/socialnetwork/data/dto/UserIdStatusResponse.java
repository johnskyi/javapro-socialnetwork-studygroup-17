package ru.skillbox.socialnetwork.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetwork.data.entity.FriendshipStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdStatusResponse {

    @JsonProperty("user_id")
    private Long userId;

    private FriendshipStatus status;
}
