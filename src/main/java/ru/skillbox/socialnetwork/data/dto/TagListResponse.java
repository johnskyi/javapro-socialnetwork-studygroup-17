package ru.skillbox.socialnetwork.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetwork.data.entity.Tag;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagListResponse {
    private String error;
    private long timestamp;
    private int total;
    private int offset;
    private int perPage;
    List<Tag> data;

}
