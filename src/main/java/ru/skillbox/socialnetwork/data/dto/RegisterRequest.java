package ru.skillbox.socialnetwork.data.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String passwd1;
    private String passwd2;
    private String firstName;
    private String lastName;
}
