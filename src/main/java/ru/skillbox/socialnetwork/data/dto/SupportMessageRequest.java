package ru.skillbox.socialnetwork.data.dto;

import lombok.Data;

@Data
public class SupportMessageRequest {
    private String email;
    private String message;
}
