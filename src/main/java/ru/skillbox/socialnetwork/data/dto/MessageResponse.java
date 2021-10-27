package ru.skillbox.socialnetwork.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {

    private String message;

    public MessageResponse() {
        message = "ok";
    }
}
