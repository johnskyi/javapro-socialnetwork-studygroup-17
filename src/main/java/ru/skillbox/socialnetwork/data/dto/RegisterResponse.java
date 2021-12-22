package ru.skillbox.socialnetwork.data.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class RegisterResponse {
    String error;
    LocalDateTime timestamp;
    Data data;

    @Builder
    @lombok.Data
    public static class Data {
        private String message;
    }
}
