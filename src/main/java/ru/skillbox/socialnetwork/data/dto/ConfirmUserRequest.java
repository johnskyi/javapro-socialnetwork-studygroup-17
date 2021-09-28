package ru.skillbox.socialnetwork.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmUserRequest {

    private Long userId;
    private String token;

}
