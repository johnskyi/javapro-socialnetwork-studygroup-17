package ru.skillbox.socialnetwork.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ErrorTimeTotalOffsetPerPageListDataResponse {

    private String error;
    private long timestamp;
    private long total;
    private int offset;
    private int perPage;
    private List<?> data;

    public ErrorTimeTotalOffsetPerPageListDataResponse(long total, int offset, int perPage, List<?> data) {
        this.error = "";
        timestamp = System.currentTimeMillis();
        this.total = total;
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
    }
}
