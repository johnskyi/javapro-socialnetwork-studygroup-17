package ru.skillbox.socialnetwork.data.dto.likes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PutLikeRequest {
    @JsonProperty("item_id")
    private long itemId;
    private String type;
}
