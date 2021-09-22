package ru.skillbox.socialnetwork.data.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class PasswordRecoveryResponse {

    private String error;
    private String errorDescription;
    private long timestamp;
    private Map<String, String> data;

}
