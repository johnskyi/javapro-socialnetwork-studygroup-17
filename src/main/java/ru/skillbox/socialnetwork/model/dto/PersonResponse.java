package ru.skillbox.socialnetwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {
    String error;
    Long timestamp;
    Data data;

    @lombok.Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        Long id;

        @JsonProperty("first_name")
        String firstName;

        @JsonProperty("last_name")
        String lastName;

        @JsonProperty("reg_date")
        Long regDate;

        @JsonProperty("birth_date")
        Long birthDate;

        String email;
        String phone;
        String photo;
        String about;
        String city;
        String country;

        @JsonProperty("messages_permission")
        MessagePermission messagePermission;

        @JsonProperty("last_online_time")
        Long lastOnlineTime;

        @JsonProperty("is_blocked")
        Boolean isBlocked;
    }
}
