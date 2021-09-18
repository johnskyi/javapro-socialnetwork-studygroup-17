package ru.skillbox.socialnetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    String error;

    @JsonProperty("error_description")
    String errorDescription;
}
