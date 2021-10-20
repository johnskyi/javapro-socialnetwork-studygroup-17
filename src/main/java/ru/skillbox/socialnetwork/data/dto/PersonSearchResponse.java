package ru.skillbox.socialnetwork.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonSearchResponse {
    private String error;
    private Long timestamp;
    private Long total;
    private int offset;
    private int perPage;
    private List<PersonResponse.Data> data;
}
