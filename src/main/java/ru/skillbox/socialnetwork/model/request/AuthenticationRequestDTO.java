package ru.skillbox.socialnetwork.model.request;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
