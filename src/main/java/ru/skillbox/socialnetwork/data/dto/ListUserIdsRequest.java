package ru.skillbox.socialnetwork.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListUserIdsRequest {

    @JsonProperty("users_ids")
    private List<Long> userIds;
}
