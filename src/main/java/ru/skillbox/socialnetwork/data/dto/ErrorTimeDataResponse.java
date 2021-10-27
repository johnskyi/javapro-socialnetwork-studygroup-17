package ru.skillbox.socialnetwork.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorTimeDataResponse {

    private String error;
    private long timestamp;
    private Object data;

    public ErrorTimeDataResponse(String error, Object data) {
        this.error = error;
        timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public ErrorTimeDataResponse(Object data) {
        this("", data);
    }
}
